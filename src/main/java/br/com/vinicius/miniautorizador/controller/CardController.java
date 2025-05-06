package br.com.vinicius.miniautorizador.controller;


import br.com.vinicius.miniautorizador.controller.request.CardRequest;
import br.com.vinicius.miniautorizador.service.CardService;
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
        return Optional.of(cardRequest.getCardNumber())
                .filter(number -> !cardService.existsByCardNumber(number))
                .map(number -> {
                    CardRequest created = cardService.createCreditCard(cardRequest);
                    return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201
                })
                .orElseGet(() -> {
                    logger.warn("Cartão já existe: {}", cardRequest.getCardNumber());
                    return ResponseEntity.unprocessableEntity().body(cardRequest); // 422
                });
    }

    @GetMapping(value = "/{numeroCartao}")
    public BigDecimal getCardBalance(@PathVariable String numeroCartao) {

        logger.info("Consultando o numero do cartao: {}", numeroCartao);
        return cardService.getBalance(numeroCartao);
    }


}