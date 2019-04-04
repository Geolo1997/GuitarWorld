package pers.geolo.guitarworld.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import butterknife.BindView;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.presenter.auth.AutoLoginPresenter;
import pers.geolo.guitarworld.view.AutoLoginView;

public class LoadingActivity extends BaseActivity implements AutoLoginView {


    AutoLoginPresenter autoLoginPresenter = new AutoLoginPresenter();

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
            startActivityAndFinish(LoginActivity.class);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_loading;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoLoginPresenter.bind(this);
        // 启动倒计时
//        loadingCountDownTimer.start();
        autoLoginPresenter.autoLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autoLoginPresenter.unBind();
    }

    @Override
    public void toMainView() {
        startActivityAndFinish(MainActivity.class);
    }

    @Override
    public void toLoginView() {
        startActivityAndFinish(LoginActivity.class);
    }
}
