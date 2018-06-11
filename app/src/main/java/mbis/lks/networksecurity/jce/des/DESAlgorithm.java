package mbis.lks.networksecurity.jce.des;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * Created by lmasi on 2018. 6. 10..
 */

public class DESAlgorithm {

    public static byte[] encrypt(String message, SecretKey key)
    {
        try {
            Cipher desCipher;

            // Create the cipher
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Initialize the cipher for encryption
            desCipher.init(Cipher.ENCRYPT_MODE, key);

            //encrypted bytes
            return desCipher.doFinal(message.getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
    }

    public static String encrpytedAsBase64(String message, SecretKey key)
    {
        return Base64.encodeToString(encrypt(message, key), Base64.DEFAULT);
    }

    public static byte[] decryptBase64(String encryptedText, SecretKey key)
    {
        try
        {
            byte[] encryptedByte = Base64.decode(encryptedText, Base64.DEFAULT);

            // Create the cipher
            Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Initialize the cipher for encryption
            desCipher.init(Cipher.DECRYPT_MODE, key);

            //decrypt
            return desCipher.doFinal(encryptedByte);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptBase64AsString(String message, SecretKey key)
    {
        return new String(decryptBase64(message, key));
    }
}
