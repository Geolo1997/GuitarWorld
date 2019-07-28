package pers.geolo.guitarworld.delegate.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.ui.UserListAdapter;

public class FriendDelegate extends BaseDelegate {

    private static final String CURRENT_USERNAME = "CURRENT_USERNAME";
    private static final String FOLLOW_TAG = "FOLLOW_TAG";

    @BindView(R.id.bt_following)
    Button btFollowing;
    @BindView(R.id.bt_follower)
    Button btFollower;
    @BindView(R.id.vp_user_list)
    ViewPager viewPager;
    @BindView(R.id.btn_black_list)
    Button btnBlackList;

    private Button selectedButton;
    private List<UserListDelegate> delegates;
    private UserListAdapter userListAdapter;

    public static FriendDelegate newInstance(String username, String followTag) {
        Bundle args = new Bundle();
        args.putString(CURRENT_USERNAME, username);
        args.putString(FOLLOW_TAG, followTag);
        FriendDelegate fragment = new FriendDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_friend;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Bundle args = getArguments();
        if (args != null) {
            String currentUsername = args.getString(CURRENT_USERNAME);
            String tag = args.getString(FOLLOW_TAG);
            HashMap<String, Object> followerFilter = new HashMap<>();
            followerFilter.put("follower", currentUsername);
            HashMap<String, Object> followingFilter = new HashMap<>();
            followingFilter.put("following", currentUsername);

            delegates = new ArrayList<>();
            delegates.add(UserListDelegate.newInstance(followerFilter));
            delegates.add(UserListDelegate.newInstance(followingFilter));
            delegates.add(UserListDelegate.newInstance(new HashMap<>()));
            initViewPager();
            selectedButton = "following".equals(tag) ? btFollowing : btFollower;
            onViewClicked(selectedButton);
        }
    }

    private void initViewPager() {
        userListAdapter = new UserListAdapter(getChildFragmentManager());
        userListAdapter.setDelegates(delegates);
        viewPager.setAdapter(userListAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Button currentSelectedButton = null;
                switch (i) {
                    case 0:
                        currentSelectedButton = btFollowing;
                        break;
                    case 1:
                        currentSelectedButton = btFollower;
                        break;
                    case 2:
                        currentSelectedButton = btnBlackList;
                        break;
                }
                selectedButton.setBackgroundColor(getContext().getColor(R.color.white));
                selectedButton = currentSelectedButton;
                selectedButton.setBackgroundColor(getContext().getColor(R.color.colorPrimary));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.bt_following, R.id.bt_follower, R.id.btn_black_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_following:
                viewPager.setCurrentItem(0);
                break;
            case R.id.bt_follower:
                viewPager.setCurrentItem(1);
                break;
            case R.id.btn_black_list:
                viewPager.setCurrentItem(2);
                break;
            default:
        }
        selectedButton.setBackgroundColor(getContext().getColor(R.color.white));
        selectedButton = (Button) view;
        selectedButton.setBackgroundColor(getContext().getColor(R.color.colorPrimary));
    }
}
