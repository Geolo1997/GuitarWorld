package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
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
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.bt_register)
    protected void register() {
        // 获取控件值
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();

        LogInfo logInfo = new LogInfo(username, password, false, false);
        HttpService.getInstance().getAPI(UserAPI.class)
                .register(username, password, email).enqueue(new BaseCallback<Void>() {
            @Override
            public void onSuccess(Void responseData) {
                DAOService.getInstance().saveLogInfo(logInfo);
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
