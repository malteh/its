package de.haw.its;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

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

        byte[] private_key = readkey(args[1]);
        byte[] public_key = readkey(args[2]);
        byte[] aes_key = createAES().getEncoded();
        byte[] signature = createSignature(aes_key);



    }

    private static byte[] createSignature(byte[] aes_key) {

    }

    public static byte[] readkey(String filename) {
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

    public static SecretKey createAES() {
        try {

            SecureRandom secureRandom = new SecureRandom();
            byte[] randomkey = new byte[128];
            secureRandom.nextBytes(randomkey);
            return new SecretKeySpec(randomkey,"AES");

        }
        catch (Exception e) {}
        return null;
    }
}
