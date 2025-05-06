package br.com.vinicius.miniautorizador.service.impl;

import br.com.vinicius.miniautorizador.controller.request.CardRequest;
import br.com.vinicius.miniautorizador.domain.Card;
import br.com.vinicius.miniautorizador.exception.CardAlreadyExistException;
import br.com.vinicius.miniautorizador.exception.CardNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card card;
    private static final String CARD_NUMBER = "1234567890123456";
    private static final String PASSWORD = "1234";
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("500.00");

    @BeforeEach
    void setUp() {
        card = Card.builder()
                .cardNumber(CARD_NUMBER)
                .password(PASSWORD)
                .balance(INITIAL_BALANCE)
                .build();
    }

    @Test
    @DisplayName("Deve criar um cartão com sucesso quando os dados são válidos")
    void createCreditCard_WithValidData_ShouldCreateCard() {
        CardRequest cardRequest = new CardRequest(CARD_NUMBER,PASSWORD);

        when(cardRepository.findByCardNumber(any())).thenReturn(Optional.empty());
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        CardRequest result = cardService.createCreditCard(cardRequest);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCardNumber()).isEqualTo(CARD_NUMBER);
        assertThat(result.getCardPassword()).isEqualTo(PASSWORD);
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar criar cartão que já existe")
    void createCreditCard_WithExistingCard_ShouldThrowException() {
        CardRequest cardRequest = new CardRequest(CARD_NUMBER,PASSWORD);

        when(cardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(card));

        CardAlreadyExistException exception = assertThrows(CardAlreadyExistException.class,
                () -> cardService.createCreditCard(cardRequest));

        assertThat(exception.getMessage()).isEqualTo(CardStatus.CARTAO_EXISTENTE.getMessage());
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Deve retornar o saldo quando o cartão existe")
    void getBalance_WithExistingCard_ShouldReturnBalance() {
        // Arrange
        when(cardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(card));

        // Act
        BigDecimal result = cardService.getBalance(CARD_NUMBER);

        // Assert
        assertThat(result).isEqualTo(INITIAL_BALANCE);
        verify(cardRepository, times(1)).findByCardNumber(CARD_NUMBER);
    }

    @Test
    @DisplayName("Deve lançar exceção quando buscar saldo de cartão inexistente")
    void getBalance_WithNonExistingCard_ShouldThrowException() {
        // Arrange
        when(cardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.empty());

        // Act & Assert
        CardNotFoundException exception = assertThrows(CardNotFoundException.class,
                () -> cardService.getBalance(CARD_NUMBER));

        assertThat(exception.getMessage()).isEqualTo(CardStatus.CARTAO_INEXISTENTE.getMessage());
        verify(cardRepository, times(1)).findByCardNumber(CARD_NUMBER);
    }
}
