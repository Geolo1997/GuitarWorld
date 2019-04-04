package pers.geolo.guitarworld.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

public abstract class BaseRecyclerViewAdapter<ViewHolderType extends BaseRecyclerViewAdapter.TestBaseViewHolder>
        extends RecyclerView.Adapter<ViewHolderType> {

    private BaseActivity activity;

    public BaseRecyclerViewAdapter(BaseActivity activity) {
        this.activity = activity;
    }

    public BaseActivity getActivity() {
        return activity;
    }

    @NonNull
    @Override
    public ViewHolderType onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getItemViewId(), viewGroup, false);
        return getViewHolder(view);
    }

    protected abstract int getItemViewId();

    protected abstract ViewHolderType getViewHolder(View view);


    public class TestBaseViewHolder extends RecyclerView.ViewHolder {

        public TestBaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
