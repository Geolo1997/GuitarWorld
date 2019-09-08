package pers.geolo.guitarworld.controller.auth;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.microview.core.ViewManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.model.AuthModel;

public class LoginController extends BaseController {

    @BindView(R.id.username_text)
    EditText etUsername;
    @BindView(R.id.password_text)
    EditText etPassword;
    @BindView(R.id.tv_loginHint)
    TextView tvLoginHint;
    @BindView(R.id.cb_savePassword)
    CheckBox cbSavePassword;
    @BindView(R.id.cb_autoLogin)
    CheckBox cbAutoLogin;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    protected int getLayout() {
        return R.layout.login;
    }

    @Override
    public void initView(ViewParams viewParams) {
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
//                startWithPop(new MainController());
            }

            @Override
            public void onError(String message) {
                tvLoginHint.setText(message);
            }
        });
    }

    @OnClick(R.id.bt_register)
    public void startRegisterActivity() {
        ViewManager.start(new RegisterController());
    }

    //TODO 注册成功回调
//    @Override
//    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
//        super.onFragmentResult(requestCode, resultCode, data);
//        if (requestCode == REGISTER && resultCode == RESULT_OK) {
//            ToastUtils.showSuccessToast(getContext(), "注册成功");
//            usernameText.setText(data.getString("username"));
//            passwordText.setText(data.getString("password"));
//            login();
//        }
//    }
}
