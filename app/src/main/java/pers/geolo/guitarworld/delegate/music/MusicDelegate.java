package pers.geolo.guitarworld.delegate.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.works.WorksListDelegate;
import pers.geolo.guitarworld.ui.viewpagernavigation.ViewPagerNavigationHelper;

public class MusicDelegate extends BaseDelegate {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    @Override
    public Object getLayout() {
        return R.layout.delegate_music;
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
    }

}
