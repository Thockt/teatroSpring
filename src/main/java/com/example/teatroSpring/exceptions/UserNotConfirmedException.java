package com.example.teatroSpring.exceptions;

public class UserNotConfirmedException extends Exception {

    @Override
    public String getMessage() {
        return "Devi confermare il link via email per poter accedere!";
    }
}
