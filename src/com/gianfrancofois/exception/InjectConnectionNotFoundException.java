package com.gianfrancofois.exception;

public class InjectConnectionNotFoundException extends RuntimeException {

    public InjectConnectionNotFoundException() {
        super("You must have a Connection field annotated with @InjectConnection in the class using @Transactional");
    }
}
