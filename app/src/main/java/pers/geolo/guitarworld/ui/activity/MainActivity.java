package pers.geolo.guitarworld.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.ui.fragment.*;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bt_dynamic)
    Button selectedButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 默认选中动态页碎片
        onButtonClicked(selectedButton);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    // 底部导航栏
    @OnClick({R.id.bt_dynamic, R.id.bt_discover, R.id.bt_tools, R.id.bt_shop, R.id.bt_profile})
    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_dynamic:
                setFragment(R.id.ll_fragment, DynamicFragment.class);
                break;
            case R.id.bt_discover:
                setFragment(R.id.ll_fragment, DiscoverFragment.class);
                break;
            case R.id.bt_tools:
                setFragment(R.id.ll_fragment, ToolsFragment.class);
                break;
            case R.id.bt_shop:
                setFragment(R.id.ll_fragment, ShopFragment.class);
                break;
            case R.id.bt_profile:
                setFragment(R.id.ll_fragment, ProfileFragment.class);
                break;
            default:
        }
        // 改变颜色
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.white));
        selectedButton = (Button) view;
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.colorPrimary));
    }


}