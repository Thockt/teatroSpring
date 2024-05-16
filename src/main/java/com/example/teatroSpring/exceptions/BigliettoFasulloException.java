package com.example.teatroSpring.exceptions;

public class BigliettoFasulloException extends Exception {

    @Override
    public String getMessage () { return ("Uno dei campi inseriti non esiste!"); }

}
