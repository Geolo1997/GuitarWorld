package pers.geolo.guitarworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import me.yokeyword.fragmentation.SupportActivity;
import org.litepal.LitePal;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.LauncherDelegate;
import pers.geolo.guitarworld.ui.icon.FontModule;
import pers.geolo.guitarworld.util.ActivityUtils;
import pers.geolo.guitarworld.util.PermissionUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class ContainerActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadRootFragment(R.id.container, new LauncherDelegate());
        // LitePal 初始化
        LitePal.initialize(getApplicationContext());

        Iconify.with(new FontAwesomeModule())
                .with(new FontModule());
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
