package Model;
import java.time.LocalTime;


public class Logger {
    private LocalTime occurenceTime;
    private String level;
    private String message;

    public Logger(String level, String message) {
        this.level = level;
        this.message = message;
        this.occurenceTime = LocalTime.now();
    }

    public LocalTime getOccurenceTime() {
        return occurenceTime;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
    public String toString(){
        return this.level +" : "+ this.message+ " : "+this.occurenceTime;
    }
}
