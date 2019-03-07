package pers.geolo.guitarworld.fragment;

import android.content.Intent;
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
import pers.geolo.guitarworld.activity.FollowActivity;
import pers.geolo.guitarworld.activity.LoginActivity;
import pers.geolo.guitarworld.activity.MyProfileActivity;
import pers.geolo.guitarworld.activity.MyWorksActivity;
import pers.geolo.guitarworld.base.BaseFragment;
import pers.geolo.guitarworld.dao.DAOService;

public class ProfileFragment extends BaseFragment {



    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.bt_my_profile)
    Button btMyProfile;
    @BindView(R.id.bt_my_works)
    Button btMyWorks;
    @BindView(R.id.bt_my_fans)
    Button btMyFans;
    @BindView(R.id.bt_my_attention)
    Button btMyAttention;
    @BindView(R.id.bt_logout)
    Button btLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        tvUsername.setText(DAOService.getInstance().getCurrentLogInfo().getUsername());
        return view;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }

    @OnClick(R.id.bt_my_profile)
    public void onBtMyProfileClicked() {
        getBaseActivity().startActivity(MyProfileActivity.class);
    }

    @OnClick(R.id.bt_my_works)
    public void onBtMyWorksClicked() {
        getBaseActivity().startActivity(MyWorksActivity.class);
    }

    @OnClick({R.id.bt_my_attention, R.id.bt_my_fans})
    public void onBtMyAttentionOrMyFansClicked(View view) {
        int id = view.getId();
        Log.d(TAG, String.valueOf(id));
        Intent intent = new Intent(getActivity(), FollowActivity.class);
        intent.putExtra("tag", id == R.id.bt_my_attention ? "following" : "fans");
        intent.putExtra("permission", "admin");
        intent.putExtra("username", DAOService.getInstance().getCurrentLogInfo().getUsername());
        startActivity(intent);
    }

    @OnClick(R.id.bt_logout)
    public void onLogout() {
        getBaseActivity().startActivity(LoginActivity.class);
        getActivity().finish();
    }

}