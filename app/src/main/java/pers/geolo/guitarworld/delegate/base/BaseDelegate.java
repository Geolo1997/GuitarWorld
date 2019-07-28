package pers.geolo.guitarworld.delegate.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.*;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.gyf.immersionbar.ImmersionBar;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import pers.geolo.guitarworld.entity.event.Event;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-04
 */
public abstract class BaseDelegate extends SwipeBackFragment {

    private Unbinder unbinder;
    private View rootView;

    public abstract Object getLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        inflateView(inflater, container, savedInstanceState);
        onBindView(savedInstanceState, rootView);
        // 设置透明状态栏
        setStatusBarFullTransparent();
        // 设置状态栏边距
        setStatusBarPadding();
        // 设置状态栏字体黑色
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .init();
        // 注册EventBus
        EventBus.getDefault().register(this);
        return rootView;
    }

    public void inflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        if (getLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) getLayout(), container, false);
        } else if (getLayout() instanceof View) {
            rootView = (View) getLayout();
        } else {
            throw new ClassCastException("getLayout() type must be int or View!");
        }
        unbinder = ButterKnife.bind(this, rootView);
    }


    public SupportActivity getContainerActivity() {
        return (SupportActivity) _mActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

    public View getRootView() {
        return rootView;
    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getContainerActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getContainerActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    protected void setStatusBarPadding() {
        int statusBarHeight = getStatusBarHeight(getContainerActivity());
        View view = getStatueBarTopView();
        if (view != null) {
            // 测量原控件高度
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            int height = view.getMeasuredHeight();
            // 修改控件高度
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = height + statusBarHeight;
            view.setLayoutParams(params);
            // 增加控件顶部内边距
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop() + statusBarHeight,
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    protected View getStatueBarTopView() {
        return null;
    }

    @Subscribe
    public void onEvent(Event event) {

    }
}
