package Kerberos;

/* Simulation einer Kerberos-Session mit Zugriff auf einen Fileserver
 /* Client-Klasse
 */

import java.util.*;

public class Client extends Object {

	private KDC myKDC;

	private Server myFileserver;

	private String currentUser;

	private Ticket tgsTicket = null;

	private long tgsSessionKey; // K(C,TGS)

	private Ticket serverTicket = null;

	private long serverSessionKey; // K(C,S)

	// Konstruktor
	public Client(KDC kdc, Server server) {
		myKDC = kdc;
		myFileserver = server;
	}

	public boolean login(String userName, char[] password) {

		boolean ret = false;
		long nonce = generateNonce();
		currentUser = userName;

		// Beim KDC anmelden und TGS-Ticket holen
		TicketResponse tr = myKDC.requestTGSTicket(currentUser,
				myKDC.getName(), nonce);
		System.out.println("##### Client (" + currentUser
				+ "): requestTGSTicket");

		// TGS-Response entschlüsseln und auswerten
		long key = generateSimpleKeyForPassword(password);
		if (tr.decrypt(key) && tr.getNonce() == nonce) {
			System.out.println("##### Client (" + currentUser
					+ "): TicketResponse entschlüsselt, nonce ok");
			
			// TGS-Session key und TGS-Ticket speichern
			tgsSessionKey = tr.getSessionKey();
			tgsTicket = tr.getResponseTicket();
			ret = true;
		}
		else{
			System.err.println("##### Client (" + currentUser
					+ "): TicketResponse nicht entschlüsselt oder nonce nicht ok");
		}
		
		// Passwort im Hauptspeicher löschen
		Arrays.fill(password, (char) 0);

		return ret;
	}

	public boolean showFile(String serverName, String filePath) {
		// Login prüfen: TGS-Ticket vorhanden?
		if (tgsTicket == null){
			System.out.println("##### Client (" + currentUser
					+ "): nicht eingeloggt");
			return false;
		}

		// Serverticket nicht vorhanden?
		if (serverTicket == null) {
			System.out.println("##### Client (" + currentUser
					+ "): Serverticket anfordern");
			// Wenn nicht, neues Serverticket anfordern
			Auth a = new Auth(currentUser, new Date().getTime());
			long nonce = generateNonce();
			a.encrypt(tgsSessionKey);
			TicketResponse tr = myKDC.requestServerTicket(tgsTicket, a,
					serverName, nonce);
			// und Antwort auswerten
			if (tr.decrypt(tgsSessionKey) && tr.getNonce() == nonce) {
				System.out.println("##### Client (" + currentUser
						+ "): TicketResponse entschlüsselt, nonce ok");
				serverSessionKey = tr.getSessionKey();
				serverTicket = tr.getResponseTicket();
			}
			else{
				System.err.println("##### Client (" + currentUser
						+ "): TicketResponse nicht entschlüsselt oder nonce nicht ok");
			}
		}

		// Serverticket jetzt vorhanden?
		if (serverTicket != null) {
			System.out.println("##### Client (" + currentUser
					+ "): Service beim Server anfordern („showFile“)");
			Auth a = new Auth(currentUser, new Date().getTime());
			a.encrypt(serverSessionKey);
			// Service beim Server anfordern („showFile“)
			return myFileserver.requestService(serverTicket, a, "showFile",
					filePath);
		}

		return false;
	}

	/* *********** Hilfsmethoden **************************** */

	private long generateSimpleKeyForPassword(char[] pw) {
		// Liefert einen Schlüssel für ein Passwort zurück, hier simuliert als
		// long-Wert
		long pwKey = 0;
		for (int i = 0; i < pw.length; i++) {
			pwKey = pwKey + pw[i];
		}
		return pwKey;
	}

	private long generateNonce() {
		// Liefert einen neuen Zufallswert
		long rand = (long) (100000000 * Math.random());
		return rand;
	}
}
