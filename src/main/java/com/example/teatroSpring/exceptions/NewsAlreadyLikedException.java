package com.example.teatroSpring.exceptions;

public class NewsAlreadyLikedException extends Exception {

    @Override
    public String getMessage () { return "Hai già messo 'mi piace' su questa news"; }
}
