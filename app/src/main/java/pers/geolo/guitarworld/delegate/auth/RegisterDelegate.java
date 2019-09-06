package pers.geolo.guitarworld.delegate.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.model.AuthModel;

public class RegisterDelegate extends BaseDelegate {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.tv_registerHint)
    TextView tvRegisterHint;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    public Object getLayoutView() {
        return R.layout.register;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R.id.bt_register)
    protected void register() {
        // 获取注册信息
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        // 发送注册请求
        // TODO 增加验证码(verifyCode)
        authModel.register(username, password, email, null, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                // 注册成功，返回登录界面
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("password", password);
//                setFragmentResult(RESULT_OK, bundle);
                pop();
            }

            @Override
            public void onError(String message) {
                tvRegisterHint.setText(message);
            }
        });
    }
}
