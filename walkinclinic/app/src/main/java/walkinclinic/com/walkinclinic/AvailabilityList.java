package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AvailabilityList extends ArrayAdapter<Availability> {
    private Activity context;
    List<Availability> availabilities;

    public AvailabilityList(Activity context, List<Availability> availabilities) {
        super(context, R.layout.layout_availability_list, availabilities);
        this.context = context;
        this.availabilities = availabilities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_availability_list, null, true);

        TextView textViewDay = (TextView) listViewItem.findViewById(R.id.textViewDay);
        TextView textViewStartTime = (TextView) listViewItem.findViewById(R.id.textViewStartTime);
        TextView textViewEndTime = (TextView) listViewItem.findViewById(R.id.textViewEndTime);

        Availability availability = availabilities.get(position);
        textViewDay.setText(availability.getDay());
        textViewStartTime.setText(String.valueOf(availability.getStartTime()));
        textViewEndTime.setText(String.valueOf(availability.getEndTime()));
        return listViewItem;
    }
}
