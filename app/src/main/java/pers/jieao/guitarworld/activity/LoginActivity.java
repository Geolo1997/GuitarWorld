package pers.jieao.guitarworld.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
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
        String id = etId.getText().toString();
        String password = etPassword.getText().toString();
        //TODO 发送登录请求
        Network.loginRequest(id, password, new Callback() {
            @Override
            public void onSuccess() {
                //TODO 成功
                progressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure() {
                //TODO 失败
            }
        });

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("注意");
        progressDialog.setMessage("正在登录中，请稍等......");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}
