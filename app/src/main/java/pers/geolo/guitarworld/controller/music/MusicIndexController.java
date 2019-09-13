package pers.geolo.guitarworld.controller.music;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import butterknife.BindView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;

/**
 * 音乐主页
 */
public class MusicIndexController extends BaseController {

    @BindView(R.id.title_layout)
    ViewGroup titleLayout;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.music_profile_layout)
    ViewGroup musicProfileLayout;
    @BindView(R.id.music_resource_layout)
    FrameLayout musicResourceLayout;

    @Override
    protected int getLayout() {
        return R.layout.music_index;
    }

    @Override
    public void initView(ViewParams viewParams) {
        // 加载子Controller
        int musicId = (int) viewParams.get("musicId");
        ControllerManager.load(musicProfileLayout, new MusicProfileController(), new ViewParams("musicId", musicId));
        ControllerManager.load(musicResourceLayout, new MusicResourceController(), new ViewParams("musicId", musicId));
        // 初始化titleBar
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                ControllerManager.destroy(MusicIndexController.this);
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
        // 初始化滑动视图
//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                ViewGroup musicResourceViewGroup = (ViewGroup) musicResourceController.getRootView();
//                int changeHeight = musicProfileLayout.getHeight() - titleBar.getHeight();
//                if (oldScrollY < changeHeight && scrollY >= changeHeight) {
//                    titleBar.setTitle(musicProfileController.getMusicName());
//                    View navigator = musicResourceViewGroup.findViewById(R.id.magic_indicator);
//                    if (navigator != null) {
//                        musicResourceController.setPlaceHolderVisible(View.VISIBLE);
//                        musicResourceViewGroup.removeView(navigator);
//                        titleLayout.addView(navigator);
//                    }
//                } else if (scrollY < changeHeight && oldScrollY >= changeHeight) {
//                    titleBar.setTitle("");
//                    View navigator = titleLayout.findViewById(R.id.magic_indicator);
//                    if (navigator != null) {
//                        musicResourceController.setPlaceHolderVisible(View.GONE);
//                        titleLayout.removeView(navigator);
//                        musicResourceViewGroup.addView(navigator, 0);
//                    }
//                }
//                // 设置渐变色
//                setTitleColor(titleBar, changeHeight, scrollY);
//            }
//        });
        // 设置渐变色
        setTitleColor(titleBar, 1, 0);
    }


//    private int getNavigatorHeight() {
//        View navigator = musicResourceController.getRootView().findViewById(R.id.magic_indicator);
//        navigator = navigator == null ? titleLayout.findViewById(R.id.magic_indicator) : navigator;
//        return navigator.getHeight();
//    }

    private void setTitleColor(View view, int criticalValue, int currentValue) {
        float percent = currentValue > criticalValue ? 1 : (float) (currentValue * 1.0 / criticalValue);
        if (percent < 0) {
            percent = 0;
        }
        String hexAlpha = Integer.toHexString((int) (percent * 255));
        if (hexAlpha.length() == 1) {
            hexAlpha = "0" + hexAlpha;
        }
        int colorValue = Color.parseColor("#" + hexAlpha + "FFD39B");
        view.setBackgroundColor(colorValue);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
