package com.example.teatroSpring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException (IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse("Illegal Argument Exception", "Id non presente nella tabella");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenereNonEsisteException.class)
    public ResponseEntity<ErrorResponse> handleGenereNonEsisteException (GenereNonEsisteException e) {
        ErrorResponse errorResponse = new ErrorResponse("Genere Non Esiste Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ComuneNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleComuneNotFoundException (ComuneNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Comune Not Found Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecensioneNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecensioneNotFoundException (RecensioneNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Recensione Not Found Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UtenteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUtenteNotFoundException (UtenteNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Utente Not Found Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpettacoloNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSpettacoloNotFoundException (SpettacoloNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Spettacolo Not Found Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SedeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSedeNotFoundException (SedeNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Sede Not Found Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TrunksHaUsatoLaMacchinaDelTempoException.class)
    public ResponseEntity<ErrorResponse> handleTrunksHaUsatoLaMacchinaDelTempoException (TrunksHaUsatoLaMacchinaDelTempoException e) {
        ErrorResponse errorResponse = new ErrorResponse("Trunks Ha Usato La Macchina Del Tempo Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BigliettoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBigliettoNotFoundException (BigliettoNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Biglietto Not Found Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BigliettoFasulloException.class)
    public ResponseEntity<ErrorResponse> handleBigliettoFasulloException (BigliettoFasulloException e) {
        ErrorResponse errorResponse = new ErrorResponse("Biglietto Fasullo Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
