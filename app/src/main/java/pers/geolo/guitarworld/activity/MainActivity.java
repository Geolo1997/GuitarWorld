package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.fragment.*;
import pers.geolo.guitarworld.util.SingletonHolder;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bt_dynamic)
    protected Button selectedButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 默认动态页碎片
        onBtDynamicClicked();
        // 将选中按钮变色
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.bt_dynamic)
    public void onBtDynamicClicked() {
        setFragment(R.id.ll_fragment, SingletonHolder.getInstance(DynamicFragment.class));
    }

    @OnClick(R.id.bt_discover)
    public void onViewClicked() {
        setFragment(R.id.ll_fragment, SingletonHolder.getInstance(DiscoverFragment.class));
    }

    @OnClick(R.id.bt_tools)
    public void onBtToolsClicked() {
        setFragment(R.id.ll_fragment, SingletonHolder.getInstance(ToolsFragment.class));
    }

    @OnClick(R.id.bt_shop)
    public void onBtShopClicked() {
        setFragment(R.id.ll_fragment, SingletonHolder.getInstance(ShopFragment.class));
    }

    @OnClick(R.id.bt_profile)
    public void onBtProfileClicked() {
        setFragment(R.id.ll_fragment, SingletonHolder.getInstance(ProfileFragment.class));
    }


    @OnClick({R.id.bt_dynamic, R.id.bt_tools, R.id.bt_shop, R.id.bt_profile})
    public void onButtonClicked(View view) {
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.white));
        selectedButton = (Button) view;
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.colorPrimary));
    }


}