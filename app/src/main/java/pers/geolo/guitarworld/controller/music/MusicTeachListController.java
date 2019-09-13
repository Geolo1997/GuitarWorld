package pers.geolo.guitarworld.controller.music;

import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;

public class MusicTeachListController extends BaseController {

    @BindView(R.id.rv_teach_list)
    RecyclerView rvTeachList;

    @Override
    protected int getLayout() {
        return R.layout.controller_music_teach_list;
    }

    @Override
    public void initView(ViewParams viewParams) {

    }
}
