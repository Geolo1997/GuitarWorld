package pers.geolo.guitarworld.delegate.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;

import java.util.List;

public class MusicTeachListDelegate extends BaseDelegate {

    @BindView(R.id.rv_teach_list)
    RecyclerView rvTeachList;

//    List<Works>

    @Override
    public Object getLayout() {
        return R.layout.delegate_music_teach_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
