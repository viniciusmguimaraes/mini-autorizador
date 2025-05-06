package br.com.vinicius.miniautorizador.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardRequest {

    @JsonProperty("numeroCartao")
    String cardNumber;
    @JsonProperty("senhaCartao")
    String cardPassword;
}
