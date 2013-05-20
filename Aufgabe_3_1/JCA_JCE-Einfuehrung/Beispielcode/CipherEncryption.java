import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * Dieses Beispiel zeigt die Verwendung der Klasse Cipher zum
 * Verschl�sseln von beliebigen Daten.
 */
public class CipherEncryption {

    /**
     * Die main Methode.
     */
    public static void main(String[] argv) {


        try {
            // AES-Schl�ssel generieren
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);        // Schl�ssell�nge
            SecretKey skey = kg.generateKey();

            // Cipher-Objekt erzeugen und initialisieren mit AES-Algorithmus und Parametern
            // SUN-Default ist ECB-Modus (damit kein IV �bergeben werden muss) und PKCS5Padding
            // F�r Default-Parameter gen�gt: Cipher.getInstance("AES")
            //          und es kann auf die Parameter (IV) verzichtet werden
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Initialisierung
            cipher.init(Cipher.ENCRYPT_MODE, skey);

            // Der Initialisierungsvektor IV muss dem Empf�nger als Parameter
            // mit �bergeben werden (falls nicht Betrieb im ECB-Modus)
            System.out.println("Cipher Parameter: " + cipher.getParameters().toString());
            AlgorithmParameters ap = cipher.getParameters();

            // die zu sch�tzenden Daten
            byte[] plain = "Das ist nur ein de.haw.its.run.Test!".getBytes();
            System.out.println("Daten: " + new String(plain));

            // nun werden die Daten verschl�sselt
            //(update wird bei gro�en Datenmengen mehrfach aufgerufen werden!)
            byte[] encData = cipher.update(plain);

            // mit doFinal abschlie�en (Rest inkl. Padding ..)
            byte[] encRest = cipher.doFinal();

            // und angezeigt
            System.out.println("Verschl�sselte Daten: " + new String(encData) + new String(encRest));
            // zeigt den Algorithmus des Schl�ssels
            System.out.println("Schl�sselalgorithmus: " + skey.getAlgorithm());
            // zeigt das Format des Schl�ssels
            System.out.println("Schl�sselformat: " + skey.getFormat());

            // nun wird der kodierte Schl�ssel als Bytefolge gespeichert
            byte[] raw_key = skey.getEncoded();

            // hier findet die �bertragung statt ...

            // sollen die Daten wieder entschl�sselt werden, so muss zuerst
            // aus der Bytefolge eine neue AES-Schl�sselspezifikation erzeugt werden
            SecretKeySpec skspec = new SecretKeySpec(raw_key, "AES");

            // mit diesem Parameter wird nun die AES-Chiffre ein zweites Mal,
            // nun aber im DECRYPT MODE initialisiert (inkl. AlgorithmParameters)
            cipher.init(Cipher.DECRYPT_MODE, skspec, ap);

            // und die Daten entschl�sselt
            byte[] decData = cipher.update(encData);

            // mit doFinal abschlie�en (Rest inkl. Padding ..)
            byte[] decRest = cipher.doFinal(encRest);

            // anzeigen der entschl�sselten Daten
            System.out.println("Entschl�sselte Daten: " + new String(decData) + new String(decRest));

        } catch (Exception ex) {
            // ein Fehler???
            System.out.println("Error: " + ex.getMessage());
        }
    }
}