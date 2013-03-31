package scanner;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class Scanner implements Runnable {

	public Scanner(String[] args)  {
		String host;
		Integer startport;
		Integer endport;

		if (args[0] != null && args[1] != null && args[2] != null) {

			host = args[0];
			startport = Integer.parseInt(args[1]);
			endport = Integer.parseInt(args[2]);
			for (int port = startport; startport <= endport; port++) {
				Socket socket =  null;
				try {
					socket = new Socket(host, port);
					System.out.println("Port:" + port + " erreichbar.");
				} catch (IOException e) {
					System.out.println("Port:" + port + " nicht erreichbar.");
				} finally {
					try {
						socket.close();
					} catch (Exception e) {
					}
				}
			}

		}
	}

	public void run() {
		// TODO Auto-generated method stub

	}

}
