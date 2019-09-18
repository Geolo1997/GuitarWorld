package pers.geolo.guitarworld.controller.music;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.util.GlideUtils;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/12
 */
public class MusicItemController extends BaseController {

    @BindView(R.id.music_image)
    ImageView musicImage;
    @BindView(R.id.music_name_text)
    TextView musicNameText;
    @BindView(R.id.music_author_text)
    TextView musicAuthorText;
    @BindView(R.id.music_item)
    LinearLayout musicItemLayout;

    private Music music;

    @Override
    protected int getLayout() {
        return R.layout.music_item;
    }

    @Override
    public void initView(ViewParams viewParams) {
        music = (Music) viewParams.get("music");
        GlideUtils.load(getActivity(), music.getImageUrl(), musicImage);
        musicNameText.setText(music.getName());
        musicAuthorText.setText(music.getAuthor());
    }

    @OnClick(R.id.music_item)
    public void onMusicItemClicked() {
        int musicId = music.getId();
        ControllerManager.start(new MusicIndexController(),
                new ViewParams("musicId", musicId));
    }
}
