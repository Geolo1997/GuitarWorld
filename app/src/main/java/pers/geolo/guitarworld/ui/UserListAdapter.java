package pers.geolo.guitarworld.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pers.geolo.guitarworld.delegate.user.UserListDelegate;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-10
 */
public class UserListAdapter extends FragmentPagerAdapter {

    private List<UserListDelegate> delegates;

    public UserListAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setDelegates(List<UserListDelegate> delegates) {
        this.delegates = delegates;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return delegates.size();
    }

    @Override
    public Fragment getItem(int i) {
        return delegates.get(i);
    }
}
