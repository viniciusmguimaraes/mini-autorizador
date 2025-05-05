package br.com.vinicius.miniautorizador.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardRequest {

    String cardNumber;
    String password;
}
