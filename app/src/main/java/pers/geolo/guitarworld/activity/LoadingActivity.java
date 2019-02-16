package pers.geolo.guitarworld.activity;

import android.os.Bundle;

import android.os.CountDownTimer;
import android.widget.TextView;
import butterknife.BindView;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.service.UserService;
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
        }.start();
    }
}
