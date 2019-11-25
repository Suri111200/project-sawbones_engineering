package walkinclinic.com.walkinclinic;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SearchAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    SearchAdapter(FragmentManager fragmentManager, int numOfTabs) {
        super(fragmentManager);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ClinicFragment();
            case 1:
                return new ServiceFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "CLINICS";
        }
        else if (position == 1)
        {
            title = "SERVICES";
        }
        return title;
    }
}