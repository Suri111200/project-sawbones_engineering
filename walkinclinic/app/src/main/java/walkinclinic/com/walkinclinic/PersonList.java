package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PersonList extends ArrayAdapter<Person> {
    private Activity context;
    List<Person> users;

    public PersonList(Activity context, List<Person> users) {
        super(context, R.layout.layout_employee_list, users);
        this.context = context;
        this.users =users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_employee_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        //TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole);

        Person person = users.get(position);
        textViewName.setText(person.getClass().getSimpleName() + ": " + person.getName());
        //textViewRole.setText(String.valueOf(employee.getRole()));
        return listViewItem;
    }
}