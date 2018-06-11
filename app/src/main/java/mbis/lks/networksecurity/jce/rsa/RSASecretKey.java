package mbis.lks.networksecurity.jce.rsa;

import android.util.Base64;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by lmasi on 2018. 6. 10..
 */

public class RSASecretKey implements Serializable{

//    private SecretKey key;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSASecretKey() {
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    public RSASecretKey setPublicKey(PublicKey key)
    {

        this.publicKey = key;
        return this;
    }
    public RSASecretKey setPrivateKey(PrivateKey key)
    {

        this.privateKey = key;
        return this;
    }


    public PublicKey getPublicKey()
    {
        return this.publicKey;
    }

    public PrivateKey getPrivateKey()
    {
        return this.privateKey;
    }

    public static RSASecretKey setPrivateKey(String key)
    {
        try
        {
            byte[] tmp_private = Base64.decode(key, Base64.NO_WRAP);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(tmp_private));

            return new RSASecretKey().setPrivateKey(privateKey);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static RSASecretKey setPublicKey(String key)
    {
        try
        {
            byte[] tmp_private = Base64.decode(key, Base64.NO_WRAP);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey privateKey = keyFactory.generatePublic(new X509EncodedKeySpec(tmp_private));

            return new RSASecretKey().setPublicKey(privateKey);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public static RSASecretKey generatePublicKeyFromBase64(String base64PublicKey, String base64PrivateKey)
    {
        try {

            byte[] tmp_public = Base64.decode(base64PublicKey, Base64.NO_WRAP);
            byte[] tmp_private = Base64.decode(base64PrivateKey, Base64.NO_WRAP);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(tmp_public));
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(tmp_private));


            return new RSASecretKey().setPublicKey(publicKey).setPrivateKey(privateKey);

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String toPublicKey2String()
    {
        return Base64.encodeToString(publicKey.getEncoded(), Base64.NO_WRAP);
    }

    public String toPrivateKey2String()
    {
        return Base64.encodeToString(privateKey.getEncoded(), Base64.NO_WRAP);
    }

}
