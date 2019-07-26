package pers.geolo.guitarworld.delegate.music;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;
import butterknife.BindView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.SwipeBackDelegate;

/**
 * 音乐主页
 */
public class MusicIndexDelegate extends SwipeBackDelegate {

    private static final String ID = "ID";

    @BindView(R.id.title_layout)
    ViewGroup titleLayout;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.music_profile_layout)
    ViewGroup musicProfileLayout;

    MusicProfileDelegate musicProfileDelegate;
    MusicResourceDelegate musicResourceDelegate;

    @Override
    public Object getLayout() {
        return R.layout.music_index;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    public static MusicIndexDelegate newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ID, id);
        MusicIndexDelegate fragment = new MusicIndexDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTitleBar();
        initScrollView();
        // 获取musicId
        int musicId = getArguments().getInt(ID);
        // 设置渐变色
        setTitleColor(titleBar, 1, 0);
        // 设置子delegate
        musicProfileDelegate = MusicProfileDelegate.newInstance(musicId);
        loadRootFragment(R.id.music_profile_layout, musicProfileDelegate);
        musicResourceDelegate = MusicResourceDelegate.newInstance(musicId);
        loadRootFragment(R.id.music_resource_layout, musicResourceDelegate);

    }

    private void initTitleBar() {
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                pop();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                Toast.makeText(getContext(), "option", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initScrollView() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                ViewGroup musicResourceViewGroup = (ViewGroup) musicResourceDelegate.getRootView();
                int changeHeight = musicProfileLayout.getHeight() - titleLayout.getHeight();
                if (oldScrollY < changeHeight && scrollY >= changeHeight) {
                    titleBar.setTitle(musicProfileDelegate.getMusicName());
                    View navigator = musicResourceViewGroup.findViewById(R.id.magic_indicator);
                    if (navigator != null) {
                        musicResourceViewGroup.removeView(navigator);
                        titleLayout.addView(navigator);
                    }
                } else if (scrollY < changeHeight && oldScrollY >= changeHeight) {
                    titleBar.setTitle("");
                    View navigator = titleLayout.findViewById(R.id.magic_indicator);
                    if (navigator != null) {
                        titleLayout.removeView(navigator);
                        musicResourceViewGroup.addView(navigator, 0);
                    }
                }
                // 设置渐变色
                setTitleColor(titleBar, changeHeight, scrollY);
            }
        });
    }

    private int getNavigatorHeight() {
        View navigator = musicResourceDelegate.getRootView().findViewById(R.id.magic_indicator);
        navigator = navigator == null ? titleLayout.findViewById(R.id.magic_indicator) : navigator;
        return navigator.getHeight();
    }

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
