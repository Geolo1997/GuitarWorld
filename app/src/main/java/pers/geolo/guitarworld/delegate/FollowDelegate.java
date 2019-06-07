package pers.geolo.guitarworld.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;

public class FollowDelegate extends BaseDelegate {

    private static final String CURRENT_USERNAME = "CURRENT_USERNAME";
    private static final String FOLLOW_TAG = "FOLLOW_TAG";
    Button selectedButton;

    Bundle following = new Bundle(), follower = new Bundle();

    public static FollowDelegate newInstance(String username) {
        Bundle args = new Bundle();
        args.putString(CURRENT_USERNAME, username);
        FollowDelegate fragment = new FollowDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_follow;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Bundle args = getArguments();
        if (args != null) {
            String currentUsername = args.getString(CURRENT_USERNAME);
            String tag = args.getString(FOLLOW_TAG);
            follower.putString("follower", currentUsername);
            following.putString("following", currentUsername);
            selectedButton = getDelegateActivity().findViewById("following".equals(tag) ? R.id.bt_following :
                    R.id.bt_follower);
            onViewClicked(selectedButton);
        }
    }

    @OnClick({R.id.bt_following, R.id.bt_follower})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_following:
                setFragment(R.id.ll_fragment, new UserListDelegate(), following);
                break;
            case R.id.bt_follower:
                setFragment(R.id.ll_fragment, new UserListDelegate(), follower);
                break;
            default:
        }
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.white));
        selectedButton = findViewById(view.getId());
        selectedButton.setBackgroundColor(getApplicationContext().getColor(R.color.colorPrimary));
    }
}
