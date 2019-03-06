package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.fragment.FollowerFragment;
import pers.geolo.guitarworld.fragment.FollowingFragment;

public class FollowActivity extends BaseActivity {

    protected Button selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_and_fans);
        ButterKnife.bind(this);
        // 设置默认碎片
        String tag = getIntent().getStringExtra("tag");
        selectedButton = findViewById("following".equals(tag) ? R.id.bt_attention : R.id.bt_fans);
        // 将选中按钮变色
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.colorPrimary));
    }

    @OnClick({R.id.bt_attention, R.id.bt_fans})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_attention:
                setFragment(R.id.ll_fragment, FollowingFragment.class);
                break;
            case R.id.bt_fans:
                setFragment(R.id.ll_fragment, FollowerFragment.class);
                break;
            default:
        }
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.white));
        selectedButton = findViewById(view.getId());
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.colorPrimary));
    }
}
