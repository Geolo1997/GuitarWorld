package pers.geolo.guitarworld.delegate.music;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.base.SwipeBackDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.MusicScore;
import pers.geolo.guitarworld.model.MusicModel;
import pers.geolo.guitarworld.util.RecyclerViewUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/10
 */
public class MusicScoreDetailDelegate extends SwipeBackDelegate {

    private static final String MUSIC_SCORE_ID = "MUSIC_SCORE_ID";

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.score_recycler_view)
    RecyclerView scoreRecyclerView;

    private List<String> musicScoreImages;
    private Adapter adapter;

    MusicModel musicModel = BeanFactory.getBean(MusicModel.class);

    @Override
    public Object getLayoutView() {
        return R.layout.music_score_detail;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    public static MusicScoreDetailDelegate newInstance(long musicScoreId) {

        Bundle args = new Bundle();
        args.putLong(MUSIC_SCORE_ID, musicScoreId);
        MusicScoreDetailDelegate fragment = new MusicScoreDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTitleBar();
        initRecyclerView();
        loadMusicScore();
    }

    private void initTitleBar() {
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                pop();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
            }
        });
    }

    private void initRecyclerView() {
        RecyclerViewUtils.setDefaultConfig(getContext(), scoreRecyclerView);
        adapter = new Adapter();
        scoreRecyclerView.setAdapter(adapter);
    }

    private void loadMusicScore() {
        musicScoreImages = new ArrayList<>();
        long musicScoreId = getArguments().getLong(MUSIC_SCORE_ID);
        musicModel.getMusicScoreImage(musicScoreId, new DataListener<List<String>>() {
            @Override
            public void onReturn(List<String> strings) {
                musicScoreImages = strings;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getContext(), message);
            }
        });
        musicModel.getMusicScore(musicScoreId, new DataListener<List<MusicScore>>() {
            @Override
            public void onReturn(List<MusicScore> musicScores) {
                MusicScore musicScore = musicScores.get(0);
                if (musicScore != null) {
                    titleBar.setTitle(musicScore.getName());
                } else {
                    //TODO 异常
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.music_score_detail_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            String url = musicScoreImages.get(i);
            viewHolder.itemIndex.setText(String.valueOf(i + 1));
            Glide.with(getContainerActivity()).load(url).into(viewHolder.itemImage);
        }

        @Override
        public int getItemCount() {
            return musicScoreImages.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.item_image)
            ImageView itemImage;
            @BindView(R.id.item_index)
            TextView itemIndex;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
