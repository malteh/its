package its.run;

import its.RSAKeyCreation;
import its.RSF;
import its.SSF;

/**
 * Created with IntelliJ IDEA. User: D Date: 20.05.13 Time: 17:23 To change this
 * template use File | Settings | File Templates.
 */
public class Test {
	public static void main(String[] a) {
		String[] args = new String[1];
		args[0] = "KMueller";

		RSAKeyCreation.main(args);
		System.out.println("Keys erzeugt für Müller");
		args[0] = "KMeier";
		RSAKeyCreation.main(args);
		System.out.println("Keys erzeugt für Meier");

		// enc
		args = new String[4];
		args[0] = "KMueller.prv";
		args[1] = "KMeier.pub";
		args[2] = "ITSAufgabe3.ssf";
		args[3] = "ITSAufgabe3.pdf";

		SSF.main(args);
		
		// dec
		args = new String[4];
		args[0] = "KMueller.pub";
		args[1] = "KMeier.prv";
		args[2] = "ITSAufgabe3.ssf";
		args[3] = "ITSAufgabe3.dec.pdf";

		RSF.main(args);
		
		// enc
		args = new String[4];
		args[0] = "KMueller.prv";
		args[1] = "OUgus.pub";
		args[2] = "ITSAufgabe3.ugus.ssf";
		args[3] = "ITSAufgabe3.pdf";
		
		SSF.main(args);
		
		

	}
}
