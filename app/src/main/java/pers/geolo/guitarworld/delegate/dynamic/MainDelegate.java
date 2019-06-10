package pers.geolo.guitarworld.delegate.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.shop.ShopDelegate;
import pers.geolo.guitarworld.delegate.tools.ToolsDelegate;

public class MainDelegate extends BaseDelegate {

    @BindView(R.id.bt_dynamic)
    LinearLayout selectedLayout;

    private BaseDelegate[] delegates;
    private BaseDelegate currentDelegate;

    @Override
    public Object getLayout() {
        return R.layout.delegate_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        selectedLayout = rootView.findViewById(R.id.bt_dynamic);
        initSubDelegates();
//        // 标题
//        loadRootFragment(R.id.container_tool_bar, ToolBarDelegate.newInstance("主页"));
    }

    private void initSubDelegates() {
        delegates = new BaseDelegate[]{
                new DynamicDelegate(),
                new DiscoverDelegate(),
                new ToolsDelegate(),
                new ShopDelegate()
        };
        loadMultipleRootFragment(R.id.ll_fragment, 0, delegates);
        currentDelegate = delegates[0];
        onButtonClicked(selectedLayout);
    }

    // 底部导航栏
    @OnClick({R.id.bt_dynamic, R.id.bt_discover, R.id.bt_tools, R.id.bt_shop})
    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_dynamic:
                showHideFragment(delegates[0], currentDelegate);
                currentDelegate = delegates[0];
                break;
            case R.id.bt_discover:
                showHideFragment(delegates[1], currentDelegate);
                currentDelegate = delegates[1];
                break;
            case R.id.bt_tools:
                showHideFragment(delegates[2], currentDelegate);
                currentDelegate = delegates[2];
                break;
            case R.id.bt_shop:
                showHideFragment(delegates[3], currentDelegate);
                currentDelegate = delegates[3];
                break;
            default:
        }
        // 改变颜色
        SetSubTextViewColor(selectedLayout, getContext().getColor(R.color.black));
        selectedLayout = (LinearLayout) view;
        SetSubTextViewColor(selectedLayout, getContext().getColor(R.color.colorPrimaryDark));
    }

    private void SetSubTextViewColor(LinearLayout selectedLayout, int color) {
        for (int i = 0; i < selectedLayout.getChildCount(); i++) {
            View view = selectedLayout.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextColor(color);
            }
        }
    }
}
