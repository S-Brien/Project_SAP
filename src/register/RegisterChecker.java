package register;

import static persistence.StaffDAO.checkIfExists;

public class RegisterChecker {

    private static RegisterChecker instance = null;
    private RegisterChecker() { }

    public static RegisterChecker getInstance() {

        if (instance == null) {
            instance = new RegisterChecker();
        }
        return instance;
    }



    public static boolean confirmUserName(String name) {

        return checkIfExists(name);
    }

    public static boolean confirmValidPin(String pin) {
        boolean status = false;
        try {
            int p = Integer.parseInt(pin);
            System.out.println("pin " + p);
            status = pin.length() == 4 ? true : false;

        } catch (NumberFormatException e) {
            status = false;
        }
        return status;
    }

    public static boolean confirmEmail(String email) {
        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+.+$";
        return email.matches(regex) ? true : false;
    }


}
