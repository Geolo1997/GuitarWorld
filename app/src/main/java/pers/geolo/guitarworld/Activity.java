package pers.geolo.guitarworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import org.litepal.LitePal;

import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.base.DelegateActivity;
import pers.geolo.guitarworld.delegate.LauncherDelegate;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class Activity extends DelegateActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // LitePal 初始化
        LitePal.initialize(getApplicationContext());
    }

    @Override
    public BaseDelegate getLauncherDelegate() {
        return new LauncherDelegate();
    }
}
