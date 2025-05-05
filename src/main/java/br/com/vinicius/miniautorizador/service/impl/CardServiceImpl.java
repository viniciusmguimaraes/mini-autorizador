package br.com.vinicius.miniautorizador.service.impl;

import br.com.vinicius.miniautorizador.service.CardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardServiceImpl implements CardService {

    @Override
    public BigDecimal getBalance(String cardNumber) {
        return null;
    }
}
