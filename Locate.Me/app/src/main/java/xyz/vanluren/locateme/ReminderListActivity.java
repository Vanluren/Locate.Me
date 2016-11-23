package xyz.vanluren.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class ReminderListActivity extends AppCompatActivity {
    FloatingActionButton createReminder;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        extras = getIntent().getExtras();


        createReminder = (FloatingActionButton) findViewById(R.id.create_reminder);
        createReminder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newReminder  = new Intent(ReminderListActivity.this, ReminderEditActivity.class);
                ReminderListActivity.this.startActivity(newReminder);
            }
        });
        populateListView();
    }

    private void populateListView() {
        ArrayList<Reminder> arrayOfReminders = Reminder.getReminders();

        CustomReminderAdapter adapter = new CustomReminderAdapter(this, arrayOfReminders);

        ListView listView = (ListView) findViewById(R.id.provider_service_list);
        listView.setAdapter(adapter);
    }

}
