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
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.index.MainDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.util.ToastUtils;

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

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    public Object getLayout() {
        return R.layout.login;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // onBindView logInfo
        authModel.getLastSavedLogInfo(new DataListener<LogInfo>() {
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

    @OnClick(R.id.bt_login)
    protected void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean isSavePassword = cbSavePassword.isChecked();
        boolean isAutoLogin = cbAutoLogin.isChecked();
        LogInfo logInfo = new LogInfo(username, password, isSavePassword, isAutoLogin);
        authModel.login(username, password, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                authModel.saveLogInfo(logInfo);
                startWithPop(new MainDelegate());
            }

            @Override
            public void onError(String message) {
                tvLoginHint.setText(message);
            }
        });
    }

    @OnClick(R.id.bt_register)
    public void startRegisterActivity() {
        startForResult(new RegisterDelegate(), REGISTER);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REGISTER && resultCode == RESULT_OK) {
            ToastUtils.showSuccessToast(getContext(), "注册成功");
            etUsername.setText(data.getString("username"));
            etPassword.setText(data.getString("password"));
            login();
        }
    }
}
