package its;

import java.security.*;
import java.io.*;

public class RSAKeyCreation {
	private static String inhaber = "";

	public static void main(String[] args) {

		inhaber = args[0].toString();

		System.out.print("Creating Keys for: " + inhaber);
		// Erzeuge Schlüssel, Code aus SignMessage.java
		KeyPair keyPair = generateKeyPair();

		// Speichere Schlüssel, Code selbst erstellt.
		writeKey(inhaber + ".pub", keyPair.getPublic().getEncoded());
		writeKey(inhaber + ".prv", keyPair.getPrivate().getEncoded());

	}

	public static void writeKey(String filename, byte[] key) {
		try {
			// Rewrite File
			DataOutputStream fos = new DataOutputStream(new FileOutputStream(
					filename));

			fos.writeInt(inhaber.length());
			fos.write(inhaber.getBytes(), 0, inhaber.getBytes().length);
			fos.writeInt(key.length);
			fos.write(key, 0, key.length);
			fos.close();
		} catch (IOException e) {
			System.err.print(e.toString() + ": " + filename);
			System.exit(1);
			return;
		}
	}

	public static KeyPair generateKeyPair() {
		try {
			// als Algorithmus verwenden wir RSA
			KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
			gen.initialize(1024);
			return gen.generateKeyPair();
		} catch (NoSuchAlgorithmException ex) {
			System.err.println("Es existiert kein KeyPairGenerator für RSA");
			System.exit(1);

		}
		return null;
	}
}
