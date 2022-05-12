package auth;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.Alerts;

/**
 * Definition of methods that enable user authentication
 */
public class Auth {

    private static final SecureRandom RAND = new SecureRandom();
    private static final int ITERATIONS = 15;
    private static final int KEY_LENGTH = 16;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    static Alerts alert;

    /**
     * Generates an array of bytes of size defined in the array declaration
     *
     * @return string.
     *
     */
    public static Optional<String> createSalt() {

        byte[] salt = new byte[16];
        RAND.nextBytes(salt);

        return Optional.of(Base64.getEncoder().encodeToString(salt));
    }

    /**
     * Generates a cryptographic key using a user password and a second string
     * 'salt' as a starting point.
     *
     * @param password - User defined
     * @param salt - Random value defined in another method
     * @return String - Converted to a fixed-length hash code.
     */
    public static Optional<String> hashPassword(String password, String salt) {

        //Turns the received password and salt in arrays so they can be used to generate hash
        char[] chars = password.toCharArray();
        byte[] bytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

        Arrays.fill(chars, Character.MIN_VALUE);

        try {
            //gets the algorith specified in the ALGORITHM variable
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            //generated an hash and returns it as a String
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Optional.of(Base64.getEncoder().encodeToString(securePassword));

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.err.println("Exception encountered in hashPassword()");
            return Optional.empty();

        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Determines whether or not a given plain text password generates the same
     * hashed password key when the same salt is used.
     *
     * @param password - User entered
     * @param key - generated hash
     * @param salt - used to create the hash
     * @return boolean
     */
    public static boolean verifyPassword(String password, String key, String salt) {
        Optional<String> optEncrypted = hashPassword(password, salt);
        //returns false if no key generated
        if (!optEncrypted.isPresent()) {
            return false;
        }
        //returns true if the key generated from password and salt received is the same as the key received by parameter
        return optEncrypted.get().equals(key);
    }

    /**
     * Check if the password entered is in accordance with the parameters
     *
     * @param psw
     * @return
     */
    public static boolean verificationKey(String psw) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(psw);
        if (!matcher.matches()) {
            alert.showError("Password não corresponde aos requesitos minimos");
            return false;
        }
        return true;
    }

    /**
     * Verify if username
     *
     * @param username
     * @return
     */
    public static boolean verificationUsername(String username) {
        String regex = "^\\w[a-zA-Z0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(username);
        if (!matcher.matches()) {
            alert.showError("Username não pode conter espaços");
            return false;
        }
        return true;
    }

}
