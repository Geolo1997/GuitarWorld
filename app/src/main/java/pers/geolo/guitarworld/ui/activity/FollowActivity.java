package pers.geolo.guitarworld.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.ui.fragment.FollowerFragment;
import pers.geolo.guitarworld.ui.fragment.FollowingFragment;

public class FollowActivity extends BaseActivity {

    protected Button selectedButton;

    protected Bundle bundle;

    @Override
    protected int getContentView() {
        return R.layout.activity_follow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String currentUsername = DAOService.getInstance().getCurrentLogInfo().getUsername();
        bundle = new Bundle();
        bundle.putString("username", currentUsername);
        // 设置默认碎片
        String tag = getIntent().getStringExtra("tag");
        selectedButton = findViewById("following".equals(tag) ? R.id.bt_following : R.id.bt_follower);
        onViewClicked(selectedButton);
    }


    @OnClick({R.id.bt_following, R.id.bt_follower})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_following:
                setFragment(R.id.ll_fragment, FollowingFragment.class, bundle);
                break;
            case R.id.bt_follower:
                setFragment(R.id.ll_fragment, FollowerFragment.class, bundle);
                break;
            default:
        }
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.white));
        selectedButton = findViewById(view.getId());
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.colorPrimary));
    }
}
