/**
 * 
 */
package scanner;

/**
 * @author D-LAPPY
 *
 */
public class Portscan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s;
		if (args.length==0) 
		{
			String[] params = {"www.haw-hamburg.de","50","500"};
			s = new Scanner(params);
		}
		else 
		{
			s = new Scanner(args);
		} 
		s.run();
	}

}
