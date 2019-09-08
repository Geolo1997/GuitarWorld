package pers.geolo.guitarworld.controller.user;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pers.geolo.guitarworld.controller.user.UserListController;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-10
 */
public class UserListAdapter extends FragmentPagerAdapter {

    private List<UserListController> controllers;

    public UserListAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setControllers(List<UserListController> controllers) {
        this.controllers = controllers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return controllers.size();
    }

    @Override
    public Fragment getItem(int i) {
//        return controllers.get(i);
        return null;
    }
}
