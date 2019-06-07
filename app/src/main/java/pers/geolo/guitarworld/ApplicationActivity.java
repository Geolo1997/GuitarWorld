package pers.geolo.guitarworld;

import pers.geolo.guitarworld.fragmentationtest.activity.DelegateActivity;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.delegate.LauncherDelegate;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class ApplicationActivity extends DelegateActivity {

    @Override
    public BaseDelegate getLauncherDelegate() {
        return new LauncherDelegate();
    }
}
