package pers.geolo.guitarworld.controller.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import com.hjq.bar.TitleBar;
import net.lucode.hackware.magicindicator.MagicIndicator;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendController extends BaseController {

    private static final String CURRENT_USERNAME = "CURRENT_USERNAME";
    private static final String FOLLOW_TAG = "FOLLOW_TAG";

    @BindView(R.id.follow_button)
    Button btFollowing;
    @BindView(R.id.bt_follower)
    Button btFollower;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp_user_list)
    ViewPager viewPager;
    @BindView(R.id.btn_black_list)
    Button btnBlackList;

    private Button selectedButton;
    private List<UserListController> controllers;
    private UserListAdapter userListAdapter;

    public static FriendController newInstance(String username, String followTag) {
        Bundle args = new Bundle();
        args.putString(CURRENT_USERNAME, username);
        args.putString(FOLLOW_TAG, followTag);
        FriendController fragment = new FriendController();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayoutView() {
        return R.layout.controller_friend;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
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

            controllers = new ArrayList<>();
            controllers.add(UserListController.newInstance(followerFilter));
            controllers.add(UserListController.newInstance(followingFilter));
            controllers.add(UserListController.newInstance(new HashMap<>()));
            initViewPager();
            selectedButton = "following".equals(tag) ? btFollowing : btFollower;
            onViewClicked(selectedButton);
        }
    }

    private void initViewPager() {
//        userListAdapter = new UserListAdapter(getChildFragmentManager());
        userListAdapter.setControllers(controllers);
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

    @OnClick({R.id.follow_button, R.id.bt_follower, R.id.btn_black_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.follow_button:
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
