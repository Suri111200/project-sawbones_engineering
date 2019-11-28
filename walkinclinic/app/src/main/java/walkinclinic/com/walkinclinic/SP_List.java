package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;

public class SP_List extends ArrayAdapter<ServiceProvider> {
    private Activity context;
    List<ServiceProvider> providers;
    String filter = "";
    DatabaseReference mDatabase;
    ArrayList<Service> services;
    ArrayList<Availability> availabilities;

    public SP_List(Activity context, List<ServiceProvider> providers) {
        super(context, R.layout.layout_sp_list, providers);
        this.context = context;
        this.providers = providers;
    }

    public void update(List<ServiceProvider> results, String query){
        this.filter = query;
        providers = new ArrayList<>();
        providers.addAll(results);
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_sp_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.textViewAddress);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription); //this is now a service
        TextView textViewAvailability = (TextView) listViewItem.findViewById(R.id.textViewAvailability);

        ServiceProvider provider = providers.get(position);

        //textViewName.setText(provider.getCompany());

        String itemValue = provider.getCompany().toString();
        int startPos = itemValue.toLowerCase(Locale.US).indexOf(filter.toLowerCase(Locale.US));
        int endPos = startPos + filter.length();
        if (startPos != -1) // This should always be true, just a sanity check
        {
            Spannable spannable = new SpannableString(itemValue);
            ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});
            TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);

            spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textViewName.setText(spannable);
        } else
            textViewName.setText(itemValue);

        itemValue = provider.getAddress().toString();
        startPos = itemValue.toLowerCase(Locale.US).indexOf(filter.toLowerCase(Locale.US));
        endPos = startPos + filter.length();
        if (startPos != -1) // This should always be true, just a sanity check
        {
            Spannable spannable = new SpannableString(itemValue);
            ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});
            TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);

            spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textViewAddress.setText(spannable);
        } else
            textViewAddress.setText(itemValue);

        services = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(provider.getId()).child("Services");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Service service = new Service(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("role").getValue().toString(), ds.child("rate").getValue().toString());
                    services.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        for(Service s : services){
            itemValue = s.getName();
            startPos = itemValue.toLowerCase(Locale.US).indexOf(filter.toLowerCase(Locale.US));
            endPos = startPos + filter.length();
                if (startPos != -1) // This should always be true, just a sanity check
                {
                    Spannable spannable = new SpannableString(itemValue);
                    ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});
                    TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);

                    spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Spannable test = new SpannableString("Service: ");

                    textViewDescription.setText(TextUtils.concat(test, spannable));
                    break;
                }
                else
                    textViewDescription.setText("Service: " + s.getName());
        }

        availabilities = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("Person").child("ServiceProvider").child(provider.getId()).child("Availability");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availabilities.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Availability availability = new Availability(ds.child("day").getValue().toString(), ds.child("startTime").getValue().toString(), ds.child("endTime").getValue().toString());
                    availabilities.add(availability);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        for(Availability a : availabilities){
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            Date dateD = new Date();
            String selDOW = simpledateformat.format(dateD);
            String s;
            if (a.getDay().equals(selDOW))
                s = a.getDay() + ": " + a.getStartTime() + "-" + a.getEndTime();
            else
                s = "Closed today.";
            itemValue = s;
            startPos = itemValue.toLowerCase(Locale.US).indexOf(filter.toLowerCase(Locale.US));
            endPos = startPos + filter.length();
            if (startPos != -1) // This should always be true, just a sanity check
            {
                Spannable spannable = new SpannableString(itemValue);
                ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});
                TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);

                spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                textViewAvailability.setText(spannable);
                break;
            }
            else
                textViewAvailability.setText(s);
        }



        return listViewItem;
    }

    @Override
    public int getCount(){
        return providers.size();
    }
}