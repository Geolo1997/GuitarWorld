package pers.geolo.guitarworld.controller.music;

import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.MusicScore;
import pers.geolo.guitarworld.model.MusicModel;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

public class MusicScoreListController extends BaseController {

    private static final String MUSIC_ID = "MUSIC_ID";

    @BindView(R.id.score_list_view)
    RecyclerView scoreListView;
//    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout srlRefresh;

//    List<MusicScore> musicScoreList = new ArrayList<>();
//    Adapter adapter = new Adapter();
//    MusicModel musicModel = BeanFactory.getBean(MusicModel.class);

    @Override
    protected int getLayout() {
        return R.layout.controller_music_score_list;
    }

    @Override
    public void initView(ViewParams viewParams) {
//        int musicId = 0;
//        if (getArguments() != null) {
//            musicId = getArguments().getInt(MUSIC_ID);
//        }
//        initRecyclerView();
//        loadMusicScore(musicId);
    }

    private void initRecyclerView() {
//        RecyclerViewUtils.setDefaultConfig(getContext(), scoreListView);
//        scoreListView.setAdapter(adapter);
    }

    private void loadMusicScore(int musicId) {
//        musicModel.getMusicScoreList(musicId, new DataCallback<List<MusicScore>>() {
//            @Override
//            public void onReturn(List<MusicScore> musicScores) {
//                musicScoreList = musicScores;
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
    }

}
