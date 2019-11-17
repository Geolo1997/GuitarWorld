package pers.geolo.guitarworld.controller.music;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.util.GlideUtils;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/12
 */
public class MusicScoreDetailItemController extends BaseController {

    @BindView(R.id.item_image)
    ImageView itemImage;
    @BindView(R.id.item_index)
    TextView itemIndex;

    @Override
    protected int getLayout() {
        return R.layout.music_score_detail_item;
    }

    @Override
    public void initView(ViewParams viewParams) {
        String url = (String) viewParams.get("musicScoreUrl");
        String index = (String) viewParams.get("musicScoreIndex");
        GlideUtils.load(getActivity(), url, itemImage);
        itemIndex.setText(index);
    }
}
