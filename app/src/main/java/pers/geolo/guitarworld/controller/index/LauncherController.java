package pers.geolo.guitarworld.controller.index;

import android.os.CountDownTimer;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.auth.LoginController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.model.AuthModel;

public class LauncherController extends BaseController {

    @BindView(R.id.timer_text)
    TextView timerText;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    private CountDownTimer launcherTimer;

    @Override
    protected int getLayout() {
        return R.layout.launcher;
    }

    @Override
    public void initView(ViewParams viewParams) {
        launcherTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int s = (int) (millisUntilFinished / 1000) + 1;
                timerText.setText(s + "秒");
            }

            @Override
            public void onFinish() {
                autoLogin();
            }
        };
        launcherTimer.start();
    }

    @OnClick(R.id.timer_text)
    public void onClick() {
        launcherTimer.cancel();
        autoLogin();
    }

    private void autoLogin() {
        authModel.autoLogin(new DataCallback<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                ControllerManager.start(new MainController());
                ControllerManager.destroy(LauncherController.this);
            }

            @Override
            public void onError(String message) {
                ControllerManager.start(new LoginController());
                ControllerManager.destroy(LauncherController.this);
            }
        });
    }
}
