package com.droidmarvin.digitalinputswithrainbowhatbuttons;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

public class MainActivity extends Activity {

    private static final String BUTTON_PIN = "BCM21";

    private Gpio mButtonGpio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PeripheralManagerService service = new PeripheralManagerService();


    }
}
