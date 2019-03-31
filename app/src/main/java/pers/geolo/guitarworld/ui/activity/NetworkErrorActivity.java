package pers.geolo.guitarworld.ui.activity;

import android.os.Bundle;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;

public class NetworkErrorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_network_error;
    }
}
