package View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments;
    private final List<String> mFragmentsTittle;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments =new ArrayList<>();
        mFragmentsTittle = new ArrayList<>();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
         return mFragmentsTittle.get(position);
    }


    public void addFrag(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentsTittle.add(title);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
