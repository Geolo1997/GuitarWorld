package pers.geolo.guitarworld.activity;

import android.os.Bundle;

import android.os.CountDownTimer;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.R;
import pers.geolo.util.SingletonHolder;

public class LoadingActivity extends BaseActivity {

    @BindView(R.id.tv_timer)
    TextView tvTimer;

    // 倒计时
    private int totalTime = 5000;
    private int intervalTime = 1000;
    private CountDownTimer loadingCountDownTimer = new CountDownTimer(totalTime, intervalTime) {
        @Override
        public void onTick(long millisUntilFinished) {
            int s = (int) (millisUntilFinished / 1000) + 1;
            tvTimer.setText(s + "秒");
        }

        @Override
        public void onFinish() {
            autoLogin();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 启动倒计时
//        loadingCountDownTimer.start();
        autoLogin();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_loading;
    }

    private void autoLogin() {
        LogInfo logInfo = SingletonHolder.getInstance(DAOService.class).getCurrentLogInfo();
        if (logInfo != null && logInfo.isAutoLogin()) {
            HttpService.getInstance().getAPI(UserAPI.class)
                    .login(logInfo.getUsername(), logInfo.getPassword())
                    .enqueue(new BaseCallback<Void>() {
                        @Override
                        public void onSuccess(Void responseData) {
                            startActivityAndFinish(MainActivity.class);
                        }

                        @Override
                        public void onError(int errorCode, String errorMessage) {
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
}
