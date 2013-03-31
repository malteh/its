/**
 * 
 */
package scanner;

import java.io.IOException;
import java.net.Socket;

/**
 * @author D-LAPPY
 * 
 */
public class Portscan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s;

		String host;
		Integer startport;
		Integer endport;

		//System.out.println(args.length);
		
		
		if (args.length<2) {
			host = "haw-hamburg.de";
			startport = 50;
			endport = 500;
		} else {
			host = args[0];
			startport = Integer.parseInt(args[1]);
			endport = Integer.parseInt(args[2]);
		}
		for (int port = startport; port <= endport; port++) {
			Thread scannerthread = new Thread(new Scanner(host, port));
			scannerthread.start();
			//System.out.println(port);
		}
	}
}
