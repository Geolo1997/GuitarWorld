package pers.geolo.guitarworld.util;

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/26
 */
public class WidgetUtils {

    /**
     * 测量控件长宽，用于在绘图未结束(如onCreate方法中)无法直接获得宽高时
     *
     * @param view 需要测量的控件
     */
    public static void measure(View view, ViewTreeObserver.OnPreDrawListener listener) {
////        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
////        int heigth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
////        view.measure(width, heigth);
//        final Integer[] width = new Integer[1];
//        final Integer[] heigth = new Integer[1];
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnPreDrawListener(listener);
    }
}
