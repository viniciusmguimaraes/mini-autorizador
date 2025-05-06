package br.com.vinicius.miniautorizador.exception;

public class CardAlreadyExistException extends RuntimeException {
    public CardAlreadyExistException(String message) {
        super(message);
    }
}
