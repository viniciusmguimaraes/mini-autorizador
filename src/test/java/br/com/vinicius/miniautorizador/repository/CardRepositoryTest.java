package br.com.vinicius.miniautorizador.repository;

import br.com.vinicius.miniautorizador.domain.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa"
})

class CardRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CardRepository cardRepository;

    @Test
    void testFindByCardNumber_WhenCardExists() {

        Card card = new Card();
        card.setCardNumber("1234567890123456");
        card.setPassword("1234");
        card.setBalance(new BigDecimal("500.00"));
        entityManager.persist(card);
        entityManager.flush();


        Optional<Card> foundCard = cardRepository.findByCardNumber("1234567890123456");


        assertTrue(foundCard.isPresent());
        assertEquals(card.getCardNumber(), foundCard.get().getCardNumber());
        assertEquals(card.getPassword(), foundCard.get().getPassword());
        assertEquals(card.getBalance(), foundCard.get().getBalance());
    }

    @Test
    void testFindByCardNumber_WhenCardDoesNotExist() {

        Optional<Card> foundCard = cardRepository.findByCardNumber("9999999999999999");


        assertFalse(foundCard.isPresent());
    }

    @Test
    void testSaveCard() {

        Card card = new Card();
        card.setCardNumber("1234567890123456");
        card.setPassword("1234");
        card.setBalance(new BigDecimal("500.00"));


        Card savedCard = cardRepository.save(card);


        assertNotNull(savedCard.getId());
        assertEquals(card.getCardNumber(), savedCard.getCardNumber());
        assertEquals(card.getPassword(), savedCard.getPassword());
        assertEquals(card.getBalance(), savedCard.getBalance());
    }
}

