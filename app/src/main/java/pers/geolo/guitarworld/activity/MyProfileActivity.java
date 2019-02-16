package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.model.SimpleUser;
import pers.geolo.guitarworld.model.User;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.service.UserService;
import pers.geolo.guitarworld.util.SingletonHolder;

public class MyProfileActivity extends BaseActivity {


    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_birthday)
    EditText etBirthday;
    @BindView(R.id.et_hometown)
    EditText etHometown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        update();
        setProfileEditEnable(false);
    }

    private void setProfileEditEnable(boolean b) {
        etSex.setEnabled(b);
        etBirthday.setEnabled(b);
        etHometown.setEnabled(b);
    }

    protected void update() {
        HttpUtils.getMyProfile(new BaseCallback<User>() {
            @Override
            public void onSuccess(User data) {
                if (data instanceof SimpleUser) {
                    SimpleUser simpleUser = (SimpleUser) data;
                    tvUsername.setText(simpleUser.getUsername());
                    etSex.setText(simpleUser.getSex());
                    etBirthday.setText((CharSequence) simpleUser.getBirthday());
                    etHometown.setText(simpleUser.getHometown());
                }
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, message);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "网络错误！");
                startActivity(NetworkErrorActivity.class);
            }
        });
    }


    @OnClick(R.id.bt_edit)
    public void onBtEditClicked() {
        setProfileEditEnable(true);
    }

    // TODO
    @OnClick(R.id.bt_save)
    public void onBtSaveClicked() {
        User user = new User();
        HttpUtils.saveMyProfile(user, new BaseCallback<Void>() {

            @Override
            public void onSuccess(Void data) {
                Log.d(TAG, "保存成功！");
                setProfileEditEnable(false);
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "保存失败！");
                toast("保存失败！");
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "网络错误！");
                toast("网络错误!");
            }
        });
    }
}
