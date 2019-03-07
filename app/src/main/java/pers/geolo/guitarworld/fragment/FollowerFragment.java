package pers.geolo.guitarworld.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseFragment;

public class FollowerFragment extends BaseFragment {
    @BindView(R.id.ss)
    TextView ss;

    @Override
    protected int getContentView() {
        return R.layout.fragment_follower;
    }
}
