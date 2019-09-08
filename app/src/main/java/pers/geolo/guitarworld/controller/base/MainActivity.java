package pers.geolo.guitarworld.controller.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import org.litepal.LitePal;
import pers.geolo.guitarworld.controller.auth.LoginController;
import pers.geolo.guitarworld.microview.MicroviewActivity;
import pers.geolo.guitarworld.microview.ViewManager;
import pers.geolo.guitarworld.model.*;
import pers.geolo.guitarworld.ui.icon.FontModule;
import pers.geolo.guitarworld.util.ActivityUtils;
import pers.geolo.guitarworld.util.MicroviewTransferUtils;
import pers.geolo.guitarworld.util.PermissionUtils;
import pers.geolo.guitarworld.util.StatusBarUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class MainActivity extends MicroviewActivity {

    @Override
    protected void init() {
        // 修改状态栏字体颜色
        StatusBarUtils.setAndroidNativeLightStatusBar(this, true);
        // LitePal 初始化
        LitePal.initialize(getApplicationContext());

        Iconify.with(new FontAwesomeModule())
                .with(new FontModule());

        BeanFactory.registerBean(new AuthModel());
        BeanFactory.registerBean(new CommentModel());
        BeanFactory.registerBean(new FileModel());
        BeanFactory.registerBean(new MusicModel());
        BeanFactory.registerBean(new UserModel());
        BeanFactory.registerBean(new WorksModel());
        BeanFactory.registerBean(new InformationModel());
        ViewManager.setActivity(this);
        ViewManager.start(new LoginController());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void loadRootFragment(int containerId, @NonNull BaseController toFragment) {
        MicroviewTransferUtils.start(toFragment);
    }

    public void start(BaseController controller) {
        MicroviewTransferUtils.start(controller);
    }
}
