package pers.geolo.guitarworld.test.fragview;

import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.model.WorksModel;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/30
 */
public class WorksContentController extends BaseController {

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
    FrameLayout imageListLayout;
    @BindView(R.id.video)
    JzvdStd video;
    @BindView(R.id.works_content_layout)
    LinearLayout worksContentLayout;

    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    @Override
    public int getLayout() {
        return R.layout.works_content;
    }

    @Override
    public void init(Object param) {
        int worksId = (int) param;
        worksModel.getWorks(worksId, new DataListener<Works>() {
            @Override
            public void onReturn(Works works) {
                titleText.setText(works.getTitle());
                // TODO 赋值
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.avatar_image)
    public void onAvatarImageClicked() {
//        load();
    }

    @OnClick(R.id.author_text)
    public void onAuthorTextClicked() {
    }

    @OnClick(R.id.works_content_layout)
    public void onWorksContentLayoutClicked() {
    }
}
