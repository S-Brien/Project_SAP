package monitor;

import Model.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PinObserver extends Observer{
    Subject monitor = new Subject();
    Observer osP = new PinObserver(monitor);

    public PinObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update(Logger log) throws IOException {
        System.out.println("Pin " + log.toString());
        try(FileWriter fileW = new FileWriter("pinUsage.txt", true);
            BufferedWriter buff = new BufferedWriter(fileW);
            PrintWriter printW = new PrintWriter(buff))
        {
            printW.println(log.toString());
        }
        catch (IOException e){
            monitor.setLog(new Logger("warn", e.getCause().getMessage()));
        }

    }
}
