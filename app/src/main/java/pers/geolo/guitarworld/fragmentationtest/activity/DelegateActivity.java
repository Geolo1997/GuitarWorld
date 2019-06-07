package pers.geolo.guitarworld.fragmentationtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-04
 */
public abstract class DelegateActivity extends SupportActivity {

    public abstract BaseDelegate getLauncherDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        FrameLayout container = new FrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, getLauncherDelegate());
        }
    }
}
