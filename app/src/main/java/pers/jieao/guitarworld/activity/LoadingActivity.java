package pers.jieao.guitarworld.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import pers.jieao.guitarworld.LoginManager;
import pers.jieao.guitarworld.R;
import pers.jieao.guitarworld.base.BaseActivity;

public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoginStatus();
        //finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_loading;
    }

    /**
     * 检查登录状态
     */
    private void checkLoginStatus() {
        // test
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoadingActivity.this);
        alertDialog.setTitle("注意");
        alertDialog.setMessage("请选择要进入的活动：");
        alertDialog.setPositiveButton("登录界面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.setNegativeButton("主界面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.show();
        // end test
        /*
        LoginManager manager = LoginManager.getInstance();
        if (manager.isLogined()) {
            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(intent);
        }*/
    }
}
