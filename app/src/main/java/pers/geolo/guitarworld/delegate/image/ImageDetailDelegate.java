package pers.geolo.guitarworld.delegate.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.model.ImageModel;
import pers.geolo.guitarworld.model.listener.GetImageListener;

public class ImageDetailDelegate extends BaseDelegate {

    private static final String IMAGE_PATH = "IMAGE_PATH";

    @BindView(R.id.iv_image)
    ImageView ivImage;

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
            ImageModel.getImage(path, new FileListener<Bitmap>() {
                @Override
                public void onProgress(int currentLength, int totalLength) {
                }

                @Override
                public void onReturn(Bitmap bitmap) {
                    ivImage.setImageBitmap(bitmap);
                }

                @Override
                public void onError(String message) {

                }
            });
        }
    }
}
