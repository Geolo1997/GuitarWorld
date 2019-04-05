package pers.geolo.guitarworld.ui.fragment;

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
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.presenter.auth.LogoutPresenter;
import pers.geolo.guitarworld.ui.activity.FollowActivity;
import pers.geolo.guitarworld.ui.activity.LoginActivity;
import pers.geolo.guitarworld.ui.activity.MyWorksActivity;
import pers.geolo.guitarworld.ui.activity.ProfileActivity;
import pers.geolo.guitarworld.ui.base.BaseFragment;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.util.ModuleMessage;
import pers.geolo.guitarworld.view.LogoutView;

public class ProfileFragment extends BaseFragment implements LogoutView {

    LogoutPresenter logoutPresenter = new LogoutPresenter();

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.bt_my_profile)
    Button btMyProfile;
    @BindView(R.id.bt_my_works)
    Button btMyWorks;
    @BindView(R.id.bt_my_follower)
    Button btMyFans;
    @BindView(R.id.bt_my_following)
    Button btMyAttention;
    @BindView(R.id.bt_logout)
    Button btLogout;

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        logoutPresenter.bind(this);
        tvUsername.setText(DAOService.getInstance().getCurrentLogInfo().getUsername());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logoutPresenter.unBind();
    }

    @OnClick(R.id.bt_my_profile)
    public void onBtMyProfileClicked() {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(ModuleMessage.CURRENT_USERNAME, CustomContext.getInstance().getLogInfo().getUsername());
        startActivity(intent);
    }

    @OnClick(R.id.bt_my_works)
    public void onBtMyWorksClicked() {
        getBaseActivity().startActivity(MyWorksActivity.class);
    }

    @OnClick({R.id.bt_my_following, R.id.bt_my_follower})
    public void onBtMyAttentionOrMyFansClicked(View view) {
        int id = view.getId();
        Log.d(TAG, String.valueOf(id));
        Intent intent = new Intent(getActivity(), FollowActivity.class);
        intent.putExtra(ModuleMessage.FOLLOW_TAG, id == R.id.bt_my_following ? "following" : "follower");
        intent.putExtra(ModuleMessage.CURRENT_USERNAME, CustomContext.getInstance().getLogInfo().getUsername());
        startActivity(intent);
    }

    @OnClick(R.id.bt_logout)
    public void onLogout() {
        logoutPresenter.logout();
    }

    @Override
    public void toLoginView() {
        getBaseActivity().startActivity(LoginActivity.class);
        getBaseActivity().finish();
    }
}