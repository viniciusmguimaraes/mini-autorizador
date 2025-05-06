
package br.com.vinicius.miniautorizador.service.impl;

import br.com.vinicius.miniautorizador.controller.request.TransactionRequest;
import br.com.vinicius.miniautorizador.domain.Card;
import br.com.vinicius.miniautorizador.exception.CardNotFoundException;
import br.com.vinicius.miniautorizador.service.CardService;
import br.com.vinicius.miniautorizador.util.enums.CardStatus;
import br.com.vinicius.miniautorizador.exception.InsufficientBalanceException;
import br.com.vinicius.miniautorizador.exception.InvalidPasswordException;
import br.com.vinicius.miniautorizador.repository.CardRepository;
import br.com.vinicius.miniautorizador.service.TransactionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final CardRepository cardRepository;

    @Override
    public String carryOutTransaction(TransactionRequest request) {
        return cardRepository.findByCardNumber(request.getCardNumber())
                .map(card -> {
                        validatePassword(card, request.getCardPassword());
                        validateBalance(card, request.getAmount());

                        card.setBalance(card.getBalance().subtract(request.getAmount()));
                        cardRepository.save(card);

                        logger.info("Transação realizada com sucesso para o cartão: {}", request.getCardNumber());
                        return CardStatus.OK.getMessage();
                }).orElseThrow(() -> new CardNotFoundException(CardStatus.CARTAO_INEXISTENTE.getMessage()));

    }

    private void validatePassword(Card card, String password) {
        Optional.of(card.getPassword())
                .filter(pwd -> pwd.equals(password))
                .orElseGet(() -> {
                    logger.warn("Senha inválida para o cartão: {}", card.getCardNumber());
                    throw new InvalidPasswordException(CardStatus.SENHA_INVALIDA.getMessage());
                });
    }


    private void validateBalance(Card card, BigDecimal amount) {
        Optional.of(card.getBalance().compareTo(amount))
                .filter(comparison -> comparison >= 0)
                .orElseGet(() -> {
                    logger.warn("Saldo insuficiente para o cartão: {}", card.getCardNumber());
                    throw new InsufficientBalanceException(CardStatus.SALDO_INSUFICIENTE.getMessage());
                });
    }
}