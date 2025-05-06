package br.com.vinicius.miniautorizador.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionRequest {

    @JsonProperty("numeroCartao")
    String cardNumber;
    @JsonProperty("senhaCartao")
    String cardPassword;
    @JsonProperty("valor")
    BigDecimal amount;
}
