package pers.geolo.guitarworld.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.MyProfileActivity;
import pers.geolo.guitarworld.activity.NetworkErrorActivity;
import pers.geolo.guitarworld.base.BaseFragment;
import pers.geolo.guitarworld.model.User;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.util.LoginManager;
import pers.geolo.guitarworld.util.SingletonHolder;

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.bt_myProfile)
    Button btMyProfile;
    @BindView(R.id.bt_myWorks)
    Button btMyWorks;
    @BindView(R.id.bt_myFans)
    Button btMyFans;
    @BindView(R.id.bt_myAttention)
    Button btMyAttention;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // 设置用户名
        String username = SingletonHolder.getInstance(LoginManager.class).getLoginUser().getUsername();
        tvUsername.setText(username);
        return view;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }


    //    @OnClick(R.id.bt_myProfile)
    public void getMyProfile() {
        startActicity(MyProfileActivity.class);

    }


    @OnClick(R.id.bt_myProfile)
    public void onBtMyProfileClicked() {
        startActicity(MyProfileActivity.class);
    }

    @OnClick(R.id.bt_myWorks)
    public void onBtMyWorksClicked() {

    }

    @OnClick(R.id.bt_myFans)
    public void onBtMyFansClicked() {
    }

    @OnClick(R.id.bt_myAttention)
    public void onBtMyAttentionClicked() {
    }
}