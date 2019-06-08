package pers.geolo.guitarworld.delegate.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.auth.MineDelegate;
import pers.geolo.guitarworld.delegate.shop.ShopDelegate;
import pers.geolo.guitarworld.delegate.tool.ToolsDelegate;
import pers.geolo.guitarworld.base.BaseDelegate;

public class MainDelegate extends BaseDelegate {

    @BindView(R.id.bt_dynamic)
    Button selectedButton;

    private BaseDelegate[] delegates;

    @Override
    public Object getLayout() {
        return R.layout.delegate_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        selectedButton = rootView.findViewById(R.id.bt_dynamic);
        selectedButton.setBackgroundColor(getContext().getColor(R.color.colorPrimary));
        initSubDelegates();
    }

    private void initSubDelegates() {
        delegates = new BaseDelegate[]{
                new DynamicDelegate(),
                new DiscoverDelegate(),
                new ToolsDelegate(),
                new ShopDelegate(),
                new MineDelegate()
        };
        loadMultipleRootFragment(R.id.ll_fragment, 0, delegates);
        onButtonClicked(selectedButton);
    }

    // 底部导航栏
    @OnClick({R.id.bt_dynamic, R.id.bt_discover, R.id.bt_tools, R.id.bt_shop, R.id.bt_mine})
    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_dynamic:
                showHideFragment(delegates[0]);
                break;
            case R.id.bt_discover:
                showHideFragment(delegates[1]);
                break;
            case R.id.bt_tools:
                showHideFragment(delegates[2]);
                break;
            case R.id.bt_shop:
                showHideFragment(delegates[3]);
                break;
            case R.id.bt_mine:
                showHideFragment(delegates[4]);
                break;
            default:
        }
        // 改变颜色
        selectedButton.setBackgroundColor(getContext().getColor(R.color.white));
        selectedButton = (Button) view;
        selectedButton.setBackgroundColor(getContext().getColor(R.color.colorPrimary));
    }
}
