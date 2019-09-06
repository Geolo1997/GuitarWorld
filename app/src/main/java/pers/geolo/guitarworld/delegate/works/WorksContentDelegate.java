package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;
import org.greenrobot.eventbus.EventBus;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.user.ProfileDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.entity.WorksType;
import pers.geolo.guitarworld.entity.event.Event;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.ArrayList;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/29
 */
public class WorksContentDelegate extends BaseDelegate {

    private static final String WORKS_ID = "WORKS_ID";

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

    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);
    UserModel userModel = BeanFactory.getBean(UserModel.class);

    Works works;

    @Override
    public Object getLayoutView() {
        return R.layout.works_content;
    }

    public static WorksContentDelegate newInstance(int worksId) {
        Bundle args = new Bundle();
        args.putInt(WORKS_ID, worksId);
        WorksContentDelegate fragment = new WorksContentDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getArguments() != null) {
            int worksId = getArguments().getInt(WORKS_ID);
            initLayout(worksId);
        }
    }

    private void initLayout(int worksId) {
        worksModel.getWorks(worksId, new DataListener<Works>() {
            @Override
            public void onReturn(Works works) {
                if (works == null) {
                    return;
                }
                WorksContentDelegate.this.works = works;
                EventBus.getDefault().postSticky(new Event(Event.Const.GET_WORKS_SUCCESS.name(), works));
                authorText.setText(works.getAuthor());
                createTimeText.setText(DateUtils.toFriendlyString(works.getCreateTime()));
                titleText.setText(works.getTitle());
                userModel.getPublicProfile(works.getAuthor(), new DataListener<User>() {
                    @Override
                    public void onReturn(User user) {
                        GlideUtils.load(getContext(), user.getAvatarUrl(), avatarImage);
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
                        GlideUtils.load(getContext(), works.getImageUrls().get(0), firstImage);
                    } else if (imageSize > 1) {
                        imageListLayout.setVisibility(View.VISIBLE);
                        loadRootFragment(R.id.image_list_layout, ImageListDelegate
                                .newInstance(new ArrayList<>(works.getImageUrls())));
                    }
                } else if (works.getType() == WorksType.VIDEO) { // 视频创作
                    String url = works.getVideoUrl();
                    video.setVisibility(View.VISIBLE);
                    video.setUp(url, works.getTitle());
                    String previewImageUrl = works.getVideoPreviewUrl();
                    if (previewImageUrl != null && !"".equals(previewImageUrl)) {
                        GlideUtils.load(getContext(), previewImageUrl, video.thumbImageView);
                    }
                }
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getContext(), message);
            }
        });
    }

    @OnClick({R.id.avatar_image, R.id.author_text})
    public void onAvatarImageClicked() {
        String author = works.getAuthor();
        getContainerActivity().start(ProfileDelegate.newInstance(author));
    }

}
