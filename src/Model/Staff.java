package Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Staff {
    private int UserID;
    private String UserName;
    private String UserPass;
    private String Pin;
    private String Locked;
    private String LockAttempt;
    private String Email;
    public ObjectProperty<LOCK> lock = new SimpleObjectProperty<>(LOCK.UNLOCKED);
    public ObjectProperty<ROLE> role = new SimpleObjectProperty<>(ROLE.USER);

    public Staff() {
    }

    public Staff(String userName, String role, String locked, String lockAttempt) {
        UserName = userName;
        LockAttempt = lockAttempt;
        this.lock = new SimpleObjectProperty<>(LOCK.valueOf(locked));
        this.role = new SimpleObjectProperty<>(ROLE.valueOf(role));
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public ROLE getRole() {return role.get();}
    public void setRole(ROLE r) {
        role.set(r);
    }

    public LOCK getLock() {return lock.get();}
    public void setLock(LOCK b) {lock.set(b);}

    public String getLocked() {
        return Locked;
    }

    public void setLocked(String locked) {
        Locked = locked;
    }

    public String getLockAttempt() {
        return LockAttempt;
    }

    public void setLockAttempt(String lockAttempt) {
        LockAttempt = lockAttempt;
    }



    public static enum LOCK {LOCKED, UNLOCKED}
    public static enum ROLE {USER, SUPER, ADMIN}
}
