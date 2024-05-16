package com.example.teatroSpring.exceptions;

public class SedeNotFoundException  extends Exception {

    @Override
    public String getMessage () { return ("La sede con questo id  non è presente!"); }
}
