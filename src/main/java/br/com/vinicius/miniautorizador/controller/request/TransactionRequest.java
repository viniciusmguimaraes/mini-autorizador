package br.com.vinicius.miniautorizador.controller.request;

import java.math.BigDecimal;

public class TransactionRequest {

    String cardNumber;
    String password;
    BigDecimal balance;
}
