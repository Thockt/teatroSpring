package com.example.teatroSpring.exceptions;

public class ComuneNotFoundException extends Exception {

    @Override
    public String getMessage () { return "Comune non trovato!"; }

}
