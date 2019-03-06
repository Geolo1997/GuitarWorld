package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;

public class NetworkErrorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_error);
        ButterKnife.bind(this);
    }
}
