package pers.geolo.guitarworld.test.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.model.FileModel;

public class ImageDetailDelegate extends BaseDelegate {

    private static final String IMAGE_PATH = "IMAGE_PATH";

    @BindView(R.id.iv_image)
    ImageView ivImage;

    FileModel fileModel = BeanFactory.getBean(FileModel.class);

    public static ImageDetailDelegate newInstance(String imagePath) {
        Bundle args = new Bundle();
        args.putString(IMAGE_PATH, imagePath);
        ImageDetailDelegate fragment = new ImageDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_image_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String path = bundle.getString(IMAGE_PATH);
            fileModel.loadImage(path, new FileListener<Bitmap>(){
                @Override
                public void onProgress(long currentLength, long totalLength) {

                }

                @Override
                public void onFinish(Bitmap bitmap) {
                    ivImage.setImageBitmap(bitmap);
                }

                @Override
                public void onError(String message) {

                }
            });
        }
    }
}
