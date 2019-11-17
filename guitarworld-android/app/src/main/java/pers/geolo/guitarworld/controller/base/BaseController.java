package pers.geolo.guitarworld.controller.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import pers.geolo.guitarworld.microview.ControllerStack;
import pers.geolo.guitarworld.microview.ViewManager;
import pers.geolo.guitarworld.microview.ViewParam;
import pers.geolo.guitarworld.util.MicroviewTransferUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-04
 */
public abstract class BaseController extends pers.geolo.guitarworld.microview.BaseController {

//    private Unbinder unbinder;
//    private View rootView;
//
//    private boolean isDark = true;

    private Bundle args;

    public abstract Object getLayoutView();

    @Override
    protected int getLayout() {
        return (int) getLayoutView();
    }

    @Override
    public void initView(ViewParam viewParam) {
        onBindView(null, getView());
    }

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        inflateView(inflater, container);
//        onBindView(savedInstanceState, rootView);
//        // 设置透明状态栏
//        setStatusBarFullTransparent();
//        // 设置状态栏边距
//        setStatusBarPadding();
//        // 设置状态栏字体黑色
//        if (isDark) {
//            ImmersionBar.with(this)
//                    .statusBarDarkFont(true)
//                    .init();
//        }
//        // 注册EventBus
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
//        return rootView;
//    }
//
//    public void inflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
//        if (getLayoutView() instanceof Integer) {
//            rootView = inflater.inflate((Integer) getLayoutView(), container, false);
//        } else if (getLayoutView() instanceof View) {
//            rootView = (View) getLayoutView();
//        } else {
//            throw new ClassCastException("getLayoutView() type must be int or View!");
//        }
//        // 为根视图生成tag
//        rootView.setTag(this);
//        unbinder = ButterKnife.bind(this, rootView);
//    }
//
//
//    public SupportActivity getContainerActivity() {
//        SupportActivity activity = (SupportActivity) _mActivity;
//        if (activity == null) {
//            activity = this.activity;
//        }
//        return activity;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (unbinder != null) {
//            unbinder.unbind();
//        }
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//    }
//
//    public View getRootView() {
//        return rootView;
//    }
//
//    /**
//     * 全透状态栏
//     */
//    protected void setStatusBarFullTransparent() {
//        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
//            Window window = getContainerActivity().getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
//            getContainerActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //虚拟键盘也透明
//            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//    }
//
//    public static int getStatusBarHeight(Context context) {
//        Resources resources = context.getResources();
//        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
//        int height = resources.getDimensionPixelSize(resourceId);
//        return height;
//    }
//
//
//    protected void setStatusBarPadding() {
//        int statusBarHeight = getStatusBarHeight(getContainerActivity());
//        View view = getStatueBarTopView();
//        if (view != null) {
//            // 测量原控件高度
//            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            view.measure(w, h);
//            int height = view.getMeasuredHeight();
//            // 修改控件高度
//            ViewGroup.LayoutParams params = view.getLayoutParams();
//            params.height = height + statusBarHeight;
//            view.setLayoutParams(params);
//            // 增加控件顶部内边距
//            view.setPadding(view.getPaddingLeft(),
//                    view.getPaddingTop() + statusBarHeight,
//                    view.getPaddingRight(), view.getPaddingBottom());
//        }
//    }
//
//    public void setStatusBarDark(boolean isDark) {
//        this.isDark = isDark;
//    }
//
//    protected View getStatueBarTopView() {
//        return null;
//    }
//
//    @Subscribe
//    public void onEvent(Event event) {
//
//    }
//
//    @Nullable
//    @Override
//    public Context getContext() {
//        Context context = super.getContext();
//        if (context == null) {
//            context = rootView.getContext();
//        }
//        return context;
//    }
//
//    private SupportActivity activity;
//
//    public void setActivity(SupportActivity activity) {
//        this.activity = activity;
//    }

    public void setArguments(Bundle bundle) {
        args = bundle;
    }

    public void start(BaseController toFragment) {
        MicroviewTransferUtils.start(toFragment);
    }

    public void loadRootFragment(int containerId, BaseController toFragment) {
        MicroviewTransferUtils.load(getView().findViewById(containerId), toFragment);
    }

    public Bundle getArguments() {
        return args;
    }

    public MainActivity getActivity() {
        return (MainActivity) ViewManager.getActivity();
    }

    public MainActivity getContainerActivity() {
        return getActivity();
    }

    @Override
    public void onBackPressed() {
        ViewManager.destroy(this);
    }

    public Context getContext() {
        return ViewManager.getActivity();
    }

    public void startWithPop(BaseController controller) {
        MicroviewTransferUtils.start(controller);
        ViewManager.destroy(this);
    }

    public void loadMultipleRootFragment(int id, int count, BaseController[] controllers) {
        ViewGroup container = (ViewGroup) getView().findViewById(id);
        for (int i = 0; i < count; i++) {
            MicroviewTransferUtils.load(container, controllers[i]);
        }
    }

    public void showHideFragment(int id, BaseController controller, BaseController lastController) {
        ViewGroup container = (ViewGroup) getView().findViewById(id);
        ControllerStack controllerStack = ViewManager.getControllerStack(container);
        int newI = 0, oldI = 0;
        for (int i = 0; i < controllerStack.size(); i++) {
            if (controllerStack.get(i) == controller) {
                controller.getView().setVisibility(View.VISIBLE);
                newI = i;
            }
            if (controllerStack.get(i) == lastController) {
                lastController.getView().setVisibility(View.GONE);
                oldI = i;
            }
        }
        controllerStack.set(newI, lastController);
        controllerStack.set(oldI, controller);

    }

    protected View getStatueBarTopView() {
        return null;
    }

    public void pop() {
        ViewManager.destroy(this);
    }

    public View getRootView() {
        return getView();
    }
}
