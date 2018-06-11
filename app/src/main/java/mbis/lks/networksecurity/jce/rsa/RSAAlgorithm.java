package mbis.lks.networksecurity.jce.rsa;

import android.util.Base64;
import android.util.Log;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;



public class RSAAlgorithm {

    public static byte[] encrypt(String message, PublicKey key)
    {
        try {
            // Create the cipher
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA1ANDMGF1PADDING");

            // Initialize the cipher for encryption
            rsaCipher.init(Cipher.ENCRYPT_MODE, key);

            //encrypted bytes
            return rsaCipher.doFinal(message.getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
    }

    public static String encrpytedAsBase64(String message, PublicKey key)
    {
        return Base64.encodeToString(encrypt(message, key), Base64.NO_WRAP);
    }

    public static byte[] decryptBase64(String encryptedText, PrivateKey key)
    {
        try
        {
            byte[] encryptedByte = Base64.decode(encryptedText, Base64.NO_WRAP);

            // Create the cipher
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA1ANDMGF1PADDING");

            // Initialize the cipher for encryption
            rsaCipher.init(Cipher.DECRYPT_MODE, key);

            //decrypt
            return rsaCipher.doFinal(encryptedByte);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptBase64AsString(String message, PrivateKey key)
    {
        return new String(decryptBase64(message, key));
    }
}
