package br.com.vinicius.miniautorizador.util.enums;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    CARD_NOT_FOUND("Cartão não encontrado"),
    INVALID_PASSWORD("Senha inválida"),
    INSUFFICIENT_BALANCE("Saldo insuficiente");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
