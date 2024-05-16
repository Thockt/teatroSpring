package com.example.teatroSpring.exceptions;

public class BigliettoNotFoundException extends Exception {

    @Override
    public String getMessage() { return ("Il biglietto con questo id non è presente!"); }

}
