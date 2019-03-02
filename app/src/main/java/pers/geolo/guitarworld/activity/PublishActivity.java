package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.service.UserService;
import pers.geolo.guitarworld.util.SingletonHolder;

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
    }

    @OnClick(R.id.bt_publish)
    public void publish() {
        Works works = new Works();
        String username = SingletonHolder.getInstance(UserService.class).getUsername();
        works.setAuthor(username);
        works.setCreateTime(new Date());
        works.setTitle(etTitle.getText().toString());
        works.setContent(etContent.getText().toString());
        HttpUtils.publishWorks(works, new BaseCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                toast("发布成功！");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }

            @Override
            public void onError(String message) {
                toast(message);
            }

            @Override
            public void onFailure() {
                toast("失败！");
            }
        });
    }
}
