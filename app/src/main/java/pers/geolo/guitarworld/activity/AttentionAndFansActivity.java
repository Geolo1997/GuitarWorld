package pers.geolo.guitarworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.fragment.AttentionFragment;
import pers.geolo.guitarworld.fragment.FansFragment;
import pers.geolo.guitarworld.util.SingletonHolder;

public class AttentionAndFansActivity extends BaseActivity {

    protected Button selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_and_fans);
        ButterKnife.bind(this);
        // 设置默认碎片
        String tag = getIntent().getStringExtra("tag");
        selectedButton = findViewById("attention".equals(tag) ? R.id.bt_attention : R.id.bt_fans);
        // 将选中按钮变色
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.bt_attention)
    public void onBtAttentionClicked() {
        setFragment(R.id.ll_fragment, SingletonHolder.getInstance(AttentionFragment.class));
    }

    @OnClick(R.id.bt_fans)
    public void onBtFansClicked() {
        setFragment(R.id.ll_fragment, SingletonHolder.getInstance(FansFragment.class));
    }


    @OnClick({R.id.bt_attention, R.id.bt_fans})
    public void onViewClicked(View view) {
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.white));
        selectedButton = findViewById(view.getId());
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.colorPrimary));
    }
}
