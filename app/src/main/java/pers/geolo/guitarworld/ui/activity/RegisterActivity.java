package pers.geolo.guitarworld.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.presenter.RegisterPresenter;
import pers.geolo.guitarworld.view.RegisterView;

public class RegisterActivity extends BaseActivity implements RegisterView {

    @BindView(R.id.et_username)
    protected EditText etUsername;
    @BindView(R.id.et_password)
    protected EditText etPassword;
    @BindView(R.id.et_email)
    protected EditText etEmail;
    @BindView(R.id.tv_registerHint)
    protected TextView tvRegisterHint;

    private RegisterPresenter registerPresenter = new RegisterPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPresenter.bind(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.bt_register)
    protected void register() {
        registerPresenter.register();
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getEmail() {
        return etEmail.getText().toString().trim();
    }

    @Override
    public void toMainView() {
        startActivityAndFinish(MainActivity.class);
    }

    @Override
    public void showHint(String errorMessage) {
        tvRegisterHint.setText(errorMessage);
    }
}
