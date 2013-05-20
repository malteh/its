
import java.security.*;
import java.io.*;

/*
 * In diesem Beispiel wird eine Nachricht signiert und zusammen mit
 * der Signatur und dem �ffentlichen Schl�ssel in einer Datei gespeichert.
 *
 */
public class SignMessage extends Object {

    // der Name der Datei, in die die signierte Nachricht gespeichert wird
    public String fileName;
    // das Schl�sselpaar
    private KeyPair keyPair = null;

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
     * Diese Methode gibt ein Byte als Hexadezimalzahl aus
     */
    public static String toHexString(byte b) {
        // b ist evtl. auf 4 Byte (Wort) aufgef�llt
        // --> obere 3 Byte auf Null setzen und zu String konvertieren
        String ret = Integer.toHexString(b & 0xFF).toUpperCase();
        // ggf. f�hrende Null einf�gen
        ret = (ret.length() < 2 ? "0" : "") + ret;
        return ret;
    }

    /**
     * Diese Methode generiert ein neues Schl�sselpaar.
     */
    public void generateKeyPair() {
        try {
            // als Algorithmus verwenden wir DSA
            KeyPairGenerator gen = KeyPairGenerator.getInstance("DSA");
            // nur 512 Bit, sonst dauert es zu lange
            gen.initialize(512);
            keyPair = gen.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            Error("Es existiert kein KeyPairGenerator f�r DSA", ex);
        }
    }

    /**
     * Die angegebene Nachricht wird signiert und dann zusammen
     * mit der Signatur und dem �ffentlichen Schl�ssel (X.509-Format) in eine
     * Datei gespeichert.
     */
    public void signAndSaveMessage(String message) {

        // die Nachricht als Byte-Array
        byte[] msg = message.getBytes();
        // als erstes erzeugen wir die Signatur
        Signature dsa = null;
        byte[] signature = null;
        try {
            dsa = Signature.getInstance("SHA1withDSA");
        } catch (NoSuchAlgorithmException ex) {
            Error("Keine Implementierung f�r SHA1withDSA!", ex);
        }

        try {
            // zum Signieren ben�tigen wir den geheimen Schl�ssel
            dsa.initSign(keyPair.getPrivate());
        } catch (InvalidKeyException ex) {
            Error("Falscher Schl�ssel!", ex);
        }

        try {
            dsa.update(msg);
            signature = dsa.sign();
        } catch (SignatureException ex) {
            Error("Fehler beim Signieren der Nachricht!", ex);
        }

        // der �ffentliche Schl�ssel vom Schl�sselpaar
        PublicKey pubKey = keyPair.getPublic();
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
            Error("Fehler beim Schreiben der signierten Nachricht.", ex);
        }
        // Bildschirmausgabe
        System.out.println("Erzeugte SHA1/DSA-Signatur: ");
        for (int i = 0; i < signature.length; ++i) {
            System.out.print(toHexString(signature[i]) + " ");
        }
        System.out.println();
    }


    /**
     * Die main Methode.
     */
    public static void main(String args[]) {

        SignMessage sm = new SignMessage();
        // Name der zu erzeugenden Signaturdatei = 1. Argument der Kommandozeile
        // Zu signierende Nachricht = 2. Argument der Kommandozeile
        if (args.length < 2) {
            System.out.println(
                    "Usage: java SignFile filename message"
            );
            System.exit(0);
        }
        sm.fileName = args[0];
        // als erstes wird ein neues Schl�sselpaar erzeugt
        sm.generateKeyPair();
        // eine Nachricht wird signiert und gespeichert
        sm.signAndSaveMessage(args[1]);
    }
}