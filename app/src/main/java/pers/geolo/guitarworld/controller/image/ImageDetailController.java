package pers.geolo.guitarworld.controller.image;

import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.util.GlideUtils;

public class ImageDetailController extends BaseController {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.image)
    ImageView image;

    @Override
    protected int getLayout() {
        return R.layout.image_detail;
    }

    @Override
    public void initView(ViewParams viewParams) {
        titleBar.setTitle("图片详情");
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                ControllerManager.destroy(ImageDetailController.this);
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
        String imageUrl = (String) viewParams.get("imageUrl");
        GlideUtils.load(getActivity(), imageUrl, image);
    }
}
