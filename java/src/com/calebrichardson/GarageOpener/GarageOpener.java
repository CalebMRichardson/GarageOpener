package com.calebrichardson.GarageOpener;

import java.io.*;
import java.lang.System;
import java.lang.Runtime;

public class GarageOpener {

    public static final String TAG = "GarageOpener";
    
    public static void main(String[] args) throws InterruptedException {
	
	GarageOpenerUtil.debug = true;

	GarageDoor garageDoor = new GarageDoor();

	Runtime.getRuntime().addShutdownHook(new Thread() {
		public void run() {
		    GarageOpenerUtil.log(TAG, "Service has been interuptted...shutting down.");
		    garageDoor.shutdown();
		}
	    });
	
	// todo change this to read from String[] args and default to a stored json file
	int port = 9999;

	try {
	    Thread t = new GarageOpenerServer(port, garageDoor);
	    t.start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }
    
}
