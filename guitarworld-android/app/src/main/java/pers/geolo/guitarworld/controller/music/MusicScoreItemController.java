package pers.geolo.guitarworld.controller.music;

import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.entity.MusicScore;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/12
 */
public class MusicScoreItemController extends BaseController {

    @BindView(R.id.music_score_name_text)
    TextView musicScoreNameText;
    @BindView(R.id.music_score_author_text)
    TextView musicScoreAuthorText;
    @BindView(R.id.music_score_item)
    LinearLayout musicScoreItem;

    private MusicScore musicScore;

    @Override
    protected int getLayout() {
        return R.layout.music_score_item;
    }

    @Override
    public void initView(ViewParams viewParams) {
        musicScore = (MusicScore) viewParams.get("musicScore");
        musicScoreNameText.setText(musicScore.getName());
        musicScoreAuthorText.setText(musicScore.getAuthor());
    }

    @OnClick(R.id.music_score_item)
    public void onMusicScoreItemClicked() {
        ControllerManager.start(new MusicScoreDetailController(), new ViewParams("musicScore", musicScore));
    }
}
