package pers.geolo.guitarworld.controller.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.base.SwipeBackController;
import pers.geolo.guitarworld.model.FileModel;
import pers.geolo.guitarworld.util.GlideUtils;

public class ImageDetailController extends SwipeBackController {

    private static final String IMAGE_PATH = "IMAGE_PATH";

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.iv_image)
    ImageView ivImage;

    FileModel fileModel = BeanFactory.getBean(FileModel.class);

    public static ImageDetailController newInstance(String imagePath) {
        Bundle args = new Bundle();
        args.putString(IMAGE_PATH, imagePath);
        ImageDetailController fragment = new ImageDetailController();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayoutView() {
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
