package pers.geolo.guitarworld.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.presenter.works.PublishWorksPresenter;
import pers.geolo.guitarworld.view.PublishWorksView;

public class PublishActivity extends BaseActivity implements PublishWorksView {

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;

    private PublishWorksPresenter publishWorksPresenter = new PublishWorksPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        publishWorksPresenter.bind(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_publish;
    }

    @OnClick(R.id.bt_publish)
    public void publish() {
        publishWorksPresenter.publishWorks();
    }


    @Override
    public String getWorksTitle() {
        return etTitle.getText().toString().trim();
    }

    @Override
    public Object getContent() {
        return etContent.getText().toString().trim();
    }

    @Override
    public void toMainView() {
        finish();
    }
}
