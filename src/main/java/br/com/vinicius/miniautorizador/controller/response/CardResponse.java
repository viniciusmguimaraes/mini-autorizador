package br.com.vinicius.miniautorizador.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardResponse(@JsonProperty("numeroCartao")
                           String cardNumber) {


}
