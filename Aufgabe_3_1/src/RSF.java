package its;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RSF {

	public static void main(String[] args) {

		String pubKeyFile = args[0];
		String prvKeyFile = args[1];
		String ssfFile = args[2];
		String decFile = args[3];
		
		try {
			DataInputStream inputStream = new DataInputStream(
					(new FileInputStream(ssfFile)));

			Cipher RSAcipher = Cipher.getInstance("RSA");
			RSAcipher.init(Cipher.DECRYPT_MODE,
					get_private_key_from_bytes(readkey(prvKeyFile)));

			byte[] pubKey = readkey(pubKeyFile);
			byte[] encSecretkey = readSecretKey(inputStream);
			byte[] encData = RSAcipher.update(encSecretkey);
			byte[] decodedKey = RSAcipher.doFinal(encData);

			int siglen = inputStream.readInt();

			byte[] signature = new byte[siglen];
			inputStream.read(signature, 0, siglen);

			Signature DSAsig = Signature.getInstance("SHA1withRSA");
			DSAsig.initVerify(get_pubic_key_from_bytes(pubKey));
			DSAsig.update(decodedKey);

			boolean ok = DSAsig.verify(signature);

			if (ok) {
				System.out.println("Signatur ok");
				Cipher AEScipher = Cipher.getInstance("AES");
				SecretKey k = new SecretKeySpec(decodedKey, "AES");
				AEScipher.init(Cipher.DECRYPT_MODE, k);

				DataOutputStream os = new DataOutputStream(
						new FileOutputStream(decFile));

				byte[] buffer = new byte[8];
				while (inputStream.read(buffer) > 0) {
					byte[] cipherBuf = AEScipher.update(buffer);
					os.write(cipherBuf, 0, cipherBuf.length);
				}

				byte[] encRest = AEScipher.doFinal();
				os.write(encRest, 0, encRest.length);
				os.close();
				System.out.println("fertig");
			} else {
				System.err.println("geht nicht!");
			}
			
			inputStream.close();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}

	}

	private static byte[] readSecretKey(DataInputStream inputStream) {
		try {
			int schluessel_laenge = inputStream.readInt();
			byte[] schluessel = new byte[schluessel_laenge];
			inputStream.read(schluessel, 0, schluessel_laenge);
			return schluessel;

		} catch (Exception e) {
			System.out.println(e + "3");
		}

		return null;
	}

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
		} catch (Exception ex) {
			System.out.println(ex + "2");
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
			System.out.println(e + "3");
		}

		return null;
	}

}
