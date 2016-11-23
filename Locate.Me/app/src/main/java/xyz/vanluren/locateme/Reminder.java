package xyz.vanluren.locateme;

import java.util.ArrayList;

/**
 * Created by villadsvalur on 13/10/2016.
 */

public class Reminder {

    public String title;
    public String content;

    public Reminder(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static ArrayList<Reminder> getReminders() {
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();
        reminders.add(new Reminder("Husk at købe kaffe!", "Kaffe Kaffe Kaffe"));
        reminders.add(new Reminder("Husk at købe kaffe!", "Kaffe Kaffe Kaffe"));
        reminders.add(new Reminder("Husk at købe kaffe!", "Kaffe Kaffe Kaffe"));
        reminders.add(new Reminder("Husk at købe kaffe!", "Kaffe Kaffe Kaffe"));
        return reminders;
    }

}
