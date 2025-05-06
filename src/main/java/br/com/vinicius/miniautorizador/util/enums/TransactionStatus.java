package br.com.vinicius.miniautorizador.util.enums;

public enum TransactionStatus {
    OK("Transação realizada com sucesso"),
    SALDO_INSUFICIENTE("Saldo insuficiente"),
    SENHA_INVALIDA("Senha inválida"),
    CARTAO_INEXISTENTE("Cartão inexistente");

    private final String message;

    TransactionStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}