package br.com.vinicius.miniautorizador.service;

import br.com.vinicius.miniautorizador.controller.request.CardRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface CardService {

    CardRequest createCreditCard(CardRequest card);
    BigDecimal getBalance(String cardNumber);

}
