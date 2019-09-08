package pers.geolo.guitarworld.controller.index;

import android.os.CountDownTimer;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.microview.core.ViewManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.auth.LoginController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.model.AuthModel;

public class LauncherController extends BaseController {

    @BindView(R.id.tv_timer)
    TextView tvTimer;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    LauncherTimer launcherTimer;

    @Override
    protected int getLayout() {
        return R.layout.launcher;
    }


    //    @Override
//    protected View getStatueBarTopView() {
//        return tvTimer;
//    }
    @Override
    public void initView(ViewParams viewParams) {
//        setStatusBarDark(false);
        // onBindView launcherTimer
        launcherTimer = new LauncherTimer(1000, 1000);
        launcherTimer.start();
    }

    @OnClick(R.id.tv_timer)
    public void onClick() {
        launcherTimer.cancel();
        autoLogin();
    }

    private void autoLogin() {
        authModel.autoLogin(new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
//                ViewManager.start(new MainController());
            }

            @Override
            public void onError(String message) {
                ViewManager.start(new LoginController());
                ViewManager.destroy(LauncherController.this);
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
