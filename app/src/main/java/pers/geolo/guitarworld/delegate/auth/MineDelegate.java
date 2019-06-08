package pers.geolo.guitarworld.delegate.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.HashMap;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.user.FollowDelegate;
import pers.geolo.guitarworld.delegate.user.ProfileDelegate;
import pers.geolo.guitarworld.delegate.works.WorksListDelegate;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.entity.DataListener;

public class MineDelegate extends BaseDelegate {

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
    public Object getLayout() {
        return R.layout.delegate_mine;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        tvUsername.setText(AuthModel.getCurrentLoginUser().getUsername());
    }

    @OnClick(R.id.bt_my_profile)
    public void onBtMyProfileClicked() {
        getDelegateActivity().start(ProfileDelegate.newInstance(AuthModel.getCurrentLoginUser().getUsername()));
    }

    @OnClick(R.id.bt_my_works)
    public void onBtMyWorksClicked() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("author", AuthModel.getCurrentLoginUser().getUsername());
        getDelegateActivity().start(WorksListDelegate.newInstance(filter));
    }

    @OnClick({R.id.bt_my_following, R.id.bt_my_follower})
    public void onBtMyAttentionOrMyFansClicked(View view) {
        int id = view.getId();
        getDelegateActivity().start(FollowDelegate.newInstance(AuthModel.getCurrentLoginUser().getUsername(),
                id == R.id.bt_my_following ? "following" : "follower"));
    }

    @OnClick(R.id.bt_logout)
    public void onLogout() {
        AuthModel.logout(new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                getDelegateActivity().startWithPop(new LoginDelegate());
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
