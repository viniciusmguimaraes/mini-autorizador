package br.com.vinicius.miniautorizador.service.impl;

import br.com.vinicius.miniautorizador.controller.request.CardRequest;
import br.com.vinicius.miniautorizador.domain.Card;
import br.com.vinicius.miniautorizador.repository.CardRepository;
import br.com.vinicius.miniautorizador.service.CardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardServiceImpl implements CardService {

    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("500.00");

    final private CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public boolean existsByCardNumber(String numeroCartao) {
        return cardRepository.findByCardNumber(numeroCartao).isPresent();
    }

    @Override
    public CardRequest createCreditCard(CardRequest card) {
        Card newCard = new Card();
        newCard.setBalance(INITIAL_BALANCE);
        newCard.setCardNumber(card.getCardNumber());
        newCard.setPassword(card.getCardPassword());

        cardRepository.save(newCard);
        return card;
    }

    @Override
    public BigDecimal getBalance(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .map(Card::getBalance)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
    }

}
