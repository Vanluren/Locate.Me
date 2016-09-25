package xyz.vanluren.locateme;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class BluetoothReciever extends BroadcastReceiver {
    private ArrayList<String> mArrayAdapter;
    public static String discoveredUser;

    public BluetoothReciever() {
        String discoveredUser;


    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        // When discovery finds a device

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Get the BluetoothDevice object from the Intent

            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // Add the name and address to an array adapter to show in a ListView

            mArrayAdapter.add(device.getName() + "\n" + device.getAddress());

            discoveredUser = device.getName();
        }
    }
}
