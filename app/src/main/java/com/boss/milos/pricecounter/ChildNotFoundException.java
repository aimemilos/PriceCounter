package com.boss.milos.pricecounter;

/**
 * Created by MiloS on 30.08.2016.
 */
public class ChildNotFoundException extends Exception {
    public ChildNotFoundException() {
        super();
    }

    public ChildNotFoundException(String message) {
        super(message);
    }

    public ChildNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChildNotFoundException(Throwable cause) {
        super(cause);
    }
}
