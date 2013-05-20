package de.haw.its.run;

import de.haw.its.RSAKeyCreation;

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
    }
}
