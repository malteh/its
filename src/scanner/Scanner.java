package scanner;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class Scanner implements Runnable {
	private String host;
	private Integer port;
	private Socket socket;
	
	public Scanner(String host, Integer port)  
	{
		this.host = host;
		this.port = port;
		Socket socket =  null;
	}

	public void run() {
		try {
			socket = new Socket(host, port);
			System.out.println("Host: " + host + " Port:" + port + " erreichbar.");
		} catch (IOException e) {
			System.out.println("Host: " + host + " Port:" + port + " nicht erreichbar.");
		} finally {
			try {
				socket.close();
				
			} catch (Exception e) {
			}
		}
	}

}
