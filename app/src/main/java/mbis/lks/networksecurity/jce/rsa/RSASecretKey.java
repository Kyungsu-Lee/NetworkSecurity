package mbis.lks.networksecurity.jce.rsa;

import android.util.Base64;

import java.io.Serializable;
import java.math.BigInteger;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * Created by lmasi on 2018. 6. 10..
 */

public class RSASecretKey implements Serializable{

//    private SecretKey key;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSASecretKey() {
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            this.publicKey = (RSAPublicKey)keyPair.getPublic();
            this.privateKey = (RSAPrivateKey)keyPair.getPrivate();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    public RSASecretKey setPublicKey(RSAPublicKey key)
    {

        this.publicKey = key;
        return this;
    }
    public RSASecretKey setPrivateKey(RSAPrivateKey key)
    {

        this.privateKey = key;
        return this;
    }


    public RSAPublicKey getPublicKey()
    {
        return this.publicKey;
    }

    public RSAPrivateKey getPrivateKey()
    {
        return this.privateKey;
    }

    public static RSASecretKey setPrivateKey(String module, String exponent)
    {
        try
        {
            BigInteger mod = new BigInteger(module);
            BigInteger exp = new BigInteger(exponent);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey)keyFactory.generatePrivate(new RSAPrivateKeySpec(mod, exp));

            return new RSASecretKey().setPrivateKey(privateKey);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static RSASecretKey setPublicKey(String module, String exponent)
    {
        try
        {
            BigInteger mod = new BigInteger(module);
            BigInteger exp = new BigInteger(exponent);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey privateKey = (RSAPublicKey)keyFactory.generatePublic(new RSAPublicKeySpec(mod, exp));

            return new RSASecretKey().setPublicKey(privateKey);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public String getPublicKeyMod()
    {
        return publicKey.getModulus().toString();
    }

    public String getPublicKeyExp()
    {
        return publicKey.getPublicExponent().toString();
    }

    public String getPrivateKeyMod()
    {
        return privateKey.getModulus().toString();
    }

    public String getPrivateKeyExp()
    {
        return privateKey.getPrivateExponent().toString();
    }

    public String getPublicKeyAsString()
    {
        return Base64.encodeToString(publicKey.getEncoded(), Base64.NO_WRAP);
    }

    public String getPrivateKeyAsString()
    {
        return Base64.encodeToString(privateKey.getEncoded(), Base64.NO_WRAP);
    }

}
