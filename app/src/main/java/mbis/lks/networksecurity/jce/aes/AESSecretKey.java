package mbis.lks.networksecurity.jce.aes;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AESSecretKey {

    private SecretKey key;

    public AESSecretKey() {
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            this.key = keyGenerator.generateKey();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    public AESSecretKey(SecretKey key)
    {
        this.key = key;
    }

    public SecretKey getKey()
    {
        return this.key;
    }

    public static AESSecretKey generateKeyFromBase64(String base64Key)
    {
        byte[] tmp = Base64.decode(base64Key, Base64.DEFAULT);
        return new AESSecretKey(new SecretKeySpec(tmp, "AES"));
    }

    @Override
    public String toString()
    {
        return Base64.encodeToString(key.getEncoded(), Base64.DEFAULT);
    }
}
