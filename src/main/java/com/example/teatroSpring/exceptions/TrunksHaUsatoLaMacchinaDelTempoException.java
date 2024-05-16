package com.example.teatroSpring.exceptions;

public class TrunksHaUsatoLaMacchinaDelTempoException  extends Exception {

    @Override
    public String getMessage (){ return ("Non puoi recensire uno spettacolo che non hai ancora visto"); }

}
