//package presentation;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserControllerTest {
//    UserController uc = null;
//
//    @BeforeEach
//    void setUp() {uc = new UserController();}
//
//    @AfterEach
//    void tearDown() {uc = null;}
//
//    @DisplayName("Pin field not blank false")
//    @Test
//    void confirmPinFieldNotBlankFalse(){
//        System.out.println("Check field not blank");
//        String pin = "";
//        String ans = "Pin Field Blank";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not blank True")
//    @Test
//    void confirmPinFieldNotBlankTrue(){
//        System.out.println("Check field not blank");
//        String pin = "1111";
//        String ans = "Pin Field not Blank";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not contain letters false")
//    @Test
//    void confirmPinFieldNotContainLettersFalse(){
//        System.out.println("Check field not Contains letters");
//        String pin = "A1b3";
//        String ans = "Pin Field Contains letter";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not contain letters true")
//    @Test
//    void confirmPinFieldNotContainLettersTrue(){
//        System.out.println("Check field not Contains letters");
//        String pin = "1111";
//        String ans = "Pin Field Contains letter";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not less than 4 False")
//    @Test
//    void confirmPinFieldNotLessthan4False(){
//        System.out.println("Check field not less than 4");
//        String pin = "111";
//        String ans = "Pin Field not less than 4";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not less than 4 True")
//    @Test
//    void confirmPinFieldNotLessthan4True(){
//        System.out.println("Check field not less than 4");
//        String pin = "1111";
//        String ans = "Pin Field not less than 4";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not greater than 4 False")
//    @Test
//    void confirmPinFieldNotGreaterthan4False(){
//        System.out.println("Check field not less than 4");
//        String pin = "123456";
//        String ans = "Pin Field not greater than 4";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not greater than 4 True")
//    @Test
//    void confirmPinFieldNotGreaterthan4True(){
//        System.out.println("Check field not Greater than 4");
//        String pin = "1234";
//        String ans = "Pin Field not greater than 4";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field equals 4 False")
//    @Test
//    void confirmPinFieldEquals4False(){
//        System.out.println("Check field Equals 4");
//        String pin = "123422";
//        String ans = "Pin Field not greater than 4";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not greater than 4 False")
//    @Test
//    void confirmPinFieldEquals4True(){
//        System.out.println("Check field Equals 4");
//        String pin = "1234";
//        String ans = "Pin Field not greater than 4";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not greater than 4 False")
//    @Test
//    void confirmPinFieldContainsSpecCharFalse(){
//        System.out.println("Check field not Greater than 4");
//        String pin = "1234";
//        String ans = "Pin Field not greater than 4";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//    @DisplayName("Pin field not greater than 4 False")
//    @Test
//    void confirmPinFieldContainsSpecCharTrue(){
//        System.out.println("Check field not Greater than 4");
//        String pin = "123@";
//        String ans = "Pin Field not greater than 4";
//        String result = uc.confirmPin(pin);
//        System.out.println("ans "+ ans+" "+ result);
//        Assert.assertTrue(result.contains(ans));
//    }
//
//}