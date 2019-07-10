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
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.entity.MusicScore;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/10
 */
public class MusicScoreDetailDelegate extends BaseDelegate {

    private static final String ID = "ID";

    @BindView(R.id.score_recycler_view)
    RecyclerView scoreRecyclerView;

    private MusicScore musicScore;
    private Adapter adapter;

    @Override
    public Object getLayout() {
        return R.layout.music_score_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecyclerView();
        loadMusicScore();
    }

    private void initRecyclerView() {
        RecyclerViewUtils.setDefaultConfig(getContext(), scoreRecyclerView);
        adapter = new Adapter();
        scoreRecyclerView.setAdapter(adapter);
    }

    private void loadMusicScore() {
        // TODO 加载乐谱信息
        musicScore = new MusicScore();
        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            imageUrls.add(HttpClient.baseUrl + "avatar?username=Xiaoming");
        }
        musicScore.setImageUrls(imageUrls);
    }

    public static MusicScoreDetailDelegate newInstance(Long id) {
        Bundle args = new Bundle();
        args.putLong(ID, id);
        MusicScoreDetailDelegate fragment = new MusicScoreDetailDelegate();
        fragment.setArguments(args);
        return fragment;
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
            String url = musicScore.getImageUrls().get(i);
            viewHolder.itemIndex.setText(String.valueOf(i + 1));
            Glide.with(getContainerActivity()).load(url).into(viewHolder.itemImage);
        }

        @Override
        public int getItemCount() {
            return musicScore.getImageUrls().size();
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
