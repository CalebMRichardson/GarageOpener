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
	

	final String PORT_CMD = "/port";
	
	int port = -1;
	
	String currentDir = System.getProperty("user.dir");
        String configFilePath = currentDir + "/../../config";

	BufferedReader br = null;
	String line = "";
	String cvsSplit = ",";
	
	File configFile = new File(configFilePath); 

	if (!configFile.exists()) {
	    System.out.println("No config file.");
	    port = 1993;
	    //todo create a new config file give it default port; 
	}
	
	try {

	    br = new BufferedReader(new FileReader(configFile));

	    while ((line = br.readLine()) != null) {
		
		String[] value = line.split(cvsSplit);
		int result = Integer.parseInt(value[1]);
		port = result;
	    }

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {

	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}


	if (port == -1) {
	    System.out.println("Bad port");
	    //todo handle error
	    return;
	}
	runThread(port, garageDoor);


    }

    private static void runThread(int port, GarageDoor garageDoor) {
	try {
	    Thread t = new GarageOpenerServer(port, garageDoor);
	    t.start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
}
