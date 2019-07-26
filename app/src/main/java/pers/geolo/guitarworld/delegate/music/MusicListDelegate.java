package pers.geolo.guitarworld.delegate.music;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.model.MusicModel;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/11
 */
public class MusicListDelegate extends BaseDelegate {

    private static final String FILTER = "FILTER";

    @BindView(R.id.music_recycler_view)
    RecyclerView musicRecyclerView;
    @BindView(R.id.recycler_refresh)
    SwipeRefreshLayout recyclerRefresh;

    List<Music> musicList;
    Adapter adapter;

    MusicModel musicModel = BeanFactory.getBean(MusicModel.class);

    @Override
    public Object getLayout() {
        return R.layout.music_list;
    }

    public static MusicListDelegate newInstance(HashMap<String, Object> filter) {

        Bundle args = new Bundle();
        args.putSerializable(FILTER, filter);
        MusicListDelegate fragment = new MusicListDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        musicList = new ArrayList<>();
        recyclerRefresh.setOnRefreshListener(() -> loadMusicList());
        loadMusicList();
        initRecyclerView();
    }

    private void loadMusicList() {
        HashMap<String, Object> filter = (HashMap<String, Object>) getArguments().getSerializable(FILTER);
        musicModel.getMusicList(filter, new DataListener<List<Music>>() {
            @Override
            public void onReturn(List<Music> music) {
                musicList = music;
                adapter.notifyDataSetChanged();
                recyclerRefresh.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                recyclerRefresh.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerViewUtils.setDefaultConfig(getContext(), musicRecyclerView);
        adapter = new Adapter();
        musicRecyclerView.setAdapter(adapter);
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.music_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Music music = musicList.get(i);
            viewHolder.musicAuthorText.setText(music.getAuthor());
            viewHolder.musicNameText.setText(music.getName());
            GlideUtils.load(getContext(), music.getImageUrl(), viewHolder.musicImage);
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.music_image)
            ImageView musicImage;
            @BindView(R.id.music_name_text)
            TextView musicNameText;
            @BindView(R.id.music_author_text)
            TextView musicAuthorText;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.music_item)
            public void startMusicDetail() {
                int id = musicList.get(getAdapterPosition()).getId();
                getContainerActivity().start(MusicIndexDelegate.newInstance(id));
            }
        }
    }
}
