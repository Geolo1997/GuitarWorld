package pers.geolo.guitarworld.ui;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/24
 */
public class NestViewPager extends ViewPager {

    public NestViewPager(Context context) {
        super(context);
    }

    public NestViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = null;
        PagerAdapter adapter = getAdapter();
        if (adapter instanceof FragmentPagerAdapter) {
            child = ((FragmentPagerAdapter) adapter).getItem(getCurrentItem()).getView();
        }
        if (child != null) {
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int height = child.getMeasuredHeight();
            int newHighMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            heightMeasureSpec = Math.max(heightMeasureSpec, newHighMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

