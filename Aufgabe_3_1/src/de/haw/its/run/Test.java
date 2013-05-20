package de.haw.its.run;

import de.haw.its.RSAKeyCreation;
import de.haw.its.SSF;

/**
 * Created with IntelliJ IDEA.
 * User: D
 * Date: 20.05.13
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] a) {
        String[] args = new String[3];
        args[0] = "java";
        args[1] = "RSAKeyCreation";
        args[2] = "KMueller";
        RSAKeyCreation key = new RSAKeyCreation();
        RSAKeyCreation.main(args);
        System.out.println("Keys erzeugt für Müller");
        args[2] = "KMeier";
        RSAKeyCreation.main(args);
        System.out.println("Keys erzeugt für Meier");

       args = new String[6];
        args[0] = "java";
        args[1] = "SSF";
        args[2] = "KMueller.prv";
        args[3] = "FMeier.pub";
        args[4] = "ITSAufgabe3.pdf";
        args[5] = "ITSAufgabe3.ssf";

        SSF ssf = new SSF();
        ssf.main(args);


    }
}
