package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;

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
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_register)
    protected void register() {
        HttpService.getInstance().getAPI(UserAPI.class)
                .register(etUsername.getText().toString(),
                        etPassword.getText().toString(),
                        etEmail.getText().toString())
                .enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        startActivityAndFinish(MainActivity.class);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        tvRegisterHint.setText(errorMessage);
                    }

                    @Override
                    public void onFailure() {
                        tvRegisterHint.setText("网络错误");
                    }
                });
    }
}
