package pers.geolo.guitarworld.controller.index;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import org.microview.core.ControllerManager;
import org.microview.core.ViewController;
import org.microview.core.ViewParams;
import org.microview.support.ActivityManager;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.user.MineController;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/10
 */
public class MainController extends BaseController {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.module_container)
    FrameLayout moduleContainer;
    @BindView(R.id.dynamic_layout)
    LinearLayout dynamicLayout;
    @BindView(R.id.discover_layout)
    LinearLayout discoverLayout;
    @BindView(R.id.tools_layout)
    LinearLayout toolsLayout;
    @BindView(R.id.shop_layout)
    LinearLayout shopLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.slide_container)
    FrameLayout slideContainer;

    private Map<ViewGroup, ViewController> controllerMap;
    private long lastBackTime;

    @Override
    protected int getLayout() {
        return R.layout.main;
    }

    @Override
    public void initView(ViewParams viewParams) {
        // 加载module
        controllerMap = new HashMap<>(4);
        controllerMap.put(dynamicLayout, new DynamicController());
        controllerMap.put(discoverLayout, new DiscoverController());
        controllerMap.put(toolsLayout, new ToolsController());
        controllerMap.put(shopLayout, new ShopController());
        controllerMap.forEach(new BiConsumer<ViewGroup, ViewController>() {
            @Override
            public void accept(ViewGroup viewGroup, ViewController viewController) {
                ControllerManager.load(moduleContainer, viewController);
            }
        });
        onViewClicked(dynamicLayout);
        ControllerManager.load(slideContainer, new MineController());
        // 设置侧滑栏和搜索按钮监听
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
                ControllerManager.start(new SearchResultController());
            }
        });
        // 设置返回键计时
        lastBackTime = -1;
    }

    @OnClick({R.id.dynamic_layout, R.id.discover_layout, R.id.tools_layout, R.id.shop_layout})
    public void onViewClicked(View view) {
        ViewController controller = controllerMap.get(view);
        ControllerManager.show(controller);
        switch (view.getId()) {
            case R.id.dynamic_layout:
                titleBar.setTitle("动态");
                break;
            case R.id.discover_layout:
                titleBar.setTitle("发现");
                break;
            case R.id.tools_layout:
                titleBar.setTitle("工具");
                break;
            case R.id.shop_layout:
                titleBar.setTitle("商城");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (lastBackTime == -1 || currentTime - lastBackTime > 2000) {
            ToastUtils.showInfoToast(ActivityManager.getActivity(), "再次点击退出应用");
            lastBackTime = currentTime;
        } else {
            super.onBackPressed();
        }
    }
}
