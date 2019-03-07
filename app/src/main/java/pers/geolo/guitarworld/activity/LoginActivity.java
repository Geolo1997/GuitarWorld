package pers.geolo.guitarworld.activity;

import android.os.Bundle;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;


public class LoginActivity extends BaseActivity {

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
        loadingLogInfo();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    private void loadingLogInfo() {
        LogInfo logInfo = DAOService.getInstance().getCurrentLogInfo();
        if (logInfo == null) {
            return;
        }
        etUsername.setText(logInfo.getUsername());
        etPassword.setText(logInfo.getPassword());
        cbSavePassword.setChecked(logInfo.isSavePassword());
        cbAutoLogin.setChecked(logInfo.isAutoLogin());
    }

    @OnClick(R.id.bt_login)
    protected void login() {
        // 获取控件值
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        boolean isAutoLogin = cbAutoLogin.isChecked();
        boolean isSavePassword = cbSavePassword.isChecked();
        // 构建登录信息对象
        LogInfo logInfo = new LogInfo(username, password, isSavePassword, isAutoLogin);
        // 登录
        HttpService.getInstance().getAPI(UserAPI.class)
                .login(username, password)
                .enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        DAOService.getInstance().saveLogInfo(logInfo);
                        startActivityAndFinish(MainActivity.class);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        tvLoginHint.setText(errorMessage);
                    }

                    @Override
                    public void onFailure() {
                        tvLoginHint.setText("网络错误");
                    }
                });
    }

    @OnClick(R.id.bt_register)
    protected void startRegisterActivity() {
        startActivity(RegisterActivity.class);
    }
}
