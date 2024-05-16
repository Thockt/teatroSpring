package com.example.teatroSpring.exceptions;

public class RecensioneNotFoundException extends Exception {

    @Override
    public String getMessage(){ return ("Questa recensione non è presente!");}
}
