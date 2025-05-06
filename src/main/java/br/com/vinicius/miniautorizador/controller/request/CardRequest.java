package br.com.vinicius.miniautorizador.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardRequest {

    @JsonProperty("numeroCartao")
    String cardNumber;
    @JsonProperty("senhaCartao")
    String cardPassword;
}
