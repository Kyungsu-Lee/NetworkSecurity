package mbis.lks.networksecurity.jce.aes;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;


public class AESAlgorithm {

    public static byte[] encrypt(String message, SecretKey key)
    {
        try {
            // Create the cipher
            Cipher aesCipher = Cipher.getInstance("AES");

            // Initialize the cipher for encryption
            aesCipher.init(Cipher.ENCRYPT_MODE, key);

            //encrypted bytes
            return aesCipher.doFinal(message.getBytes());
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
            Cipher aesCipher = Cipher.getInstance("AES");

            // Initialize the cipher for encryption
            aesCipher.init(Cipher.DECRYPT_MODE, key);

            //decrypt
            return aesCipher.doFinal(encryptedByte);
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
