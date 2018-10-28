package pers.jieao.guitarworld.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import pers.jieao.guitarworld.LoginManager;
import pers.jieao.guitarworld.LoginStatus;
import pers.jieao.guitarworld.R;
import pers.jieao.guitarworld.base.BaseActivity;
import pers.jieao.guitarworld.network.Callback;
import pers.jieao.guitarworld.network.Network;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_password)
    EditText etPassword;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.bt_login)
    protected void login() {
        final String id = etId.getText().toString();
        final String password = etPassword.getText().toString();
        //TODO 发送登录请求
        Network.loginRequest(id, password, new Callback() {
            @Override
            public void onSuccess() {
                //TODO 成功
                progressDialog.dismiss();
                LoginManager.getInstance().setLoginStatus(LoginStatus.LOGINED, id, password);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure() {
                //TODO 失败
                progressDialog.dismiss();
                Looper.prepare();
                Toast.makeText(LoginActivity.this, "登录失败！账号或密码错误", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("注意");
        progressDialog.setMessage("正在登录中，请稍等......");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}
