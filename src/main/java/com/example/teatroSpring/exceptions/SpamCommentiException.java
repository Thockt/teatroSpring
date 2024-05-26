package com.example.teatroSpring.exceptions;

public class SpamCommentiException extends Exception {

    @Override
    public String getMessage () {
        return "Hai commentato troppo di recente! Devi aspettare";
    }
}
