package its;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created with IntelliJ IDEA. User: D Date: 20.05.13 Time: 20:07 To change this
 * template use File | Settings | File Templates.
 */
public class SSF {
	public static void main(String[] args) {
		if (args.length < 4) {
			System.err.print("Falsche Anzahl an Parametern 6 erwartet.");
			System.exit(1);
		}

		byte[] private_key = readkey(args[0]);
		byte[] public_key = readkey(args[1]);

		signAndSaveMessage(createAES(), private_key, public_key, args[2],
				args[3]);
	}

	public static void signAndSaveMessage(byte[] aes_key, byte[] p_key,
			byte[] public_key, String fileNameOut, String fileNameIn) {
		try {
			DataOutputStream os = new DataOutputStream(new FileOutputStream(
					fileNameOut));

			PublicKey pubKey = get_pubic_key_from_bytes(public_key);

			Cipher rsa = Cipher.getInstance("RSA");
			rsa.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] encAesKey = rsa.doFinal(rsa.update(aes_key));

			// als erstes erzeugen wir die Signatur
			Signature dsa = Signature.getInstance("SHA1withRSA");
			byte[] signature = null;

			// zum Signieren ben�tigen wir den geheimen Schl�ssel
			dsa.initSign(get_private_key_from_bytes(p_key));

			dsa.update(aes_key);
			signature = dsa.sign();

			System.out
					.println("Der Public Key wird in folgendem Format gespeichert: "
							+ pubKey.getFormat());

			// eine Datei wird erzeugt und danach die Nachricht, die Signatur
			// und
			// der �ffentliche Schl�ssel darin gespeichert

			os.writeInt(encAesKey.length);
			os.write(encAesKey, 0, encAesKey.length);
			os.writeInt(signature.length);
			os.write(signature, 0, signature.length);

			FileInputStream in = new FileInputStream(new File(fileNameIn));

			Cipher cipher = Cipher.getInstance("AES");
			SecretKey k = new SecretKeySpec(aes_key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, k);

			byte[] buffer = new byte[8];
			while (in.read(buffer) > 0) {
				byte[] encData = cipher.update(buffer);
				os.write(encData, 0, encData.length);
			}

			byte[] encRest = cipher.doFinal();
			os.write(encRest, 0, encRest.length);

			in.close();
			os.close();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		// Bildschirmausgabe
		System.out.println("Done");
	}

	// Helper Methoden

	private static PrivateKey get_private_key_from_bytes(byte[] key) {
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(key);
			return kf.generatePrivate(ks);
		} catch (Exception ex) {
			System.out.println(ex + "1");
			return null;
		}

	}

	private static PublicKey get_pubic_key_from_bytes(byte[] key) {
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec ks = new X509EncodedKeySpec(key);
			return kf.generatePublic(ks);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static byte[] readkey(String filename) {
		try {
			// Lesen eines RSA Schlüssel
			DataInputStream inputStream = new DataInputStream(
					(new FileInputStream(filename)));
			// Länge der Nachricht
			int inhaber_laenge = inputStream.readInt();
			byte[] inhaber_name = new byte[inhaber_laenge];
			inputStream.read(inhaber_name, 0, inhaber_laenge);
			int schluessel_laenge = inputStream.readInt();
			byte[] schluessel = new byte[schluessel_laenge];
			inputStream.read(schluessel, 0, schluessel_laenge);
			return schluessel;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static byte[] createAES() {
		try {
			KeyGenerator gen = KeyGenerator.getInstance("AES");
			gen.init(128);
			return gen.generateKey().getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
