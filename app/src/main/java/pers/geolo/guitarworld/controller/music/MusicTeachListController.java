package pers.geolo.guitarworld.controller.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;

public class MusicTeachListController extends BaseController {

    @BindView(R.id.rv_teach_list)
    RecyclerView rvTeachList;

//    List<Works>

    @Override
    public Object getLayoutView() {
        return R.layout.controller_music_teach_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
