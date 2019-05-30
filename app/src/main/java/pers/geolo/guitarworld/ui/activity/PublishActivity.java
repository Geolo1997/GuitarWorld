package pers.geolo.guitarworld.ui.activity;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.network.ImageUploader;
import pers.geolo.guitarworld.presenter.image.UploadImagePresenter;
import pers.geolo.guitarworld.presenter.works.PublishWorksPresenter;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.ui.temp.PermissionCallback;
import pers.geolo.guitarworld.ui.temp.PermissionRequestCode;
import pers.geolo.guitarworld.util.AndroidSystemUtils;
import pers.geolo.guitarworld.view.PublishWorksView;
import pers.geolo.guitarworld.view.UploadImageView;

public class PublishActivity extends BaseActivity implements PublishWorksView, UploadImageView {

    public static final int MAX_IMAGE_COUNT = 3;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_upload)
    Button btUpload;
    @BindView(R.id.ll_images)
    LinearLayout llImages;
    private PublishWorksPresenter publishWorksPresenter = new PublishWorksPresenter();
    private UploadImagePresenter uploadImagePresenter = new UploadImagePresenter();
    private int imageCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        publishWorksPresenter.bind(this);
        uploadImagePresenter.bind(this);
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
    public String getContent() {
        return etContent.getText().toString().trim();
    }

    @Override
    public void toMainView() {
        finish();
    }

    @OnClick(R.id.bt_upload)
    public void onViewClicked() {
        if (imageCount >= MAX_IMAGE_COUNT) {
            showToast("最多上传" + MAX_IMAGE_COUNT + "张图片");
        } else {
            requestPermission(PermissionRequestCode.USE_PHOTO_ALBUM, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    new PermissionCallback() {
                        @Override
                        public void onSuccess() {

                            AndroidSystemUtils.getLocalImage(PublishActivity.this, file -> {
                                ImageUploader.upload(file, data -> {
                                    publishWorksPresenter.addImage(data);
                                    addImage(file.getAbsolutePath());
                                }, null);
                            }, null);
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
        }
    }

    @Override
    public void addImage(String imagePath) {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 300);
        imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        imageView.setLayoutParams(params);
        llImages.addView(imageView);
        imageCount++;
    }

    @Override
    public String getLocalImagePath() {
        return null;
    }
}
