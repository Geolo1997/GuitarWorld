package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.presenter.AuthPresenter;
import pers.geolo.guitarworld.view.LoginView;


public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.et_username)
    protected EditText etUsername;
    @BindView(R.id.et_password)
    protected EditText etPassword;
    @BindView(R.id.tv_loginHint)
    protected TextView tvLoginHint;
    @BindView(R.id.cb_savePassword)
    protected CheckBox cbSavePassword;
    @BindView(R.id.cb_autoLogin)
    protected CheckBox cbAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载保存的登录信息
        AuthPresenter.loadingLogInfo(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.bt_login)
    protected void login() {
        AuthPresenter.login(this);
    }

    @OnClick(R.id.bt_register)
    protected void startRegisterActivity() {
        startActivity(RegisterActivity.class);
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString();
    }

    @Override
    public void setUsername(String username) {
        etUsername.setText(username);
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void setPassword(String password) {
        etPassword.setText(password);
    }

    @Override
    public void onLoginSuccess() {
        startActivity(MainActivity.class);
    }

    @Override
    public void onLoginError() {
        tvLoginHint.setText("账号或密码错误");
    }

    @Override
    public void onLoginFailure() {
        tvLoginHint.setText("网络错误");
    }

    @Override
    public void setSavePassword(boolean savePassword) {
        cbSavePassword.setChecked(savePassword);
    }

    @Override
    public void setAutoLogin(boolean autoLogin) {
        cbAutoLogin.setChecked(autoLogin);
    }
}
