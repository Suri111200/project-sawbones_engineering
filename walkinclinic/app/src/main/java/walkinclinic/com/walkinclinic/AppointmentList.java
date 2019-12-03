package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppointmentList extends ArrayAdapter<Appointment> {

    private Activity context;
    List<Appointment> appointments;
    String filter = "";

    public AppointmentList (Activity context, List<Appointment> appointments) {
        super(context, R.layout.layout_review_list, appointments);
        this.context = context;
        this.appointments = appointments;
    }

    public void update(List<Appointment> results, String query){
        this.filter = query;
        appointments = new ArrayList<>();
        appointments.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_review_list, null, true);

        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.RatingTV);
        TextView textViewReview = (TextView) listViewItem.findViewById(R.id.ReviewTV);


        Appointment appointment = appointments.get(position);
        textViewRating.setText(appointment.getDate());
        textViewReview.setText(appointment.getTime());
        return listViewItem;
    }

    @Override
    public int getCount(){
        return appointments.size();
    }
}
