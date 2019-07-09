package pers.geolo.guitarworld.delegate.music;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.model.MusicModel;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

public class MusicScoreListDelegate extends BaseDelegate {

    private static final String MUSIC_ID = "MUSIC_ID";

    @BindView(R.id.rv_score_list)
    RecyclerView rvScoreList;
//    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout srlRefresh;

    List<Music> musicList = new ArrayList<>();
    Adapter adapter = new Adapter();
    MusicModel musicModel = new MusicModel();

    @Override
    public Object getLayout() {
        return R.layout.delegate_music_score_list;
    }

    public static MusicScoreListDelegate newInstance(Long musicId) {
        Bundle args = new Bundle();
        args.putLong(MUSIC_ID, musicId);
        MusicScoreListDelegate fragment = new MusicScoreListDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Long musicId = 0L;
        if (getArguments() != null) {
            musicId = getArguments().getLong(MUSIC_ID);
            initRecyclerView();
            loadMusicScore(musicId);
        }
    }

    private void initRecyclerView() {
        RecyclerViewUtils.setDefaultConfig(getContext(), rvScoreList);
        rvScoreList.setAdapter(adapter);
    }

    private void loadMusicScore(Long musicId) {
        musicModel.getMusicScoreList(musicId, new DataListener<List<Music>>() {
            @Override
            public void onReturn(List<Music> music) {
                musicList = music;
                adapter.notifyDataSetChanged();
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
                    .inflate(R.layout.item_music_score_view, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Music music = musicList.get(i);
            viewHolder.tvMusicScoreAuthor.setText(music.getAuthor());
            viewHolder.tvMusicScoreTitle.setText(music.getTitle());
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_music_score_title)
            TextView tvMusicScoreTitle;
            @BindView(R.id.tv_music_score_author)
            TextView tvMusicScoreAuthor;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}