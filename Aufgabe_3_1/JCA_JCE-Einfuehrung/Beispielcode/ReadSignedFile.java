
import java.security.*;
import java.security.spec.*;
import java.io.*;

/*
 * In diesem Beispiel wird eine Datei mit einer Nachricht, zugeh�riger SHA1/DSA-Signatur
 * und �ffentlichem Schl�ssel im X.509-Format ge�ffnet und die Signatur mit Hilfe des
 * �ffentlichen Schl�ssels verifiziert.
 */
public class ReadSignedFile extends Object {

    // der Name der Datei, in die die signierte Nachricht gespeichert wird
    public String fileName;

    /**
     * Diese Methode gibt eine Fehlermeldung sowie eine Beschreibung
     * der Ausnahme aus. Danach wird das Programm beendet.
     *
     * @param msg eine Beschreibung f�r den Fehler
     * @param ex  die Ausnahme, die den Fehler ausgel�st hat
     */
    private final static void Error(String msg, Exception ex) {
        System.out.println(msg);
        System.out.println(ex.getMessage());
        System.exit(0);
    }

    /**
     * Diese Methode liest eine Nachricht, deren Signatur und den
     * geh�rigen �ffentlichen Schl�ssel zur Verifizierung der Signatur.
     * Dann wird die Signatur �berpr�ft und die Nachricht zur�ckgelierfert.
     */
    public String readAndVerifyMessage() {

        byte[] message = null;
        byte[] signature = null;
        byte[] pubKeyEnc = null;

        try {
            // die Datei wird ge�ffnet und die Daten gelesen
            DataInputStream is = new DataInputStream(new FileInputStream(fileName));
            // die L�nge der Nachricht
            int len = is.readInt();
            message = new byte[len];
            // die Nachricht
            is.read(message);
            // die L�nge der Signatur
            len = is.readInt();
            signature = new byte[len];
            // die Signatur
            is.read(signature);
            // die L�nge des �ffentlichen Schl�ssels
            len = is.readInt();
            pubKeyEnc = new byte[len];
            // der �ffentliche Schl�ssel
            is.read(pubKeyEnc);
        } catch (IOException ex) {
            Error("Fehler beim Lesen der signierten Nachricht!", ex);
        }

        // nun wird aus der Kodierung wieder ein public key erzeugt
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("DSA");
        } catch (NoSuchAlgorithmException ex) {
            Error("Es existiert keine KeyFactory f�r DSA.", ex);
        }
        // aus dem Byte-Array k�nnen wir eine X.509-Schl�sselspezifikation erzeugen
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyEnc);
        // und in einen abgeschlossene, providerabh�ngigen Schl�ssel konvertieren
        PublicKey pubKey = null;
        try {
            pubKey = keyFac.generatePublic(x509KeySpec);
        } catch (InvalidKeySpecException ex) {
            Error("Fehler beim Konvertieren des Schl�ssels.", ex);
        }

        // nun wird die Signatur �berpr�ft
        Signature dsa = null;
        try {
            dsa = Signature.getInstance("SHA1withDSA");
            dsa.initVerify(pubKey);
            dsa.update(message);
        } catch (NoSuchAlgorithmException ex) {
            Error("Keine Implementierung f�r SHA1withDSA!", ex);
        } catch (SignatureException ex) {
            Error("Fehler beim �berpr�fen der Signatur!", ex);
        } catch (InvalidKeyException ex) {
            Error("Falscher Algorithmus?", ex);
        }

        try {
            boolean ok = dsa.verify(signature);
            if (ok)
                System.out.println("Signatur erfolgreich verifiziert!");
            else
                System.out.println("Signatur konnte nicht verifiziert werden!");
        } catch (SignatureException ex) {
            Error("Fehler beim Verifizieren der Signatur!", ex);
        }

        // als Ergebnis liefern wir die urpspr�ngliche Nachricht
        return new String(message);
    }

    /**
     * Die main Methode.
     */
    public static void main(String args[]) {

        ReadSignedFile rsf = new ReadSignedFile();
        // Name der zu lesenden Signaturdatei = 1. Argument der Kommandozeile
        if (args.length < 1) {
            System.out.println(
                    "Usage: java ReadSignedFile filename"
            );
            System.exit(0);
        }
        rsf.fileName = args[0];

        // die Nachricht wird wieder gelesen und die Signatur �berpr�ft
        String msg = rsf.readAndVerifyMessage();
        System.out.println("Signierte Nachricht: " + msg);
    }
}