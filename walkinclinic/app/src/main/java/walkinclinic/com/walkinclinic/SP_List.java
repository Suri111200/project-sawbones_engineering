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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class SP_List extends ArrayAdapter<ServiceProvider> {
    private Activity context;
    List<ServiceProvider> providers;
    String filter = "";

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

        ArrayList<Service> services = provider.getServices();
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
                Spannable service = new SpannableString("Service: ");
                Spannable rate = new SpannableString(", Rate: " + s.getRate() + "$/hour");
                textViewDescription.setText(TextUtils.concat(service, spannable, rate));
                break;
            }
            else
                textViewDescription.setText("Service: " + services.get(0).getName() + ", Rate: " + s.getRate() + "$/hour");
        }

        ArrayList<Availability> availabilities = provider.getAvailabilities();
        for(Availability a : availabilities){
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            Date dateD = new Date();
            String selDOW = simpledateformat.format(dateD);
            String s;
            //if (a.getDay().equals(selDOW))
                s = a.toString();
            //else
            // commented out for now, it will always say closed today. if its monday and the user is searching
            // for Tuesday to book an appointment that day, it still says closed today
            //    s = "Closed today.";
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
                textViewAvailability.setText(availabilities.get(0).toString());
        }



        return listViewItem;
    }

    @Override
    public int getCount(){
        return providers.size();
    }

    public List<ServiceProvider> getProviders(){ return this.providers; }
}