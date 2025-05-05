package br.com.vinicius.miniautorizador.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface CardService {

    BigDecimal getBalance(String cardNumber);

}
