package pers.geolo.guitarworld.controller.works;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.user.ProfileController;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.entity.WorksType;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.util.GlideUtils;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/29
 */
public class WorksItemController extends BaseController {

    @BindView(R.id.avatar_image)
    CircleImageView avatarImage;
    @BindView(R.id.author_text)
    TextView authorText;
    @BindView(R.id.create_time_text)
    TextView createTimeText;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.content_text)
    TextView contentText;
    @BindView(R.id.first_image)
    ImageView firstImage;
    @BindView(R.id.image_list_layout)
    ViewGroup imageListLayout;
    @BindView(R.id.video)
    JzvdStd video;
    @BindView(R.id.works_content_layout)
    LinearLayout worksContentLayout;

    UserModel userModel = BeanFactory.getBean(UserModel.class);

    private Works works;

    @Override
    protected int getLayout() {
        return R.layout.works_content;
    }

    @Override
    public void initView(ViewParams viewParams) {
        works = (Works) viewParams.get("works");
        authorText.setText(works.getAuthor());
        createTimeText.setText(DateUtils.toFriendlyString(works.getCreateTime()));
        titleText.setText(works.getTitle());
        userModel.getPublicProfile(works.getAuthor(), new DataCallback<User>() {
            @Override
            public void onReturn(User user) {
                GlideUtils.load(getActivity(), user.getAvatarUrl(), avatarImage);
            }

            @Override
            public void onError(String message) {

            }
        });
        if (works.getType() == WorksType.IMAGE_TEXT) { // 图文创作
            contentText.setVisibility(View.VISIBLE);
            contentText.setText(works.getContent());
            int imageSize = works.getImageUrls().size();
            if (imageSize == 1) {
                // 加载一张图
                GlideUtils.load(getActivity(), works.getImageUrls().get(0), firstImage);
            } else if (imageSize > 1) {
                imageListLayout.setVisibility(View.VISIBLE);
                ControllerManager.load(imageListLayout, new ImageListController(),
                        new ViewParams("imageUrls", works.getImageUrls()));
            }
        } else if (works.getType() == WorksType.VIDEO) { // 视频创作
            String url = works.getVideoUrl();
            video.setVisibility(View.VISIBLE);
            video.setUp(url, works.getTitle());
            String previewImageUrl = works.getVideoPreviewUrl();
            if (previewImageUrl != null && !"".equals(previewImageUrl)) {
                GlideUtils.load(getActivity(), previewImageUrl, video.thumbImageView);
            }
        }
    }


    @OnClick({R.id.avatar_image, R.id.author_text})
    public void onAvatarImageClicked() {
        ControllerManager.start(new ProfileController(),
                new ViewParams("username", works.getAuthor()));
    }
}
