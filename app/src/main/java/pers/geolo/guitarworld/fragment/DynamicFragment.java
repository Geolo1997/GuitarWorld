package pers.geolo.guitarworld.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.PublishActivity;
import pers.geolo.guitarworld.base.BaseFragment;

public class DynamicFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.publish_works)
    Button publishWorks;
    @BindView(R.id.liner_list_srl)
    SwipeRefreshLayout linerListSrl;

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }

    @OnClick(R.id.publish_works)
    public void publishWorks() {
       getBaseActivity(). startActivity(PublishActivity.class);
    }
}
