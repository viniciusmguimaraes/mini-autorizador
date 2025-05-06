package br.com.vinicius.miniautorizador.exception;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {

        String errorMessage = "Cartão não encontrado";

        CardNotFoundException exception = new CardNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void shouldInheritFromRuntimeException() {

        CardNotFoundException exception = new CardNotFoundException("Teste");

        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldPreserveStackTrace() {

        String errorMessage = "Cartão não encontrado";

        CardNotFoundException exception = new CardNotFoundException(errorMessage);

        assertNotNull(exception.getStackTrace());
    }

    @Test
    void shouldCreateExceptionWithNullMessage() {

        CardNotFoundException exception = new CardNotFoundException(null);

        assertNull(exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithEmptyMessage() {

        String emptyMessage = "";

        CardNotFoundException exception = new CardNotFoundException(emptyMessage);

        assertEquals(emptyMessage, exception.getMessage());
    }
}
