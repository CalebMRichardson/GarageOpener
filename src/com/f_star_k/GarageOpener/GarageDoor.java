package com.f_star_k.GarageOpener;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class GarageDoor {

    private static final String TAG = "GarageDoor";

    private final GpioController gpio;
    private final GpioPinDigitalOutput pin;
    
    public GarageDoor() {
	gpio = GpioFactory.getInstance();
	pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "GarageRelay", PinState.HIGH);
	pin.setShutdownOptions(true, PinState.HIGH);
	pin.high();
    }

    public void toggle() throws InterruptedException {
	GarageOpenerUtil.log(TAG, "toggling");
	//pin.high();
	//Thread.sleep(1000);
	//pin.low();
	pin.low();
	Thread.sleep(1000);
	pin.high();
	//pin.high();
    }

    public void shutdown() {
	GarageOpenerUtil.log(TAG, "shutting down...");
	gpio.shutdown();
    }
    
}
