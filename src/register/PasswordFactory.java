package register;

public class PasswordFactory {
    public Password getTest(String passType){
        if(passType==null){
            return null;
        }else if(passType.equalsIgnoreCase("PWDSAME")){
            return new PasswordSame("Pasword do not Match");
        }else if(passType.equalsIgnoreCase("PWDLENGTH")){
            return new PasswordLength("Password is not long enough");
        }else if(passType.equalsIgnoreCase("PWDCOMPROMISED")){
            return new PasswordCompromised("Password has been compromised");
        }
        return null;
    }
}
