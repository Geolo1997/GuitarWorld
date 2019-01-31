package pers.geolo.guitarworld.activity;

import android.os.Bundle;

import android.os.CountDownTimer;
import android.widget.TextView;
import butterknife.BindView;
import pers.geolo.guitarworld.util.LoginManager;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.util.SingletonHolder;
import pers.geolo.guitarworld.model.User;

public class LoadingActivity extends BaseActivity {

    @BindView(R.id.tv_timer)
    TextView tvTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int s = (int) (millisUntilFinished / 1000) + 1;
                tvTimer.setText(s + "秒");
            }

            @Override
            public void onFinish() {

                // 自动登录
                LoginManager loginManager = SingletonHolder.getInstance(LoginManager.class);

                //-----------test--------------
                User user = new User();
                user.setUsername("桀骜");
                loginManager.setLoginUser(user);
                //-------------end test-----------

                if (loginManager.isLogged()) {
                    startActivity(MainActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                finish();
            }
        }.start();
    }
}
