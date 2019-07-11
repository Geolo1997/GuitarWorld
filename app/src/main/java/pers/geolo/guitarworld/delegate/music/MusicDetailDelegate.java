package pers.geolo.guitarworld.delegate.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.works.WorksListDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.model.MusicModel;
import pers.geolo.guitarworld.ui.viewpagernavigation.ViewPagerNavigationHelper;

public class MusicDetailDelegate extends BaseDelegate {

    private static final String ID = "ID";

    @BindView(R.id.music_name_text)
    TextView musicNameText;
    @BindView(R.id.music_author_text)
    TextView musicAuthorText;
    @BindView(R.id.music_profile_text)
    TextView musicProfileText;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    MusicModel musicModel = new MusicModel();

    @Override
    public Object getLayout() {
        return R.layout.delegate_music;
    }

    public static MusicDetailDelegate newInstance(long id) {

        Bundle args = new Bundle();
        args.putLong(ID, id);
        MusicDetailDelegate fragment = new MusicDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        loadRootFragment(R.id.container_score_list, MusicScoreListDelegate.newInstance(0L));
        ViewPagerNavigationHelper helper = new ViewPagerNavigationHelper.Builder(getChildFragmentManager())
                .bottomNavigationView(bottomNavigationView)
                .viewPager(vp)
                .addItem(new MusicScoreListDelegate(), R.id.music_score_list)
                .addItem(new WorksListDelegate(), R.id.music_teach_list)
                .addItem(new WorksListDelegate(), R.id.music_show_list)
                .build();
        loadMusic();
    }

    private void loadMusic() {
        musicModel.getMusic(getArguments().getLong(ID), new DataListener<Music>() {
            @Override
            public void onReturn(Music music) {
                musicNameText.setText(music.getName());
                musicAuthorText.setText(music.getAuthor());
                musicProfileText.setText(music.getProfile());
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
