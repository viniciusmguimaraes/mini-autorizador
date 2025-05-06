package br.com.vinicius.miniautorizador.service.impl;

import br.com.vinicius.miniautorizador.controller.request.CardRequest;
import br.com.vinicius.miniautorizador.domain.Card;
import br.com.vinicius.miniautorizador.exception.CardAlreadyExistException;
import br.com.vinicius.miniautorizador.exception.CardNotFoundException;
import br.com.vinicius.miniautorizador.repository.CardRepository;
import br.com.vinicius.miniautorizador.service.CardService;
import br.com.vinicius.miniautorizador.util.enums.CardStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("500.00");

    final private CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public CardRequest createCreditCard(CardRequest card) {

        //Valida se o CardNumber existe no banco de dados
        Optional.of(card.getCardNumber())
                .filter(this::existsByCardNumber)
                .ifPresent(cardNumber -> {
                    throw new CardAlreadyExistException(CardStatus.CARTAO_EXISTENTE.getMessage());
                });

        Card newCard = Card.builder().balance(INITIAL_BALANCE)
                .cardNumber(card.getCardNumber())
                .password(card.getCardPassword()).build();

        cardRepository.save(newCard);
        return card;
    }

    @Override
    public BigDecimal getBalance(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .map(Card::getBalance)
                .orElseThrow(() -> new CardNotFoundException(CardStatus.CARTAO_INEXISTENTE.getMessage()));
    }

    private boolean existsByCardNumber(String numeroCartao) {
        return cardRepository.findByCardNumber(numeroCartao).isPresent();
    }

}
