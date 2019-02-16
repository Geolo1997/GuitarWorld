package pers.geolo.guitarworld.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.PublishActivity;

public class DynamicFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }

    @OnClick(R.id.publishWorks)
    public void publishWorks() {
        startActivity(PublishActivity.class);
    }
}
