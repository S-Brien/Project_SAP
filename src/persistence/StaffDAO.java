package persistence;

import Model.Logger;
import Model.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import monitor.FileObserver;
import monitor.Observer;
import monitor.SQLiteObserver;
import monitor.Subject;

import javax.management.relation.Role;
import java.sql.*;


public class StaffDAO {
    static Subject monitor = new Subject();
    static Observer obsi = new SQLiteObserver(monitor);
    static Observer obs2 = new FileObserver(monitor);


    public static boolean validateUser(String name, String password) {
        boolean status = false;
        String sql = "SELECT * FROM staff WHERE UserName = ? AND UserPass = ?";
        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }

        return status;
    }

    public static boolean validatePin(String PIN){
        boolean p = false;
        try{
            Connection con = DBMySQL.getInstance().getConnection();
            String sl = "select * from Staff where pin= " + PIN;
            Statement selectStatement = con.createStatement();
            ResultSet rs = selectStatement.executeQuery(sl);
            p = rs.next();
        }catch(Exception e){
            monitor.setLog(new Logger("warn", e.getMessage()));
        }
        return p;
    }


    public static String getRole(String name) {
        String role = "user";
        String sql = "select * from Staff where UserName=?";

        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    role = rs.getString("Role");
                }
            }
        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }

        return role;
    }

    public static int getPin(String name){
        int pin = 1234;

        String sql = "select * from Staff where UserName=?";
        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pin = rs.getInt("Pin");
                }
            }
        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }


        return pin;
    }

    public static boolean checkIfExists(String UserName) {
        boolean status = false;
        String sql = "select * from Staff where UserName=?";

        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, UserName);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }
        return status;
    }


    public static int addUser(String userName, String userPass, String userPin, String userEmail) throws SQLException {
        int status = 0;
        String role = "user";
        String sql = "insert into Staff(UserName, UserPass, Role, Pin, Email) values(?,?,?,?,?)";
        int pin = 1234;
        pin = Integer.valueOf(userPin);

        try (Connection con = persistence.DBMySQL.getInstance().getConnection()){
                try(PreparedStatement ps = con.prepareStatement(sql)){
                    ps.setString(1, userName);
                    ps.setString(2, userPass);
                    ps.setString(3, role);
                    ps.setInt(4, pin);
                    ps.setString(5, userEmail);
                    status = ps.executeUpdate();
                }catch(SQLException e){
                    monitor.setLog(new Logger("warn", e.getMessage()));
                }
            } catch (Exception e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }

        return status;
    }

    public static int deleteUser(String userName) {
        int status = 0;
        try (Connection con = persistence.DBMySQL.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("delete from Staff where UserName=?")) {
                ps.setString(1, userName);
                status = ps.executeUpdate();
            } catch (SQLException e) {
                monitor.setLog(new Logger("warn", e.getMessage()));

            }
        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }
        return status;
    }

    public static ObservableList<Staff> getAllStaff() {
        ObservableList<Staff> data = FXCollections.observableArrayList();
        try {
            String SQL = "Select * from staff";
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            try (ResultSet rs = con.createStatement().executeQuery(SQL)) {
                while (rs.next()) {
                    String UserName = rs.getString("UserName");
                    String Role = rs.getString("Role").toUpperCase();
                    String Locked = rs.getString("Locked");
                    String LockAttempt = String.valueOf(rs.getInt("LockAttempt"));
                    Staff cm = new Staff(UserName, Role, Locked, LockAttempt);
                    data.add(cm);
                }
            }

        } catch (Exception e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        } finally {
            return data;
        }
    }

    public static String updateLockedData(String userName, String lockedState) {
        String msg = "";
        String sql = "UPDATE Staff SET Locked = ?, LockAttempt = ? WHERE UserName = ?";
        String role = getRole((userName));
        int adminLockAttemts = 15;
        int lockAttempts = 10;
        int LockAttemptAllowed = role.equals("admin") ? adminLockAttemts : lockAttempts;

        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps1 = con.prepareStatement(sql)) {
            ps1.setString(1, lockedState);
            ps1.setInt(2, LockAttemptAllowed );
            ps1.setString(3, userName);
            int row = ps1.executeUpdate();
            msg = "Database updated";

        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }
        return msg;
    }

    public static String updateRole(String userName, String role) {
        String msg = "";
        String sql = "UPDATE Staff SET role = ? WHERE UserName = ?";
        role = role.toUpperCase();
        System.out.println("Role to update "+role);

        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps1 = con.prepareStatement(sql)) {
            ps1.setString(1, role);
            ps1.setString(2, userName );
            int row = ps1.executeUpdate();
            msg = "Database updated";

        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }
        return msg;
    }

    public static String updateLockedAttempt(String userName){
        String msg = "";
        String unLockedState = "UNLOCKED";
        String lockedState = "LOCKED";
        String State = "";
        String sql = "UPDATE Staff SET Locked = ?, LockAttempt = ? WHERE UserName = ?";
        int lockAttempt = getLockedAttempts(userName) - 1;
        State = lockAttempt <= 0 ? lockedState : unLockedState;

        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps1 = con.prepareStatement(sql)) {
            ps1.setString(1, State);
            ps1.setInt(2, lockAttempt );
            ps1.setString(3, userName);
            ps1.executeUpdate();
            msg = "Database updated  "+lockAttempt+" State "+State;

        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }
        return msg;
    }

    public static int getLockedAttempts(String userName){
        int la= -1;
        String sql = "select LockAttempt from Staff where UserName=?";

        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    la = rs.getInt("LockAttempt");
                }
            }
        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }
        return la;
    }

    public static boolean validateLockedState(String name) {
        boolean status = false;
        String State = "";
        String sql = "SELECT * FROM staff WHERE UserName = ? ";
        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    State = rs.getString("Locked");
                    status = State.equals("UNLOCKED");
                }
            }
        } catch (SQLException e) {
            monitor.setLog(new Logger("warn", e.getMessage()));
        }
        return status;
    }

}
