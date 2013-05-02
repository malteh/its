package a3;

import java.io.*;

import a2.LCG;

public class HC1 {

	public static void main(String[] args) {
		
		System.out.println("Seed:");
		long seed = Long.parseLong(readLine());
		System.out.println("Dateiname (in):");
		String fin = readLine();
		System.out.println("Dateiname (out):");
		String fout = readLine();
		
		try {
			crypt(seed, fin, fout);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void crypt(long seed, String fin, String fout) throws IOException {
		LCG l = new LCG(seed);
		FileInputStream in = new FileInputStream(new File(fin));
		FileOutputStream out = new FileOutputStream(new File(fout));
		byte[] buffer = new byte[8];
		int len;
		while ((len = in.read(buffer)) > 0) {
			for (int i=0; i<len;i++) {
				buffer[i] = (byte) (buffer[i] ^ l.nextByte());
			}
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}
	
	
	// http://terokarvinen.com/readline_in_java.html
	public static String readLine()
	{
		String s = "";
		try {
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			s = in.readLine();
		} catch (Exception e) {
			System.out.println("Error! Exception: "+e); 
		}
		return s;
	}

}
