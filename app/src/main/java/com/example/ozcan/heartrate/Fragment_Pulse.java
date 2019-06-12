package com.example.ozcan.heartrate;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by ozcan on 12.05.2018.
 */

public class Fragment_Pulse extends Fragment {

    TextView txtBPM;
    TextView txtConnected;
    Button btnOpen;
    Button btnClose;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter=83;
    String value;
    volatile boolean stopWorker;

    DS_Katmanı ds;


    PulsatorLayout pulsator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pulse_layout,container,false);

        pulsator = (PulsatorLayout) view.findViewById(R.id.pulsator);

        txtBPM = (TextView) view.findViewById(R.id.txt_pulse);
        btnClose = (Button)view.findViewById(R.id.btn_close);
        btnOpen = (Button)view.findViewById(R.id.btn_open);

        btnClose.setVisibility(View.GONE);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    findBT();
                    openBT();
                    pulsator.start();
                    btnOpen.setVisibility(View.GONE);
                    btnClose.setVisibility(View.VISIBLE);

                }
                catch (IOException ex) { }
            }
        });


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    pulsator.stop();
                    btnClose.setVisibility(View.GONE);
                    btnOpen.setVisibility(View.VISIBLE);
                    closeBT();
                }
                catch (IOException ex) { }
            }
        });

        return view;
    }

    void findBT()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        if(mBluetoothAdapter == null)
        {
            Log.e("fragmentPulse","No bluetooth adapter available");
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals("HC-05"))
                {
                    mmDevice = device;
                    break;
                }
            }
        }
        Log.e("fragmentPulse","Bluetooth Device Found");
        //txtConnected.setText("Bluetooth Device Found");
    }

    void openBT() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        beginListenForData();

        Log.e("fragmentPulse","Bluetooth Opened");
        //txtConnected.setText("Bluetooth Opened");
    }

    BufferedReader r;

    void beginListenForData()
    {
        r = new BufferedReader(new InputStreamReader(mmInputStream));
        stopWorker = false;
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {

                    try {
                        value=r.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(Integer.valueOf(value)>counter)
                    {
                        value=""+counter;
                    }
                    yazdir(value);


                    ds=new DS_Katmanı(getContext());
                    ds.open();
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    ds.addPulse(new Pulse_Deger(timeStamp ,Integer.valueOf(value)));
                    ds.close();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workerThread.start();
    }

    void yazdir(final String value){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    txtBPM.setText(""+value);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void closeBT() throws IOException
    {
        stopWorker = true;
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
       // txtConnected.setText("Bluetooth Closed");
        txtBPM.setText("");
    }
}
