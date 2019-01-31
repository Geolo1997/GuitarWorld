package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.util.SingletonHolder;
import pers.geolo.guitarworld.fragment.DynamicFragment;
import pers.geolo.guitarworld.fragment.ProfileFragment;
import pers.geolo.guitarworld.fragment.ShopFragment;
import pers.geolo.guitarworld.fragment.ToolsFragment;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(findViewById(R.id.bt_dynamic));
    }

    /**
     * 替换Fragment
     *
     * @param view
     */
    @OnClick({R.id.bt_dynamic, R.id.bt_tools, R.id.bt_shop, R.id.bt_profile})
    protected void changeFragment(View view) {
        Fragment replaceFragment = null;
        switch (view.getId()) {
            case R.id.bt_dynamic:
                replaceFragment = SingletonHolder.getInstance(DynamicFragment.class);
                break;
            case R.id.bt_tools:
                replaceFragment = SingletonHolder.getInstance(ToolsFragment.class);
                break;
            case R.id.bt_shop:
                replaceFragment = SingletonHolder.getInstance(ShopFragment.class);
                break;
            case R.id.bt_profile:
                replaceFragment = SingletonHolder.getInstance(ProfileFragment.class);
                break;
            default:
                break;
        }
        setFragment(R.id.ll_fragment, replaceFragment);
    }
}