package pers.geolo.guitarworld.controller.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.auth.MineController;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.util.ToastUtils;

public class MainController extends BaseController {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.bt_dynamic)
    LinearLayout selectedLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private BaseController[] controllers;
    private BaseController currentController;

    private BackTimeCounter backTimeCounter = new BackTimeCounter();

    @Override
    public Object getLayoutView() {
        return R.layout.main;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        setSwipeBackEnable(false);
        initTitleBar();
        selectedLayout = rootView.findViewById(R.id.bt_dynamic);
        initSubControllers();
        loadRootFragment(R.id.slide_container, new MineController());
//        // 标题
//        loadRootFragment(R.id.container_tool_bar, ToolBarController.newInstance("主页"));
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

    private void initSubControllers() {
        controllers = new BaseController[]{
                new DynamicController(),
                new DiscoverController(),
                new ToolsController(),
                new ShopController()
        };
        loadMultipleRootFragment(R.id.delegates_layout, 4, controllers);
        currentController = controllers[0];
        onButtonClicked(selectedLayout);
    }

    // 底部导航栏
    @OnClick({R.id.bt_dynamic, R.id.bt_discover, R.id.bt_tools, R.id.bt_shop})
    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_dynamic:
                showHideFragment(R.id.delegates_layout,controllers[0], currentController);
                currentController = controllers[0];
                titleBar.setTitle("动态");
                break;
            case R.id.bt_discover:
                showHideFragment(R.id.delegates_layout,controllers[1], currentController);
                currentController = controllers[1];
                titleBar.setTitle("发现");
                break;
            case R.id.bt_tools:
                showHideFragment(R.id.delegates_layout,controllers[2], currentController);
                currentController = controllers[2];
                titleBar.setTitle("工具");
                break;
            case R.id.bt_shop:
                showHideFragment(R.id.delegates_layout,controllers[3], currentController);
                currentController = controllers[3];
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

////    @Override
//    public boolean onBackPressedSupport() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//            return true;
//        } else if (backTimeCounter.isFirstTimeClick()) {
//            ToastUtils.showInfoToast(getContext(), "再次点击退出应用");
//            backTimeCounter.setStartTime();
//            return true;
//        } else {
////            return super.onBackPressedSupport();
//        }
//
//    }

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
