package pers.geolo.guitarworld.delegate;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.auth.LoginDelegate;
import pers.geolo.guitarworld.delegate.dynamic.MainDelegate;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.entity.DataListener;

public class LauncherDelegate extends BaseDelegate {

    @BindView(R.id.tv_timer)
    TextView tvTimer;

    private LauncherTimer launcherTimer;

    @Override
    public Object getLayout() {
        return R.layout.delegate_loading;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initCountDownTimer();
    }

    @OnClick(R.id.tv_timer)
    public void onClick() {
        launcherTimer.cancel();
        autoLogin();
    }

    private void initCountDownTimer() {
        launcherTimer = new LauncherTimer(5000, 1000);
        launcherTimer.start();
    }

    private void autoLogin() {
        AuthModel.autoLogin(new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                startWithPop(new MainDelegate());
            }

            @Override
            public void onError(String message) {
                startWithPop(new LoginDelegate());
            }
        });
    }

    private class LauncherTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public LauncherTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int s = (int) (millisUntilFinished / 1000) + 1;
            tvTimer.setText(s + "秒");
        }

        @Override
        public void onFinish() {
            autoLogin();
        }
    }
}
