package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_username)
    protected EditText etUsername;
    @BindView(R.id.et_password)
    protected EditText etPassword;
    @BindView(R.id.et_email)
    protected EditText etEmail;
    @BindView(R.id.tv_registerHint)
    protected TextView tvRegisterHint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @OnClick(R.id.bt_register)
    protected void register() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        HttpUtils.register(username, password, email, new BaseCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                Log.d(TAG, "注册成功");
                startActivity(MainActivity.class);
                finish();
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "注册失败，错误信息为：" + message);
                tvRegisterHint.setText(message);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "网络错误");
                tvRegisterHint.setText("网络错误!");
            }
        });
    }
}
