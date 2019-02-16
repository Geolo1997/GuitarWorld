package pers.geolo.guitarworld.activity;

import android.os.Bundle;

import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.*;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.model.User;
import pers.geolo.guitarworld.service.UserService;
import pers.geolo.guitarworld.util.SingletonHolder;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;

import java.io.IOException;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    protected EditText etUsername;
    @BindView(R.id.et_password)
    protected EditText etPassword;
    @BindView(R.id.tv_loginHint)
    protected TextView tvLoginHint;
    @BindView(R.id.cb_rememberPassword)
    protected CheckBox cbRememberPassword;
    @BindView(R.id.cb_autoLogin)
    protected CheckBox cbAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        User user = userService.getUser();
        etUsername.setText(user.getUsername());
        String password = user.getPassword();
        if (password != null) {
            etPassword.setText(password);
            cbRememberPassword.setChecked(true);
        }
    }

    @OnClick(R.id.bt_login)
    protected void login() {
        // 获取控件值
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        boolean isAutoLogin = cbAutoLogin.isChecked();
        boolean isRememberPassword = cbRememberPassword.isChecked();
        UserService userService = SingletonHolder.getInstance(UserService.class);
        userService.setState(username, password, isAutoLogin, isRememberPassword);
        userService.login(new BaseCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                startActivityAndFinish(MainActivity.class);
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "登录失败，错误信息为：" + message);
                tvLoginHint.setText(message);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "网络错误");
                tvLoginHint.setText("网络错误!");
            }
        });
    }

    @OnClick(R.id.bt_register)
    protected void startRegisterActivity() {
        startActivity(RegisterActivity.class);
    }
}
