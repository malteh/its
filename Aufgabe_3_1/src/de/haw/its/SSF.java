package de.haw.its;

import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.nio.file.Files;

/**
 * Created with IntelliJ IDEA.
 * User: D
 * Date: 20.05.13
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
public class SSF {
    public static void main(String[] args) {
        if (args.length < 6) {
            System.err.print("Falsche Anzahl an Parametern 6 erwartet.");
            System.exit(1);
        }

        //byte[] message = "TEST".getBytes();
        byte[] private_key = readkey(args[2]);
        byte[] public_key = readkey(args[3]);
        byte[] message = Files.readAllBytes(args[4]);

        byte[] aes = createAES();
        signAndSaveMessage(message, private_key,public_key,args[5]);



    }




    public static void signAndSaveMessage(byte[] msg,byte[] p_key, byte[] public_key, String fileName) {


        // als erstes erzeugen wir die Signatur
        Signature rsa = null;
        byte[] signature = null;
        try {
            rsa = Signature.getInstance("SHA1withRSA");
        } catch (NoSuchAlgorithmException ex) {
           System.err.println("Keine Implementierung f�r SHA1withDSA!" + ex);
        }

        try {
            // zum Signieren ben�tigen wir den geheimen Schl�ssel
            assert rsa != null;
            rsa.initSign(get_private_key_from_bytes(p_key));
        } catch (InvalidKeyException ex) {
            System.err.println("Falscher Schl�ssel!" + ex);
        }

        try {
            rsa.update(msg);
            signature = rsa.sign();
        } catch (SignatureException ex) {
            System.err.println("Fehler beim Signieren der Nachricht!" + ex);
        }

        // der �ffentliche Schl�ssel vom Schl�sselpaar
        PublicKey pubKey = get_pubic_key_from_bytes(public_key);
        // wir ben�tigen die Default-Kodierung
        byte[] pubKeyEnc = pubKey.getEncoded();
        System.out.println("Der Public Key wird in folgendem Format gespeichert: " + pubKey.getFormat());

        try {
            // eine Datei wird erzeugt und danach die Nachricht, die Signatur und
            // der �ffentliche Schl�ssel darin gespeichert
            DataOutputStream os = new DataOutputStream(new FileOutputStream(fileName));
            os.writeInt(msg.length);
            os.write(msg);
            os.writeInt(signature.length);
            os.write(signature);
            os.writeInt(pubKeyEnc.length);
            os.write(pubKeyEnc);
            os.close();
        } catch (IOException ex) {
            System.err.println("Fehler beim Schreiben der signierten Nachricht."+ ex);
        }
        // Bildschirmausgabe
        System.out.println("Done");
    }


    // Helper Methoden

    private PrivateKey get_private_key_from_bytes(byte[] key)
    {
        try{ KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(key);
            return kf.generatePrivate(ks);
        } catch (Exception ex) {}
        return null;
    }

    private PublicKey get_pubic_key_from_bytes(byte[] key)
    {
        try{ KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec ks = new X509EncodedKeySpec(key);
            return kf.generatePublic(ks);
        } catch (Exception ex) {}
        return null;
    }

    private static byte[] readkey(String filename) {
        try {
            // Lesen eines RSA Schlüssel
            DataInputStream inputStream = new DataInputStream((new FileInputStream(args[1])));
            // Länge der Nachricht
            int inhaber_laenge = inputStream.read();
            byte[] inhaber_name = new byte[inhaber_laenge];
            inputStream.read(inhaber_name, Integer.SIZE, inhaber_laenge);
            int schluessel_laenge = inputStream.read();
            byte[] schluessel = new byte[schluessel_laenge];
            inputStream.read(schluessel, Integer.SIZE + inhaber_laenge, schluessel_laenge);
            return schluessel;

        } catch (Exception e) {
        }

        return null;
    }

    private static byte[] createAES() {
        try {
            KeyGenerator gen = KeyGenerator.getInstance("AES");
            gen.init(128);
            return gen.generateKey().getEncoded();
           }
        catch (Exception e) {}
        return null;
    }
}
