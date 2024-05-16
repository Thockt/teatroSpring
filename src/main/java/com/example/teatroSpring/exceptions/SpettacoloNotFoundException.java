package com.example.teatroSpring.exceptions;

public class SpettacoloNotFoundException extends Exception {

    @Override
    public String getMessage () { return ("Lo spettacolo con questo id non è presente!"); }

}
