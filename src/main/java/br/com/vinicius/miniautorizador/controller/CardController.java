package br.com.vinicius.miniautorizador.controller;


import br.com.vinicius.miniautorizador.controller.request.CardRequest;
import br.com.vinicius.miniautorizador.service.CardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/cartoes")
public class CardController {

    private static final Logger logger = LogManager.getLogger(CardController.class);

    private CardService cardService;

    @PostMapping
    public String createCreditCard(@RequestBody CardRequest cardRequest){


        return null;
    }

    @GetMapping(value = "/{numeroCartao}")
    public BigDecimal getCardBalance(@PathVariable String numeroCartao) {

        logger.info("Consultando o numero do cartao: {}", numeroCartao);

        var balance = cardService.getBalance(numeroCartao);


        return null;
    }


}