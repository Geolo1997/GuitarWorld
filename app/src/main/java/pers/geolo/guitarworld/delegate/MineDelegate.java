package pers.geolo.guitarworld.delegate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.dao.DataBaseManager;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.listener.LogoutListener;
import pers.geolo.guitarworld.util.ActivityUtils;

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
        return R.layout.delegate_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        tvUsername.setText(DataBaseManager.getInstance().getCurrentLogInfo().getUsername());
    }

    @OnClick(R.id.bt_my_profile)
    public void onBtMyProfileClicked() {
        Intent intent = new Intent(getActivity(), ProfileDelegate.class);
        intent.putExtra("username", AuthModel.getCurrentLoginUser().getUsername());
        startActivity(intent);
    }

    @OnClick(R.id.bt_my_works)
    public void onBtMyWorksClicked() {
        ActivityUtils.startActivity(getBaseActivity(), MyWorksActivity.class);
    }

    @OnClick({R.id.bt_my_following, R.id.bt_my_follower})
    public void onBtMyAttentionOrMyFansClicked(View view) {
        int id = view.getId();
        Log.d(TAG, String.valueOf(id));

        Bundle bundle = new Bundle();
        bundle.putString("username", AuthModel.getCurrentLoginUser().getUsername());
        bundle.putString("follow_tag", id == R.id.bt_my_following ? "following" : "follower");
        startActivity(FollowDelegate.class, bundle, null);
    }

    @OnClick(R.id.bt_logout)
    public void onLogout() {
        AuthModel.logout(AuthModel.getCurrentLoginUser().getUsername(),
                new LogoutListener() {
                    @Override
                    public void onSuccess() {
                        getActivity().finish();
                    }
                });
    }
}
