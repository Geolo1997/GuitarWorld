package pers.geolo.guitarworld.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-10
 */
public class DelegatePagerAdapter extends FragmentPagerAdapter {

    private List<SupportFragment> delegateList = new ArrayList<>();

    public DelegatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setDelegateList(List<SupportFragment> delegateList) {
        this.delegateList = delegateList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return delegateList.get(i);
    }

    @Override
    public int getCount() {
        return delegateList.size();
    }
}
