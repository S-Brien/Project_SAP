package monitor;

import Model.Logger;

import java.io.IOException;

public abstract class Observer {

    protected Subject subject;
    public abstract void update(Logger log) throws IOException;
}
