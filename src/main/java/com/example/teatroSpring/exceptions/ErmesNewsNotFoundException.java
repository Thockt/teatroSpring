package com.example.teatroSpring.exceptions;

public class ErmesNewsNotFoundException extends Exception {

    @Override
    public String getMessage () { return "News non presente!"; }
}
