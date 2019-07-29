package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.base.SwipeBackDelegate;
import pers.geolo.guitarworld.model.FileModel;
import pers.geolo.guitarworld.util.GlideUtils;

public class ImageDetailDelegate extends SwipeBackDelegate {

    private static final String IMAGE_PATH = "IMAGE_PATH";

    @BindView(R.id.title_bar)
    TitleBar titleBar;
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
        return R.layout.image_detail;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTitleBar();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String path = bundle.getString(IMAGE_PATH);
            GlideUtils.load(getContext(), path, ivImage);
        }
    }

    private void initTitleBar() {
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                pop();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }
}
