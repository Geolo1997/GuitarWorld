package pers.geolo.guitarworld.test.adapterfragmentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/29
 */
public class DelegateRecyclerViewAdapter extends RecyclerView.Adapter<DelegateRecyclerViewAdapter.ViewHolder> {

    private BaseDelegate delegate;
    private List<BaseDelegate> subDelegates;

    public DelegateRecyclerViewAdapter(BaseDelegate delegate) {
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


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.delegate_recycler_view_item, viewGroup, false);
        view.setId(View.generateViewId());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int i) {
        BaseDelegate subDelegate = subDelegates.get(i);
        viewholder.itemView
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (delegate.getRootView().findViewById(viewholder.itemView.getId()) != null) {
                            delegate.loadRootFragment(viewholder.itemView.getId(), subDelegate);
                            viewholder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
        viewholder.itemView
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        View delegateView = delegate.getRootView();
                        View subDelegateView = subDelegate.getRootView();
                        if (subDelegateView != null && delegateView.findViewWithTag(subDelegate) != null) {
                            viewholder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return subDelegates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
