package com.eventplatform.util;

import com.eventplatform.exception.utils.PasswordEncoderException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Scope(value = "singleton")
@Component
public class PasswordEncoder {
    /**
     * @param password
     * @param encodeType
     * @return String hex
     * @throws PasswordEncoderException
     */
    public String encode(String password, String encodeType) throws PasswordEncoderException {
        try {
            MessageDigest md = MessageDigest.getInstance(encodeType);
            md.update(password.getBytes());

            StringBuffer sb = new StringBuffer();
            String hex;
            for (byte data : md.digest()) {
                hex = Integer.toHexString(0xFF & data);
                if (hex.length() == 1) sb.append('0');
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new PasswordEncoderException(UtilConstants.ENCODE_EX_MSG + encodeType);
        }
    }
}
