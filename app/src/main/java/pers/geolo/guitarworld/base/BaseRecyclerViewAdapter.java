package pers.geolo.guitarworld.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import java.lang.reflect.InvocationTargetException;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

import pers.geolo.guitarworld.util.GenericUtils;
import pers.geolo.guitarworld.view.base.BaseView;
import pers.geolo.guitarworld.view.base.LoadingView;
import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;

public abstract class BaseRecyclerViewAdapter<ListType, ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder>
        extends RecyclerView.Adapter<ViewHolder> {


    private final List<ListType> DATA_LIST;
    private Class viewHolderType;
    private BaseActivity activity;


    public BaseRecyclerViewAdapter(BaseActivity activity) {
        this.activity = activity;
        viewHolderType = GenericUtils.getActualGenericExtended(this, 1);
        DATA_LIST = new ArrayList<>();
    }


    public BaseActivity getActivity() {
        return activity;
    }

    public List<ListType> getDataList() {
        return DATA_LIST;
    }

    public void setDataList(List<ListType> dataList) {
        DATA_LIST.clear();
        DATA_LIST.addAll(dataList);
        notifyDataSetChanged();
    }


    public abstract int getItemViewId();

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
        return DATA_LIST.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder implements LoadingView, RefreshView, ToastView {

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
    }
}
