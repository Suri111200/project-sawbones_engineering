package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClinicList extends ArrayAdapter<Clinic> {
    private Activity context;
    List<Clinic> clinics;

    public ClinicList(Activity context, List<Clinic> clinics) {
        super(context, R.layout.layout_clinic_list, clinics);
        this.context = context;
        this.clinics = clinics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_clinic_list, null, true);

        TextView textViewClinicName = (TextView) listViewItem.findViewById(R.id.textViewClinicName);

        Clinic clinic = clinics.get(position);
        textViewClinicName.setText(clinic.getName());
        return listViewItem;
    }
}