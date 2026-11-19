package com.sentana.job.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Converter
public class StringConverter implements AttributeConverter<String, String> {
    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    public StringConverter() throws Exception {
        String aes = "AES";
        byte[] encryptKey = "this-is-test-key".getBytes();
        Key key = new SecretKeySpec(encryptKey, aes);
        encryptCipher = Cipher.getInstance(aes);
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance(aes);
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        if (s != null) {
            try {
                return Base64.getEncoder().encodeToString(encryptCipher.doFinal(s.getBytes()));
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        return null;
    }

    @Override
    public String convertToEntityAttribute(String s) {
        if (s != null) {
            try {
                return new String(decryptCipher.doFinal(Base64.getDecoder().decode(s)));
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        return null;
    }
}
