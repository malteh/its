package de.haw.its;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.KeyFactory;
import java.io.*;

public class RSAKeyCreation {
    private static byte[] private_key;
    private static byte[] public_key;
    private static String inhaber = "";

    static {
        public_key = new byte[1024];
        private_key = new byte[1024];
    }

    public static void main(String[] args) {
        if (args == null)
            return;

        if (args.length < 3) {
            System.err.print("Call: java RSAKeyCreation <Name>");
            return;

        }
        inhaber = args[2].toString();

        System.out.print("Creating Keys for: " + inhaber);
        // Erzeuge Schlüssel, Code aus SignMessage.java
        KeyPair keyPair = generateKeyPair();

        // Speichere Schlüssel, Code selbst erstellt.
        writeKey(inhaber+".pub",keyPair.getPublic().getEncoded());
        writeKey(inhaber+".prv",keyPair.getPrivate().getEncoded());

    }

    public static void writeKey(String filename, byte[] key) {
        try {
            // Rewrite File
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(inhaber.length());
            fos.write(inhaber.getBytes());
            fos.write(key.length);
            fos.write(key);
            fos.close();
        } catch (IOException e)
        {
           System.err.print(e.toString() + ": "+filename);
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
