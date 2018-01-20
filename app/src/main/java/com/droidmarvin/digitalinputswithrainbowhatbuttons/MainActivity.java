package com.droidmarvin.digitalinputswithrainbowhatbuttons;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

public class MainActivity extends Activity {

    private static final String BUTTON_PIN = "BCM21";

    private Gpio mButtonGpio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PeripheralManagerService service = new PeripheralManagerService();

        try {
            mButtonGpio = service.openGpio(BUTTON_PIN);
        }catch (Exception e){
            throw new IllegalStateException (mButtonGpio+ "button cannot not be opened",e);
        }

        try {
            mButtonGpio.setDirection(Gpio.DIRECTION_IN);
            mButtonGpio.setActiveType(Gpio.ACTIVE_LOW);
        }catch (Exception e){
            throw new IllegalStateException(mButtonGpio+ "button configuration failed",e );
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mButtonGpio.setEdgeTriggerType(Gpio.EDGE_BOTH);
            mButtonGpio.registerGpioCallback(mButtonCallback);
        }catch (Exception e){
            throw new IllegalStateException(mButtonGpio +"Button cannot be monitored.",e);
        }

    }

    private final GpioCallback mButtonCallback = new GpioCallback() {
        @Override
        public boolean onGpioEdge(Gpio gpio) {

            try {
                if (mButtonGpio.getValue()){
                    Log.i("TUT", "ON PRESSED DOWN");
                }else {
                    Log.i("TUT", "ON PRESSED UP");
                }
            }catch (Exception e){
                throw new IllegalStateException(mButtonGpio+ "button cannot be read");
            }

            return true;
        }
    };

    @Override
    protected void onStop() {
        mButtonGpio.unregisterGpioCallback(mButtonCallback);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        try {
            mButtonGpio.close();
        }catch (Exception e){
            throw new IllegalStateException(mButtonGpio+"Button did not close", e);
        }
        super.onDestroy();
    }
}
