package pers.geolo.guitarworld.delegate.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;

public class MusicDelegate extends BaseDelegate {

    @Override
    public Object getLayout() {
        return R.layout.delegate_music;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        loadRootFragment(R.id.container_score_list, MusicScoreListDelegate.newInstance(0L));
    }
}
