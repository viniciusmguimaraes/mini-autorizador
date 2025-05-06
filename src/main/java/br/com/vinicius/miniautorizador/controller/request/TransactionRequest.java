package br.com.vinicius.miniautorizador.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionRequest {

    @JsonProperty("numeroCartao")
    String cardNumber;
    @JsonProperty("senhaCartao")
    String cardPassword;
    @JsonProperty("valor")
    BigDecimal amount;
}
