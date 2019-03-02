package pers.geolo.guitarworld.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import pers.geolo.guitarworld.network.APIFactory;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.service.UserService;
import pers.geolo.guitarworld.util.SingletonHolder;

public class LoadingActivity extends BaseActivity {

    @BindView(R.id.tv_timer)
    TextView tvTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

//        showInputDialog();

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int s = (int) (millisUntilFinished / 1000) + 1;
                tvTimer.setText(s + "秒");
            }

            @Override
            public void onFinish() {
                autoLogin();
            }
        }.start();
    }

    private void autoLogin() {
        UserService userService = SingletonHolder.getInstance(UserService.class);
        if (userService.isAutoLogin()) {
            userService.login(new BaseCallback<Void>() {
                @Override
                public void onSuccess(Void data) {
                    startActivityAndFinish(MainActivity.class);
                }

                @Override
                public void onError(String message) {
                    startActivityAndFinish(LoginActivity.class);
                }

                @Override
                public void onFailure() {
                    startActivityAndFinish(LoginActivity.class);
                }
            });
        } else {
            startActivityAndFinish(LoginActivity.class);
        }
    }

    private void showInputDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(LoadingActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(LoadingActivity.this);
        inputDialog.setTitle("输入服务器hrl:").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = editText.getText().toString();
                        APIFactory.setBaseUrl(url);
                        autoLogin();
                    }
                }).show();
    }
}
