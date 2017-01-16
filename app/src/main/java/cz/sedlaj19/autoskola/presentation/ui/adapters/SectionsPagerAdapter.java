package cz.sedlaj19.autoskola.presentation.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import cz.sedlaj19.autoskola.presentation.ui.fragments.InstructorStudentsFragment;
import cz.sedlaj19.autoskola.presentation.ui.fragments.InstructorsRideFragment;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter{

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return InstructorsRideFragment.newInstance(position + 1);
            case 1:
                return InstructorStudentsFragment.newInstance(position + 1);
            default:
                return InstructorsRideFragment.newInstance(position + 1);
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "RIDES";
            case 1:
                return "STUDENTS";
        }
        return null;
    }
}
