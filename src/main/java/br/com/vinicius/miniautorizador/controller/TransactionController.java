package br.com.vinicius.miniautorizador.controller;

import br.com.vinicius.miniautorizador.controller.request.TransactionRequest;
import br.com.vinicius.miniautorizador.service.TransactionService;
import br.com.vinicius.miniautorizador.util.enums.CardStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/transacoes")
public class TransactionController {

    private static final Logger logger = LogManager.getLogger(TransactionController.class);
    private final TransactionService transactionService;


    @PostMapping
    public ResponseEntity<String> processTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        logger.info("Iniciando transação para o cartão {}", transactionRequest.getCardNumber());

        return ResponseEntity.ok(transactionService.carryOutTransaction(transactionRequest));
    }
}
