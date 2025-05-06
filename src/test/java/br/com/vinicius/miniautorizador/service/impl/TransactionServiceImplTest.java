package br.com.vinicius.miniautorizador.service.impl;

import br.com.vinicius.miniautorizador.controller.request.TransactionRequest;
import br.com.vinicius.miniautorizador.domain.Card;
import br.com.vinicius.miniautorizador.exception.CardNotFoundException;
import br.com.vinicius.miniautorizador.exception.InsufficientBalanceException;
import br.com.vinicius.miniautorizador.exception.InvalidPasswordException;
import br.com.vinicius.miniautorizador.repository.CardRepository;
import br.com.vinicius.miniautorizador.util.enums.CardStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private static final String CARD_NUMBER = "1234567890123456";
    private static final String PASSWORD = "1234";
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(500.00);
    private static final BigDecimal VALID_TRANSACTION_VALUE = BigDecimal.valueOf(100.00);

    private Card card;
    private TransactionRequest transactionRequest;

    @BeforeEach
    void setUp() {
        card = Card.builder()
                .cardNumber(CARD_NUMBER)
                .password(PASSWORD)
                .balance(INITIAL_BALANCE)
                .build();
    }

    @Test
    @DisplayName("Deve realizar transação com sucesso quando todos os dados são válidos")
    void carryOutTransaction_WithValidData_ShouldCompleteTransaction() {
        TransactionRequest transactionRequest = new TransactionRequest(CARD_NUMBER, PASSWORD, VALID_TRANSACTION_VALUE);
        BigDecimal expectedBalance = INITIAL_BALANCE.subtract(VALID_TRANSACTION_VALUE);
        when(cardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        String result = transactionService.carryOutTransaction(transactionRequest);

        assertThat(result).isEqualTo(CardStatus.OK.getMessage());
        assertThat(card.getBalance()).isEqualTo(expectedBalance);
        verify(cardRepository).findByCardNumber(CARD_NUMBER);
        verify(cardRepository).save(card);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cartão não for encontrado")
    void carryOutTransaction_WithNonexistentCard_ShouldThrowCardNotFoundException() {
        TransactionRequest transactionRequest = new TransactionRequest(CARD_NUMBER, PASSWORD, VALID_TRANSACTION_VALUE);

        when(cardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.carryOutTransaction(transactionRequest))
                .isInstanceOf(CardNotFoundException.class)
                .hasMessage(CardStatus.CARTAO_INEXISTENTE.getMessage());

        verify(cardRepository).findByCardNumber(CARD_NUMBER);
        verify(cardRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha estiver incorreta")
    void carryOutTransaction_WithInvalidPassword_ShouldThrowInvalidPasswordException() {
        TransactionRequest transactionRequest = new TransactionRequest(CARD_NUMBER, PASSWORD, VALID_TRANSACTION_VALUE);

        transactionRequest.setCardPassword("senha_errada");
        when(cardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(card));

        // Act & Assert
        assertThatThrownBy(() -> transactionService.carryOutTransaction(transactionRequest))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage(CardStatus.SENHA_INVALIDA.getMessage());

        verify(cardRepository).findByCardNumber(CARD_NUMBER);
        verify(cardRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o saldo for insuficiente")
    void carryOutTransaction_WithInsufficientBalance_ShouldThrowInsufficientBalanceException() {
        TransactionRequest transactionRequest = new TransactionRequest(CARD_NUMBER, PASSWORD, VALID_TRANSACTION_VALUE);

        transactionRequest.setAmount(BigDecimal.valueOf(600.00));
        when(cardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(card));

        assertThatThrownBy(() -> transactionService.carryOutTransaction(transactionRequest))
                .isInstanceOf(InsufficientBalanceException.class)
                .hasMessage(CardStatus.SALDO_INSUFICIENTE.getMessage());

        verify(cardRepository).findByCardNumber(CARD_NUMBER);
        verify(cardRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve realizar transação com sucesso quando o valor é igual ao saldo")
    void carryOutTransaction_WithExactBalance_ShouldCompleteTransaction() {
        TransactionRequest transactionRequest = new TransactionRequest(CARD_NUMBER, PASSWORD, null);

        transactionRequest.setAmount(INITIAL_BALANCE);
        when(cardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        String result = transactionService.carryOutTransaction(transactionRequest);

        assertThat(result).isEqualTo(CardStatus.OK.getMessage());
        assertThat(card.getBalance().compareTo(BigDecimal.ZERO)).isZero();
        verify(cardRepository).findByCardNumber(CARD_NUMBER);
        verify(cardRepository).save(card);
    }
}