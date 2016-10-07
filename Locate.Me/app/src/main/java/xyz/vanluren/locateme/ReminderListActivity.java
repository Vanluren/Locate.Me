package xyz.vanluren.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;

public class ReminderListActivity extends AppCompatActivity {
    private FloatingActionMenu editFAM;
    private FloatingActionButton createReminder = (FloatingActionButton) findViewById(R.id.create_reminder);
    private FloatingActionButton editList = (FloatingActionButton) findViewById(R.id.edit_reminder_list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        editFAM = (FloatingActionMenu) findViewById(R.id.edit_reminders_fam);
        createReminder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newReminder  = new Intent(ReminderListActivity.this, ReminderListActivity.class);
                ReminderListActivity.this.startActivity(newReminder);

            }
        });
        editList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //Todo: implement new edit list intent.

            }
        });



    }

}
