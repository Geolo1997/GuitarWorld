package pers.geolo.guitarworld.test.adapterfragmentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/29
 */
public class DelegateBaseAdapter extends BaseAdapter {

    private BaseDelegate delegate;
    private List<BaseDelegate> subDelegates;

    public DelegateBaseAdapter(BaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void setSubDelegates(List<BaseDelegate> subDelegates) {
        this.subDelegates = subDelegates;
        notifyDataSetChanged();
    }

    public void addSubDelegate(BaseDelegate subDelegate) {
        if (subDelegates == null) {
            subDelegates = new ArrayList<>();
        }
        subDelegates.add(subDelegate);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return subDelegates.size();
    }

    @Override
    public Object getItem(int position) {
        return subDelegates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(delegate.getContext())
                    .inflate(R.layout.delegate_base_adapter_item, null);
            convertView.setId(View.generateViewId());

            ViewTreeObserver observer = convertView.getViewTreeObserver();
            View finalConvertView = convertView;
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (finalConvertView instanceof ViewGroup) {
                        ((ViewGroup) finalConvertView).removeAllViews();
                    }
                    if (delegate.getRootView().findViewById(finalConvertView.getId()) != null) {
                        BaseDelegate subDelegate = subDelegates.get(position);
                        // 加载子delegate
                        delegate.loadRootFragment(finalConvertView.getId(), subDelegate);
//                        // 重新设置宽高
//                        View subDelegateRootView = subDelegate.getRootView();
//                        ViewTreeObserver subDelegateObserver = subDelegateRootView.getViewTreeObserver();
//                        subDelegateObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                            @Override
//                            public void onGlobalLayout() {
//                                int width = subDelegate.getRootView().getMeasuredWidth();
//                                int height = subDelegate.getRootView().getMeasuredHeight();
//                                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
//                                finalConvertView.setLayoutParams(params);
//                                if (width != 0 && height != 0) {
//                                    subDelegateObserver.removeOnGlobalLayoutListener(this);
//                                }
//                            }
//                        });
                        if (observer.isAlive()) {
                            observer.removeOnGlobalLayoutListener(this);
                        }
                    }
                }
            });
//            convertView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//                @Override
//                public void onViewAttachedToWindow(View v) {
//                    if (finalConvertView instanceof ViewGroup) {
//                        ((ViewGroup) finalConvertView).removeAllViews();
//                    }
//                    BaseDelegate subDelegate = subDelegates.get(position);
//                    delegate.loadRootFragment(finalConvertView.getId(), subDelegate);
//                }
//
//                @Override
//                public void onViewDetachedFromWindow(View v) {
//
//                }
//            });
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    BaseDelegate subDelegate = subDelegates.get(position);
                    if (subDelegate.getRootView() != null) {
                        int width = subDelegate.getRootView().getMeasuredWidth();
                        int height = subDelegate.getRootView().getMeasuredHeight();
                        AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, height);
                        finalConvertView.setLayoutParams(params);
                        if (width != 0 && height != 0) {
                            finalConvertView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                }
            });
        }

        return convertView;
    }
}
