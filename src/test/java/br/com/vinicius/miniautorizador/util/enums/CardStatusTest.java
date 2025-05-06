package br.com.vinicius.miniautorizador.util.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardStatusTest {

    @Test
    void shouldReturnCorrectMessageToStatusOK() {
        assertEquals("Transação realizada com sucesso", CardStatus.OK.getMessage());
    }

    @Test
    void shouldReturnCorrectMessageForInsufficientBalance() {
        assertEquals("Saldo insuficiente", CardStatus.SALDO_INSUFICIENTE.getMessage());
    }

    @Test
    void shouldReturnCorrectMessageForInvalidPassword() {
        assertEquals("Senha inválida", CardStatus.SENHA_INVALIDA.getMessage());
    }

    @Test
    void shouldReturnCorrectMessageForNon() {
        assertEquals("Cartão inexistente", CardStatus.CARTAO_INEXISTENTE.getMessage());
    }

    @Test
    void shouldReturnCorrectMessageForInternalError() {
        assertEquals("Erro Interno", CardStatus.ERRO_INTERNO.getMessage());
    }

    @Test
    void shouldReturnCorrectMessageForCardAlreadyExist() {
        assertEquals("Cartão existente", CardStatus.CARTAO_EXISTENTE.getMessage());
    }

    @Test
    void mustCheckQuantityOfEnums() {
        assertEquals(6, CardStatus.values().length);
    }

    @Test
    void shouldConvertStringToEnum() {
        assertEquals(CardStatus.OK, CardStatus.valueOf("OK"));
    }

    @Test
    void mustGenerateExcecaoParaEnumInvalido() {
        assertThrows(IllegalArgumentException.class, () -> CardStatus.valueOf("STATUS_INVALIDO"));
    }

    @Test
    void mustCheckEnumOrder() {
        CardStatus[] statusEmOrdem = CardStatus.values();
        assertEquals(CardStatus.OK, statusEmOrdem[0]);
        assertEquals(CardStatus.SALDO_INSUFICIENTE, statusEmOrdem[1]);
        assertEquals(CardStatus.SENHA_INVALIDA, statusEmOrdem[2]);
        assertEquals(CardStatus.CARTAO_INEXISTENTE, statusEmOrdem[3]);
        assertEquals(CardStatus.ERRO_INTERNO, statusEmOrdem[4]);
        assertEquals(CardStatus.CARTAO_EXISTENTE, statusEmOrdem[5]);
    }
}

