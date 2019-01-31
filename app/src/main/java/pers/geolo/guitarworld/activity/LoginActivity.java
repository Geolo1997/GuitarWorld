package pers.geolo.guitarworld.activity;

import android.os.Bundle;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import pers.geolo.guitarworld.util.LoginManager;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.util.SingletonHolder;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    protected EditText etUsername;
    @BindView(R.id.et_password)
    protected EditText etPassword;
    @BindView(R.id.tv_loginHint)
    protected TextView tvLoginHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.bt_login)
    protected void login() {
        final String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        HttpUtils.login(username, password, new BaseCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                Log.d(TAG, "登录成功");
                // 将登录用户存入LoginManager
                SingletonHolder.getInstance(LoginManager.class).getLoginUser().setUsername(username);
                // 跳转至主活动
                startActivity(MainActivity.class);
                finish();
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
