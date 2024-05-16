package com.example.teatroSpring.exceptions;

public class GenereNonEsisteException extends Exception {

    @Override
    public String getMessage () { return "Questo genere non è presente!";}
}
