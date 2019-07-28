package pers.geolo.guitarworld.delegate.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;

public class MainDelegate extends BaseDelegate {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.bt_dynamic)
    LinearLayout selectedLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private BaseDelegate[] delegates;
    private BaseDelegate currentDelegate;

    private BackTimeCounter backTimeCounter = new BackTimeCounter();

    @Override
    public Object getLayout() {
        return R.layout.main;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        setSwipeBackEnable(false);
        initTitleBar();
        selectedLayout = rootView.findViewById(R.id.bt_dynamic);
        initSubDelegates();
//        // 标题
//        loadRootFragment(R.id.container_tool_bar, ToolBarDelegate.newInstance("主页"));
    }

    private void initTitleBar() {
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }

    private void initSubDelegates() {
        delegates = new BaseDelegate[]{
                new DynamicDelegate(),
                new DiscoverDelegate(),
                new ToolsDelegate(),
                new ShopDelegate()
        };
        loadMultipleRootFragment(R.id.delegates_layout, 0, delegates);
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
                titleBar.setTitle("动态");
                break;
            case R.id.bt_discover:
                showHideFragment(delegates[1], currentDelegate);
                currentDelegate = delegates[1];
                titleBar.setTitle("发现");
                break;
            case R.id.bt_tools:
                showHideFragment(delegates[2], currentDelegate);
                currentDelegate = delegates[2];
                titleBar.setTitle("工具");
                break;
            case R.id.bt_shop:
                showHideFragment(delegates[3], currentDelegate);
                currentDelegate = delegates[3];
                titleBar.setTitle("商城");
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

    @Override
    public boolean onBackPressedSupport() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (backTimeCounter.isFirstTimeClick()) {
            Toast.makeText(getContext(), "再次点击退出应用", Toast.LENGTH_SHORT).show();
            backTimeCounter.setStartTime();
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }

    class BackTimeCounter {

        private long startTime;

        public void setStartTime() {
            this.startTime = System.currentTimeMillis();
        }

        public boolean isFirstTimeClick() {
            long currentTime = System.currentTimeMillis();
            if (startTime == 0 || currentTime - startTime >= 2000) {
                startTime = 0;
                return true;
            } else {
                return false;
            }
        }
    }
}
