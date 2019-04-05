package pers.geolo.guitarworld.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.ui.base.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.list.ListItemView;
import pers.geolo.guitarworld.view.list.ListView;

public abstract class MvpRecyclerViewAdapter<ViewHolderType extends MvpRecyclerViewAdapter.ViewHolder,
        ListItemViewType extends ListItemView>
        extends BaseRecyclerViewAdapter<ViewHolderType>
        implements ListView<ListItemViewType> {

    private List<ListItemViewType> listItemViews;
    private RefreshView refreshView;

    public MvpRecyclerViewAdapter(BaseActivity activity) {
        super(activity);
        listItemViews = new ArrayList<>();
    }

    public void setRefreshView(RefreshView refreshView) {
        this.refreshView = refreshView;
    }

    @NonNull
    @Override
    public ViewHolderType onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolderType viewHolder = super.onCreateViewHolder(viewGroup, i);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderType viewHolderType, int i) {
        if (listItemViews.size() <= i) {
            listItemViews.add(i, (ListItemViewType) viewHolderType);
        } else {
            listItemViews.set(i, (ListItemViewType) viewHolderType);
        }
        onBindItemView(viewHolderType, i);
    }

    public abstract void onBindItemView(ViewHolderType viewHolderType, int i);

    @Override
    public int getListSize() {
        return getItemCount();
    }

    // TODO ---------------更精确的实现方案-----------------------


    @Override
    public ListItemViewType getItemView(int index) {
        return listItemViews.get(index);
    }

    @Override
    public void addItemView() {
        notifyDataSetChanged();
    }

    @Override
    public void addItemView(int index) {
        notifyDataSetChanged();
    }

    @Override
    public void removeItemView(int index) {
        notifyDataSetChanged();
    }

    @Override
    public void clearItemView() {
        notifyDataSetChanged();
    }

    @Override
    public void addAllItemView() {
        notifyDataSetChanged();
    }

    // TODO END---------------更精确的实现方案-----------------------


    @Override
    public void showRefreshing() {
        refreshView.showRefreshing();
    }

    @Override
    public void hideRefreshing() {
        refreshView.hideRefreshing();
    }

    @Override
    public void showToast(String message) {
        getActivity().showToast(message);
    }

    @Override
    public void showLongToast(String message) {
        getActivity().showLongToast(message);
    }

    public class ViewHolder extends BaseRecyclerViewAdapter.TestBaseViewHolder implements ListItemView {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public int getIndex() {
            return getAdapterPosition();
        }
    }
}
