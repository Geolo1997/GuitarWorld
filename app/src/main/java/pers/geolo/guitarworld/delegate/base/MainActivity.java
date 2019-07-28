package pers.geolo.guitarworld.delegate.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import me.yokeyword.fragmentation.SupportActivity;
import org.litepal.LitePal;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.index.LauncherDelegate;
import pers.geolo.guitarworld.model.*;
import pers.geolo.guitarworld.ui.icon.FontModule;
import pers.geolo.guitarworld.util.ActivityUtils;
import pers.geolo.guitarworld.util.PermissionUtils;
import pers.geolo.guitarworld.util.StatusBarUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
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

        loadRootFragment(R.id.container, new LauncherDelegate());
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
}
