package pers.geolo.guitarworld.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.listener.LoginListener;

public class LoginDelegate extends BaseDelegate {

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
        return R.layout.activity_login;
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
        AuthModel.login(username, password, new LoginListener() {
            @Override
            public void onSuccess() {
                AuthModel.saveLogInfo(logInfo);
                startWithPop(new MainDelegate());
            }

            @Override
            public void onError(String message) {
                tvLoginHint.setText(message);
            }

            @Override
            public void onFailure() {
                tvLoginHint.setText("网络错误");
            }
        });
    }

    @OnClick(R.id.bt_register)
    protected void startRegisterActivity() {
        start(new RegisterDelegate());
    }

    private void initLogInfo() {
        LogInfo logInfo = AuthModel.getLastSavedLogInfo();
        etUsername.setText(logInfo.getUsername());
        etPassword.setText(logInfo.getPassword());
        cbSavePassword.setChecked(logInfo.isSavePassword());
        cbAutoLogin.setChecked(logInfo.isAutoLogin());
    }
}
