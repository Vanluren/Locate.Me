package xyz.vanluren.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ReminderListActivity extends AppCompatActivity {
    FloatingActionMenu editFAM;
    FloatingActionButton createReminder;
    FloatingActionButton editList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createReminder = (FloatingActionButton) findViewById(R.id.create_reminder);
        createReminder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newReminder  = new Intent(ReminderListActivity.this, ReminderEditActivity.class);
                ReminderListActivity.this.startActivity(newReminder);
            }
        });
    }

}
