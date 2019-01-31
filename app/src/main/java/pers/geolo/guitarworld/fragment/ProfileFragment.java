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
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.AttentionAndFansActivity;
import pers.geolo.guitarworld.activity.MyWorksActivity;
import pers.geolo.guitarworld.activity.MyProfileActivity;
import pers.geolo.guitarworld.base.BaseFragment;
import pers.geolo.guitarworld.util.LoginManager;
import pers.geolo.guitarworld.util.SingletonHolder;

public class ProfileFragment extends BaseFragment {

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
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // 设置用户名
        username = SingletonHolder.getInstance(LoginManager.class).getLoginUser().getUsername();
        tvUsername.setText(username);
        return view;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
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
        Intent intent = new Intent(getActivity(), AttentionAndFansActivity.class);
        intent.putExtra("tag", id == R.id.bt_myAttention ? "attention" : "fans");
        intent.putExtra("permission", "admin");
        intent.putExtra("username", username);
        startActivity(intent);
    }
}