package br.com.vinicius.miniautorizador.controller;


import br.com.vinicius.miniautorizador.controller.request.CardRequest;
import br.com.vinicius.miniautorizador.controller.response.CardResponse;
import br.com.vinicius.miniautorizador.exception.CardNotFoundException;
import br.com.vinicius.miniautorizador.service.CardService;
import br.com.vinicius.miniautorizador.util.enums.CardStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/cartoes")
public class CardController {

    private static final Logger logger = LogManager.getLogger(CardController.class);

    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardRequest> createCreditCard(@Valid @RequestBody CardRequest cardRequest){
        logger.info("Criando cartão para número: {}", cardRequest.getCardNumber());
        return ResponseEntity.ok(cardService.createCreditCard(cardRequest));
    }

    @GetMapping(value = "/{numeroCartao}")
    public ResponseEntity<BigDecimal> getCardBalance(@PathVariable String numeroCartao) {

        logger.info("Consultando o numero do cartao: {}", numeroCartao);
        return ResponseEntity.ok(cardService.getBalance(numeroCartao));

    }
}