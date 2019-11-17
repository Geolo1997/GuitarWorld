package pers.geolo.guitarworld;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import org.litepal.LitePal;
import org.microview.core.ControllerManager;
import org.microview.support.MicroviewActivity;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.index.LauncherController;
import pers.geolo.guitarworld.model.*;
import pers.geolo.guitarworld.ui.icon.FontModule;
import pers.geolo.guitarworld.util.StatusBarUtils;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/8
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

        ControllerManager.start(new LauncherController());
    }
}
