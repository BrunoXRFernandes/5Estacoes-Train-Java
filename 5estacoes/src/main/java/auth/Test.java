
package auth;
/**
 *
 * @author hp
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String salt = Auth.createSalt().get();
        System.out.println("salt: "+salt);

        String password = "password";

        String key = Auth.hashPassword(password, salt).get();
        System.out.println("Hash: "+key);

        boolean verify1 = Auth.verifyPassword("password", key, salt);
        System.out.println("Should pass: "+verify1);

        boolean verify2 = Auth.verifyPassword("nopassword", key, salt);
        System.out.println("Should fail: "+verify2);

    }
    
}
