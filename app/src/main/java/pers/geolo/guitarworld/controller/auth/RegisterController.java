package pers.geolo.guitarworld.controller.auth;

import android.os.Bundle;
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
import pers.geolo.guitarworld.model.AuthModel;

public class RegisterController extends BaseController {

    @BindView(R.id.username_text)
    EditText usernameText;
    @BindView(R.id.password_text)
    EditText passwordText;
    @BindView(R.id.email_text)
    EditText emailText;
    @BindView(R.id.register_hint)
    TextView registerHint;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    protected int getLayout() {
        return R.layout.register;
    }

    @Override
    public void initView(ViewParams viewParams) {

    }

    @OnClick(R.id.bt_register)
    protected void register() {
        // 获取注册信息
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        // 发送注册请求
        // TODO 增加验证码(verifyCode)
        authModel.register(username, password, email, null,
                new DataListener<Void>() {
                    @Override
                    public void onReturn(Void aVoid) {
                        // 注册成功，返回登录界面
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username);
                        bundle.putString("password", password);
                        // TODO EventBus.getDefault().post(new RegisterSuccessEvent);
                        ViewManager.destroy(RegisterController.this);
                    }

                    @Override
                    public void onError(String message) {
                        registerHint.setText(message);
                    }
                });
    }
}
