package mbis.lks.networksecurity.jce;

import java.sql.Timestamp;

/**
 * Created by lmasi on 2018. 6. 11..
 */

public class GenerateTimeStamp {

    public static String generate()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toString();
    }
}
