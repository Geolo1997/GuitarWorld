package pers.geolo.guitarworld.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.ui.fragment.FollowerFragment;
import pers.geolo.guitarworld.ui.fragment.FollowingFragment;
import pers.geolo.guitarworld.util.ModuleMessage;

public class FollowActivity extends BaseActivity {

    protected Button selectedButton;

    protected Bundle bundle = new Bundle();

    @Override
    protected int getContentView() {
        return R.layout.activity_follow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String currentUsername = intent.getStringExtra(ModuleMessage.CURRENT_USERNAME);
        bundle.putString(ModuleMessage.CURRENT_USERNAME, currentUsername);
        // 设置默认碎片
        String tag = getIntent().getStringExtra(ModuleMessage.FOLLOW_TAG);
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
