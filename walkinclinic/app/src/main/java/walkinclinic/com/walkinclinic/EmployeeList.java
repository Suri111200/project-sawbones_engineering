package walkinclinic.com.walkinclinic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EmployeeList extends ArrayAdapter<Employee> {
    private Activity context;
    List<Employee> employees;

    public EmployeeList(Activity context, List<Employee> employees) {
        super(context, R.layout.layout_employee_list, employees);
        this.context = context;
        this.employees = employees;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_employee_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        //TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole);

        Employee employee = employees.get(position);
        textViewName.setText(employee.getName());
        //textViewRole.setText(String.valueOf(employee.getRole()));
        return listViewItem;
    }
}