package mbis.lks.networksecurity.jce.des;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by lmasi on 2018. 6. 10..
 */

public class DESSecretKey {

    private SecretKey key;

    public DESSecretKey() {
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(64);
            this.key = keyGenerator.generateKey();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    public DESSecretKey(SecretKey key)
    {
        this.key = key;
    }

    public SecretKey getKey()
    {
        return this.key;
    }

    public static DESSecretKey generateKeyFromBase64(String base64Key)
    {
        byte[] tmp = Base64.decode(base64Key, Base64.DEFAULT);
        return new DESSecretKey(new SecretKeySpec(tmp, "DES"));
    }

    @Override
    public String toString()
    {
        return Base64.encodeToString(key.getEncoded(), Base64.DEFAULT);
    }
}
