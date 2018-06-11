package mbis.lks.networksecurity.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by lmasi on 2018. 6. 11..
 */

public class ObjectByteStream
{
    public static byte[] toByteArray (Object obj)
    {
        byte[] bytes = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bos.close();
            bytes = bos.toByteArray ();
        }
        catch (IOException ex)
        {
            //TODO: Handle the exception
            ex.printStackTrace();

        }
        return bytes;
    }


    public static Object toObject (byte[] bytes)
    {
        Object obj = null;

        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        }
        catch (IOException ex)
        { //TODO: Handle the exception
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            //TODO: Handle the exception
            ex.printStackTrace();
        }
        return obj;
    }

}
