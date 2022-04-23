package com.wfs.utility.algorithms;

import com.wfs.utility.exception.WeatherForecastException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

public class DecryptUtil {
    private static final String SECRET_KEY = "abf24ace382697908885b567938bfcbd4b666750";
    private static final String SALT_KEY = "1496b519271f97d17819397774c5ed344fd91ef9";
    private static final String CRYPTO_ALGO = "AES";
    private static final String SECRET_ALGO = "PBKDF2WithHmacSHA256";
    private static final String CIPHER_ALGO = "AES/CBC/PKCS5Padding";


    public static String encrypt(String strToEncrypt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_ALGO);
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT_KEY.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), CRYPTO_ALGO);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new WeatherForecastException(e.getMessage());
        }
    }

    public static String decrypt(String strToDecrypt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_ALGO);
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT_KEY.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), CRYPTO_ALGO);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            throw new WeatherForecastException(e.getMessage());
        }
    }

    private DecryptUtil() {
    }
}
