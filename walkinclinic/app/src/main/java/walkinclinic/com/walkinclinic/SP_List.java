package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

public class SP_List extends ArrayAdapter<ServiceProvider> {
    private Activity context;
    List<ServiceProvider> providers;

    public SP_List(Activity context, List<ServiceProvider> providers) {
        super(context, R.layout.layout_sp_list, providers);
        this.context = context;
        this.providers = providers;
    }

    public void update(List<ServiceProvider> results){
        providers = new ArrayList<>();
        providers.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_sp_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        //TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole);

        ServiceProvider provider = providers.get(position);
        //TODO: Display rating next to Service Provider's name. Maybe clinic description too.
        textViewName.setText(provider.getCompany());
        //textViewRole.setText(String.valueOf(employee.getRole()));
        return listViewItem;
    }

    @Override
    public int getCount(){
        return providers.size();
    }
}