package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AvailibilityList extends ArrayAdapter<Availibility> {
    private Activity context;
    List<Availibility> availibilities;

    public AvailibilityList(Activity context, List<Availibility> availibilities) {
        super(context, R.layout.layout_availibility_list, availibilities);
        this.context = context;
        this.availibilities = availibilities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_availibility_list, null, true);

        TextView textViewDay = (TextView) listViewItem.findViewById(R.id.textViewDay);
        TextView textViewStartTime = (TextView) listViewItem.findViewById(R.id.textViewStartTime);
        TextView textViewEndTime = (TextView) listViewItem.findViewById(R.id.textViewEndTime);

        Availibility availibility = availibilities.get(position);
        textViewDay.setText(availibility.getDay());
        textViewStartTime.setText(String.valueOf(availibility.getStartTime()));
        textViewEndTime.setText(String.valueOf(availibility.getEndTime()));
        return listViewItem;
    }
}
