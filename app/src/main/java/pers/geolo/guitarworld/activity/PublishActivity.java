package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;

import java.util.Date;

public class PublishActivity extends BaseActivity {

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_publish)
    public void publish() {
        Works works = new Works();
        String username = DAOService.getInstance().getCurrentLogInfo().getUsername();
        works.setAuthor(username);
        works.setCreateTime(new Date());
        works.setTitle(etTitle.getText().toString());
        works.setContent(etContent.getText().toString());

        HttpService.getInstance().getAPI(WorksAPI.class)
                .publishWorks(works)
                .enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        finish();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }
}
