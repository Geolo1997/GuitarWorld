package pers.geolo.guitarworld.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.delegate.DynamicDelegate;

public class MainDelegate extends BaseDelegate {

    @BindView(R.id.bt_dynamic)
    Button selectedButton;

    private BaseDelegate[] delegates;

    @Override
    public Object getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initSubDelegates();
    }

    private void initSubDelegates() {
        delegates = new BaseDelegate[]{
                new DynamicDelegate(),

        };
//        showHideFragment();
        loadMultipleRootFragment(R.id.ll_fragment, 0, delegates);
        onButtonClicked(selectedButton);
    }

    // 底部导航栏
    @OnClick({R.id.bt_dynamic, R.id.bt_discover, R.id.bt_tools, R.id.bt_shop, R.id.bt_profile})
    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_dynamic:
//                loadr(R.id.ll_fragment, DynamicDelegate.class);
                break;
            case R.id.bt_discover:
//                setFragment(R.id.ll_fragment, DiscoverDelegate.class);
                break;
            case R.id.bt_tools:
//                setFragment(R.id.ll_fragment, ToolsDelegate.class);
                break;
            case R.id.bt_shop:
//                setFragment(R.id.ll_fragment, ShopDelegate.class);
                break;
            case R.id.bt_profile:
//                setFragment(R.id.ll_fragment, MineDelegate.class);
                break;
            default:
        }
        // 改变颜色
        selectedButton.setBackgroundColor(getContext().getColor(R.color.white));
        selectedButton = (Button) view;
        selectedButton.setBackgroundColor(getContext().getColor(R.color.colorPrimary));
    }
}