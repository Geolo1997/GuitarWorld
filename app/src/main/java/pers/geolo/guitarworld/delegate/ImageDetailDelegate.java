package pers.geolo.guitarworld.delegate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
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
        return R.layout.activity_image_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String path = bundle.getString(IMAGE_PATH);
            ImageModel.getImage(path, new GetImageListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    ivImage.setImageBitmap(bitmap);
                }
            });
        }
    }
}
