package com.example.teatroSpring.exceptions;

public class ScheduledErmesNewsNotFoundException extends Exception {

    @Override
    public String getMessage () { return "News non presente!"; }

}
