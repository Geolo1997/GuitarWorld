package pers.geolo.guitarworld.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import butterknife.BindView;
import java.io.InputStream;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.presenter.image.ImageDetailPresenter;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.util.ModuleMessage;
import pers.geolo.guitarworld.view.ImageDetailView;

public class ImageDetailActivity extends BaseActivity implements ImageDetailView {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    ImageDetailPresenter imageDetailPresenter = new ImageDetailPresenter();

    @Override
    protected int getContentView() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageDetailPresenter.bind(this);
        String path = getIntent().getStringExtra(ModuleMessage.IMAGE_PATH);
        imageDetailPresenter.setImagePath(path);
        imageDetailPresenter.loadImage();
    }

    @Override
    public void setImageView(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        runOnUiThread(() -> {
            ivImage.setImageBitmap(bitmap);
        });
    }
}
