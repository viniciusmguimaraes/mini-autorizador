package br.com.vinicius.miniautorizador.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CardTest {

    @Test
    void testCardGettersAndSetters() {

        Card card = new Card();
        Long id = 1L;
        String cardNumber = "1234567890123456";
        String password = "1234";
        BigDecimal balance = new BigDecimal("500.00");


        card.setId(id);
        card.setCardNumber(cardNumber);
        card.setPassword(password);
        card.setBalance(balance);


        assertEquals(id, card.getId());
        assertEquals(cardNumber, card.getCardNumber());
        assertEquals(password, card.getPassword());
        assertEquals(balance, card.getBalance());
    }

    @Test
    void testCardConstructor() {

        Card card = new Card();


        assertNull(card.getId());
        assertNull(card.getCardNumber());
        assertNull(card.getPassword());
        assertNull(card.getBalance());
    }

}
