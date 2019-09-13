package pers.geolo.guitarworld.controller.music;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import org.microview.support.MicroviewRVAdapter;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.entity.MusicScore;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/10
 */
public class MusicScoreDetailController extends BaseController {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.score_recycler_view)
    RecyclerView scoreRecyclerView;

    @Override
    protected int getLayout() {
        return R.layout.music_score_detail;
    }

    @Override
    public void initView(ViewParams viewParams) {
        MusicScore musicScore = (MusicScore) viewParams.get("musicScore");
        titleBar.setTitle(musicScore.getName());
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                ControllerManager.destroy(MusicScoreDetailController.this);
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
            }
        });
        scoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        scoreRecyclerView.setAdapter(new MicroviewRVAdapter(MusicScoreDetailItemController.class, musicScore.getImageUrls()
                .stream()
                .map(new Function<String, ViewParams>() {
                    @Override
                    public ViewParams apply(String s) {
                        // TODO index不对
                        return new ViewParams("musicScoreUrl", s, "musicScoreIndex", 0);
                    }
                })
                .collect(Collectors.toList())));
    }
}
