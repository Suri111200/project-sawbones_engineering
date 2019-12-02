package walkinclinic.com.walkinclinic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SearchAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    Person user;

    SearchAdapter(FragmentManager fragmentManager, int numOfTabs, Person user) {
        super(fragmentManager);
        this.numOfTabs = numOfTabs;
        this.user = user;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ClinicFragment clinicF = new ClinicFragment();
                Bundle args = new Bundle();
                args.putSerializable("Person", user);
                clinicF.setArguments(args);
                return clinicF;
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