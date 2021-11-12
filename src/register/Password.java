package register;

public abstract class Password {
    private String message;

    public Password() {
        this.message = "";
    }

    public Password(String message) {
        this.message = message;
    }

    public Password(String pass1, String pass2, String checkRequired) {
        this.message = "";
    }

    public String getMessage() {
        return message;
    }



    public abstract boolean checkPassword(String pass1, String pass2);
}
