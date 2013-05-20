package de.haw.its;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: D
 * Date: 20.05.13
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
public class SSF {
    public static void main(String[] args)
    {
       if (args.length<6)
       {
           System.err.print("Falsche Anzahl an Parametern 6 erwartet.");
           System.exit(1);
       }


    }

    public byte[] readkey(String filename)
    {
        try {
            // Rewrite File
            FileInputStream fos = new FileInputStream(filename)
            int name_length = fos.read();


            fos.write(key.length);
            fos.write(key);
            fos.close();
        } catch (IOException e)
        {
            System.err.print(e.toString() + ": "+filename);
            System.exit(1);
            return;
        }
        return null;
    }
}
