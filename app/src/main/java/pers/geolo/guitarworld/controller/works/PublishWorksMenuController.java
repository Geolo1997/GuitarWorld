package pers.geolo.guitarworld.controller.works;

import butterknife.BindView;
import butterknife.OnClick;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/10
 */
public class PublishWorksMenuController extends BaseController {

    @Override
    protected int getLayout() {
        return R.layout.publish_works_menu;
    }

    @Override
    public void initView(ViewParams viewParams) {

    }

    @OnClick(R.id.publish_image_text_works_button)
    public void onPublishImageTextWorksButtonClicked() {
//        ControllerManager.start(new PublishImageTextWorksController());
    }

    @OnClick(R.id.publish_video_works_button)
    public void onPublishVideoWorksButtonClicked() {
//        ControllerManager.start(new PublishVideoWorksController());
    }
}
