
package br.com.vinicius.miniautorizador.service.impl;

import br.com.vinicius.miniautorizador.controller.request.TransactionRequest;
import br.com.vinicius.miniautorizador.domain.Card;
import br.com.vinicius.miniautorizador.util.enums.TransactionStatus;
import br.com.vinicius.miniautorizador.exception.CardNotFoundException;
import br.com.vinicius.miniautorizador.exception.InsufficientBalanceException;
import br.com.vinicius.miniautorizador.exception.InvalidPasswordException;
import br.com.vinicius.miniautorizador.repository.CardRepository;
import br.com.vinicius.miniautorizador.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final CardRepository cardRepository;

    public TransactionServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public String carryOutTransaction(TransactionRequest request) {
        return cardRepository.findByCardNumber(request.getCardNumber())
                .map(card -> {
                    try {
                        validatePassword(card, request.getCardPassword());
                        validateBalance(card, request.getAmount());

                        card.setBalance(card.getBalance().subtract(request.getAmount()));
                        cardRepository.save(card);

                        logger.info("Transação realizada com sucesso para o cartão: {}", request.getCardNumber());
                        return TransactionStatus.OK.name();

                    } catch (InvalidPasswordException e) {
                        return TransactionStatus.SENHA_INVALIDA.name();
                    } catch (InsufficientBalanceException e) {
                        return TransactionStatus.SALDO_INSUFICIENTE.name();
                    }
                })
                .orElseGet(() -> {
                    logger.warn("Cartão inexistente: {}", request.getCardNumber());
                    return TransactionStatus.CARTAO_INEXISTENTE.name();
                });
    }

        private void validatePassword(Card card, String password) {
        if (!card.getPassword().equals(password)) {
            logger.warn("Senha inválida para o cartão: {}", card.getCardNumber());
            throw new InvalidPasswordException(TransactionStatus.SENHA_INVALIDA.getMessage());
        }
    }

    private void validateBalance(Card card, BigDecimal amount) {
        if (card.getBalance().compareTo(amount) < 0) {
            logger.warn("Saldo insuficiente para o cartão: {}", card.getCardNumber());
            throw new InsufficientBalanceException(TransactionStatus.SALDO_INSUFICIENTE.getMessage());
        }
    }
}