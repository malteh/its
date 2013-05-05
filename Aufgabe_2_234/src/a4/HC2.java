package a4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HC2 {

	public enum Mode {
		ENCRYPT, DECRYPT
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new HC2().crypt("files/3DESTest.enc", "files/3DESTest.pdf",
					"files/3DESTest.key", Mode.DECRYPT);
			new HC2().crypt("files/3DESTest.pdf", "files/3DESTest.enc2",
					"files/3DESTest.key", Mode.ENCRYPT);
			new HC2().crypt("files/3DESTest.enc2", "files/3DESTest.pdf",
					"files/3DESTest.key", Mode.DECRYPT);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void crypt(String pathIn, String pathOut, String keyfilepath,
			Mode mode) throws IOException {
		FileInputStream keyfile = new FileInputStream(new File(keyfilepath));
		byte[] key1 = new byte[8];
		keyfile.read(key1);
		byte[] key2 = new byte[8];
		keyfile.read(key2);
		byte[] key3 = new byte[8];
		keyfile.read(key3);
		byte[] init = new byte[8];
		keyfile.read(init);
		keyfile.close();

		DES d1 = new DES(key1);
		DES d2 = new DES(key2);
		DES d3 = new DES(key3);

		FileInputStream in = new FileInputStream(new File(pathIn));
		FileOutputStream out = new FileOutputStream(new File(pathOut));
		byte[] buffer = new byte[8];

		long Fk = DES.makeLong(init, 0, init.length);
		int len;

		long text;
		while ((len = in.read(buffer)) > 0) {
			text = DES.makeLong(buffer, 0, len);
			DES.writeBytes(Fk, buffer, 0, buffer.length);

			d1.encrypt(buffer, 0, buffer, 0);
			d2.decrypt(buffer, 0, buffer, 0);
			d3.encrypt(buffer, 0, buffer, 0);
			DES.writeBytes(DES.makeLong(buffer, 0, buffer.length) ^ text,
					buffer, 0, buffer.length);

			if (mode == Mode.ENCRYPT) {
				Fk = DES.makeLong(buffer, 0, buffer.length);
			} else if (mode == Mode.DECRYPT) {
				Fk = text;
			}
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();

	}
}
