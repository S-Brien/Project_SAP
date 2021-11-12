package presentation;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterControllerTest {
    RegisterController rc = null;

    @BeforeEach
    void setUp() {
        rc = new RegisterController();
    }

    @AfterEach
    void tearDown() {
        rc = null;
    }
    @DisplayName("Test Length greater than 8 false")
    @Test
    void confirmPasswordLengthFalse() {
        System.out.println("Check length");
        String pass1 = "12345";
        String pass2 = "12345";
        String ans = "Password is not long enough";
        String result = rc.confirmPassword(pass1,pass2);
        System.out.println("ans "+ ans+" "+ result);
        Assert.assertTrue(result.contains(ans));

    }
    @DisplayName("Test Length greater than 8 true")
    @Test
    void confirmPasswordLengthTrue() {
        System.out.println("Check length");
        String pass1 = "123456789";
        String pass2 = "123456789";
        String ans = "Password is not long enough";
        String result = rc.confirmPassword(pass1,pass2);
        System.out.println("ans "+ ans+" "+ result);
        Assert.assertFalse(result.contains(ans));

    }
    @DisplayName("Test Password match true")
    @Test
    void confirmPasswordMatchTrue() {
        System.out.println("Check length");
        String pass1 = "12345";
        String pass2 = "12346";
        String ans = "Pasword do not Match";
        String result = rc.confirmPassword(pass1,pass2);
        System.out.println("ans "+ ans+" result "+ result);
        Assert.assertTrue(result.contains(ans));

    }
    @DisplayName("Test Password match false")
    @Test
    void confirmPasswordMatchFalse() {
        System.out.println("Check length");
        String pass1 = "12345";
        String pass2 = "12346";
        String ans = "Pasword do not Match";
        String result = rc.confirmPassword(pass1,pass2);
        System.out.println("ans "+ ans+" result "+ result);
        Assert.assertTrue(result.contains(ans));

    }
    @DisplayName("Test Password Compromised")
    @Test
    void confirmPasswordCompromised() {
        System.out.println("Check length");
        String pass1 = "123456789";
        String pass2 = "123456789";

        String ans = "Password has been compromised";
        String result = rc.confirmPassword(pass1,pass2);
        System.out.println("ans "+ ans+" result "+ result);
        Assert.assertTrue(result.contains(ans));

    }

}