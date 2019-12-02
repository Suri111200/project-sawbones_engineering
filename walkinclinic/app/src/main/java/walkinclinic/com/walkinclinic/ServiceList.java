package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServiceList extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> services;
    String filter = "";

    public ServiceList(Activity context, List<Service> services) {
        super(context, R.layout.layout_service_list, services);
        this.context = context;
        this.services = services;
    }

    public void update(List<Service> results, String query){
        this.filter = query;
        services = new ArrayList<>();
        services.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_service_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole);
        TextView textViewRate = (TextView) listViewItem.findViewById(R.id.textViewRate);


        Service service = services.get(position);

        String itemValue = service.getName().toString();
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

        textViewRole.setText(String.valueOf(service.getRole()));
        textViewRate.setText(String.valueOf(service.getRate()));

        return listViewItem;
    }

    @Override
    public int getCount(){
        return services.size();
    }
}