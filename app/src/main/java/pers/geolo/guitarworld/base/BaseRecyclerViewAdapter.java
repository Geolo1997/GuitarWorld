package pers.geolo.guitarworld.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import java.lang.reflect.InvocationTargetException;

import pers.geolo.guitarworld.util.GenericUtils;
import pers.geolo.guitarworld.view.base.LoadingView;
import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;
import pers.geolo.guitarworld.view.list.ListItemView;
import pers.geolo.guitarworld.view.list.OnBindViewListener;
import pers.geolo.guitarworld.view.list.SizeListener;

public abstract class BaseRecyclerViewAdapter<ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder>
        extends RecyclerView.Adapter<ViewHolder> {

    private Class viewHolderType;
    private BaseActivity activity;
    private SizeListener sizeListener = () -> 0;
    private OnBindViewListener onBindViewListener;

    public BaseRecyclerViewAdapter(BaseActivity activity) {
        this.activity = activity;
        viewHolderType = GenericUtils.getActualGenericExtended(this, 0);
    }

    public void setSizeListener(SizeListener sizeListener) {
        this.sizeListener = sizeListener;
    }

    public void setOnBindViewListener(OnBindViewListener onBindViewListener) {
        this.onBindViewListener = onBindViewListener;
    }

    public BaseActivity getActivity() {
        return activity;
    }

    public abstract int getItemViewId();

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        onBindViewListener.onBindView(viewHolder, i);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getItemViewId(), viewGroup, false);
        ViewHolder viewHolder = null;
        try {
            viewHolder = (ViewHolder) GenericUtils.newInstance(viewHolderType, new Class[]{getClass(), View.class},
                    new Object[]{this, view});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return sizeListener.getSize();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder implements ListItemView, LoadingView, RefreshView,
            ToastView {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void showLoading() {
        }

        @Override
        public void hideLoading() {

        }

        @Override
        public void showToast(String message) {
            getActivity().showToast(message);
        }

        @Override
        public void showLongToast(String message) {
            getActivity().showLongToast(message);
        }

        @Override
        public void showRefreshing() {

        }

        @Override
        public void hideRefreshing() {

        }

        @Override
        public int getIndex() {
            return getAdapterPosition();
        }

        @Override
        public void remove() {
            int index = getIndex();
            notifyItemRemoved(index);
            if (index != getItemCount()) {
                notifyItemRangeChanged(index, getItemCount() - index);
            }
        }
    }
}
