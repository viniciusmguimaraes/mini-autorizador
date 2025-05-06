package br.com.vinicius.miniautorizador.util.enums;

public enum CardStatus {
    OK("Transação realizada com sucesso"),
    SALDO_INSUFICIENTE("Saldo insuficiente"),
    SENHA_INVALIDA("Senha inválida"),
    CARTAO_INEXISTENTE("Cartão inexistente"),
    ERRO_INTERNO("Erro Interno"),
    CARTAO_EXISTENTE("Cartão existente");


    private final String message;

    CardStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}