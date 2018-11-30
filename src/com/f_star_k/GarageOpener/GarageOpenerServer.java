package com.f_star_k.GarageOpener;

import java.net.*;
import java.io.*;

public class GarageOpenerServer extends Thread {

    private static final String TAG = "GarageOpenerServer"; 
    private ServerSocket serverSocket;

    private GarageDoor garageDoor;
    
    public GarageOpenerServer(int _port, GarageDoor _garageDoor) throws IOException {
	serverSocket = new ServerSocket(_port);
	garageDoor = _garageDoor;
    }


    public void run() {
 	
	while (true) {
	    try {

		StringBuilder msgString = new StringBuilder();
		msgString.append("Waiting for client on port...");
		msgString.append(serverSocket.getLocalPort());
		
		GarageOpenerUtil.log(TAG, msgString.toString());

		Socket server = serverSocket.accept();

	        msgString.setLength(0);
		msgString.append("Client Connected...");
		msgString.append(server.getRemoteSocketAddress());
		GarageOpenerUtil.log(TAG, msgString.toString());
		
		DataInputStream in = new DataInputStream(server.getInputStream());

		String data = in.readUTF();

		if (data.equals("gdToggle")) {
		    try {
			garageDoor.toggle();
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}

		DataOutputStream out = new DataOutputStream(server.getOutputStream());
		// todo confirm that the door has been toggled
		out.writeUTF("Please Reamin Calm.");

		server.close();
	    } catch (SocketTimeoutException e) {
		System.out.println("Socket Timed out.");
		break;
	    } catch (IOException e) {
		e.printStackTrace();
		break;
	    }
	}
    }
}
