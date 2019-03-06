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

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.bt_logout)
    Button btLogout;
    Unbinder unbinder;
    private String username;

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
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvUsername.setText(username);
        return view;
    }

    @OnClick(R.id.bt_myProfile)
    public void onBtMyProfileClicked() {
        startActivity(MyProfileActivity.class);
    }

    @OnClick(R.id.bt_myWorks)
    public void onBtMyWorksClicked() {
        startActivity(MyWorksActivity.class);
    }

    @OnClick({R.id.bt_myAttention, R.id.bt_myFans})
    public void onBtMyAttentionOrMyFansClicked(View view) {
        int id = view.getId();
        Log.d(TAG, String.valueOf(id));
        Intent intent = new Intent(getActivity(), FollowActivity.class);
        intent.putExtra("tag", id == R.id.bt_myAttention ? "following" : "fans");
        intent.putExtra("permission", "admin");
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @OnClick(R.id.bt_logout)
    public void onLogout() {

        startActivity(LoginActivity.class);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}