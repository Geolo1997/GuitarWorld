package pers.geolo.guitarworld.controller.auth;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.greenrobot.eventbus.Subscribe;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.index.MainController;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.entity.event.RegisterSuccessEvent;
import pers.geolo.guitarworld.model.AuthModel;

public class LoginController extends BaseController {

    @BindView(R.id.username_text)
    EditText usernameText;
    @BindView(R.id.password_text)
    EditText passwordText;
    @BindView(R.id.login_hint_text)
    TextView loginHintText;
    @BindView(R.id.save_password_check)
    CheckBox savePasswordCheck;
    @BindView(R.id.auto_login_check)
    CheckBox autoLoginCheck;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    protected int getLayout() {
        return R.layout.login;
    }

    @Override
    public void initView(ViewParams viewParams) {
        authModel.getLastSavedLogInfo(new DataCallback<LogInfo>() {
            @Override
            public void onReturn(LogInfo logInfo) {
                setLogInfo(logInfo);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void setLogInfo(LogInfo logInfo) {
        usernameText.setText(logInfo.getUsername());
        passwordText.setText(logInfo.getPassword());
        savePasswordCheck.setChecked(logInfo.isSavePassword());
        autoLoginCheck.setChecked(logInfo.isAutoLogin());
    }

    @OnClick(R.id.login_button)
    protected void login() {
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        boolean isSavePassword = savePasswordCheck.isChecked();
        boolean isAutoLogin = autoLoginCheck.isChecked();
        LogInfo logInfo = new LogInfo(username, password, isSavePassword, isAutoLogin);
        authModel.login(username, password, new DataCallback<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                authModel.saveLogInfo(logInfo);
                ControllerManager.start(new MainController());
                ControllerManager.destroy(LoginController.this);
            }

            @Override
            public void onError(String message) {
                loginHintText.setText(message);
            }
        });
    }

    @OnClick(R.id.register_button)
    public void toRegister() {
        ControllerManager.start(new RegisterController());
    }

    @Subscribe
    public void onRegisterSuccess(RegisterSuccessEvent registerSuccessEvent) {
        LogInfo logInfo = registerSuccessEvent.getLogInfo();
        setLogInfo(logInfo);
    }
}
