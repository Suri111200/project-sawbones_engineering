package walkinclinic.com.walkinclinic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class ServiceFragment extends Fragment {

    public ServiceFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list_services, container, false);
        //TODO: Implement search for services
        return view;
    }
}
