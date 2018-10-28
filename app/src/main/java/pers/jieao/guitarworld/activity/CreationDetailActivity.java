package pers.jieao.guitarworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import pers.jieao.guitarworld.R;
import pers.jieao.guitarworld.base.BaseActivity;
import pers.jieao.guitarworld.model.Creation;

public class CreationDetailActivity extends BaseActivity {


    @BindView(R.id.tv_authorId_detail)
    TextView tvAuthorId;
    @BindView(R.id.tv_createTime_detail)
    TextView tvCreateTime;
    @BindView(R.id.tv_title_detail)
    TextView tvTitle;
    @BindView(R.id.tv_content_detail)
    TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Creation creation = (Creation) intent.getSerializableExtra("creation");
        tvAuthorId.setText(creation.getAuthorId());
        tvCreateTime.setText(creation.getCreateTime().toString());
        tvTitle.setText(creation.getTitle());
        tvContent.setText(creation.getContent());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_creation_detail;
    }
}
