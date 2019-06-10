package pers.geolo.guitarworld.delegate.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.dynamic.MainDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.model.AuthModel;

public class LoginDelegate extends BaseDelegate {

    private static final int REGISTER = 1;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_loginHint)
    TextView tvLoginHint;
    @BindView(R.id.cb_savePassword)
    CheckBox cbSavePassword;
    @BindView(R.id.cb_autoLogin)
    CheckBox cbAutoLogin;

    @Override
    public Object getLayout() {
        return R.layout.delegate_login;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initLogInfo();
    }

    @OnClick(R.id.bt_login)
    protected void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean isSavePassword = cbSavePassword.isChecked();
        boolean isAutoLogin = cbAutoLogin.isChecked();
        LogInfo logInfo = new LogInfo(username, password, isSavePassword, isAutoLogin);
        AuthModel.login(username, password, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                AuthModel.saveLogInfo(logInfo);
                startWithPop(new MainDelegate());
            }

            @Override
            public void onError(String message) {
                tvLoginHint.setText(message);
            }
        });
    }

    @OnClick(R.id.bt_register)
    protected void startRegisterActivity() {
        startForResult(new RegisterDelegate(), REGISTER);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REGISTER && resultCode == RESULT_OK) {
            Toast.makeText(getContext(), "注册成功！",Toast.LENGTH_SHORT).show();
            etUsername.setText(data.getString("username"));
            etPassword.setText(data.getString("password"));
            login();
        }
    }

    private void initLogInfo() {
        AuthModel.getLastSavedLogInfo(new DataListener<LogInfo>() {
            @Override
            public void onReturn(LogInfo logInfo) {
                etUsername.setText(logInfo.getUsername());
                etPassword.setText(logInfo.getPassword());
                cbSavePassword.setChecked(logInfo.isSavePassword());
                cbAutoLogin.setChecked(logInfo.isAutoLogin());
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
