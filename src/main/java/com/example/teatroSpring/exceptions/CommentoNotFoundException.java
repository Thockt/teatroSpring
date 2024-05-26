package com.example.teatroSpring.exceptions;

public class CommentoNotFoundException extends Exception {

    @Override
    public String getMessage () { return "Questo commento non esiste!"; }
}
