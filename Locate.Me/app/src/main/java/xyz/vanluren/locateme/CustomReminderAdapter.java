package xyz.vanluren.locateme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by villadsvalur on 13/10/2016.
 */

public class CustomReminderAdapter extends ArrayAdapter<Reminder> {

    public CustomReminderAdapter(Context context, ArrayList<Reminder> reminders) {
        super(context, 0, reminders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Reminder reminder = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_reminder, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.reminder_title);
        TextView tvHome = (TextView) convertView.findViewById(R.id.reminder_content);
        // Populate the data into the template view using the data object
        tvName.setText(reminder.title);
        tvHome.setText(reminder.content);
        // Return the completed view to render on screen
        return convertView;
    }
}
