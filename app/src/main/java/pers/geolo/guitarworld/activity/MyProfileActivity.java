package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.util.ViewUtils;

public class MyProfileActivity extends BaseActivity {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_birthday)
    EditText etBirthday;
    @BindView(R.id.et_hometown)
    EditText etHometown;
    @BindView(R.id.edit_texts)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 更新个人资料
        updateProfile();
        // 禁用编辑控件
        ViewUtils.setViewGroupEnabled(linearLayout, false);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_profile;
    }

    protected void updateProfile() {
        HttpService.getInstance()
                .getAPI(UserAPI.class)
                .getMyProfile()
                .enqueue(new BaseCallback<User>() {
                    @Override
                    public void onSuccess(User responseData) {
                        tvUsername.setText(responseData.getUsername());
                        etEmail.setText(responseData.getEmail());
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                    }

                    @Override
                    public void onFailure() {
                        startActivity(NetworkErrorActivity.class);
                    }
                });
    }


    @OnClick(R.id.bt_edit)
    public void onBtEditClicked() {
        ViewUtils.setViewGroupEnabled(linearLayout, true);
    }

    // TODO
    @OnClick(R.id.bt_save)
    public void onBtSaveClicked() {
        LogInfo logInfo = DAOService.getInstance().getCurrentLogInfo();
        User user = new User();
        user.setUsername(logInfo.getUsername());
        user.setPassword(logInfo.getPassword());
        user.setEmail(etEmail.getText().toString());
        HttpService.getInstance().getAPI(UserAPI.class)
                .saveMyProfile(user)
                .enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        ViewUtils.setViewGroupEnabled(linearLayout, false);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        showToast("保存失败！");
                    }

                    @Override
                    public void onFailure() {
                        startActivity(NetworkErrorActivity.class);
                    }
                });
    }
}
