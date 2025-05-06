package br.com.vinicius.miniautorizador.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardAlreadyExistExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {

        String errorMessage = "Cartão já existe";

        CardAlreadyExistException exception = new CardAlreadyExistException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void shouldInheritFromRuntimeException() {

        CardAlreadyExistException exception = new CardAlreadyExistException("Teste");

        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldPreserveStackTrace() {

        String errorMessage = "Cartão já existe";

        CardAlreadyExistException exception = new CardAlreadyExistException(errorMessage);

        assertNotNull(exception.getStackTrace());
    }

    @Test
    void shouldCreateExceptionWithNullMessage() {

        CardAlreadyExistException exception = new CardAlreadyExistException(null);

        assertNull(exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithEmptyMessage() {

        String emptyMessage = "";

        CardAlreadyExistException exception = new CardAlreadyExistException(emptyMessage);

        assertEquals(emptyMessage, exception.getMessage());
    }
}
