package pers.geolo.guitarworld.delegate;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.listener.LoginListener;

public class LauncherDelegate extends BaseDelegate {

    @BindView(R.id.tv_timer)
    TextView tvTimer;

    @Override
    public Object getLayout() {
        return R.layout.activity_loading;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initCountDownTimer();
    }

    @OnClick(R.id.tv_timer)
    public void onClick() {
        autoLogin();
    }

    private void initCountDownTimer() {
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
        LogInfo logInfo = AuthModel.getLastSavedLogInfo();
        if (logInfo.isAutoLogin()) {
            AuthModel.login(logInfo.getUsername(), logInfo.getPassword(), new LoginListener() {
                @Override
                public void onSuccess() {
                    startWithPop(new MainDelegate());
                }

                @Override
                public void onError(String message) {
                    startWithPop(new LoginDelegate());
                }

                @Override
                public void onFailure() {
                    startWithPop(new LoginDelegate());
                }
            });
        }
    }
}
