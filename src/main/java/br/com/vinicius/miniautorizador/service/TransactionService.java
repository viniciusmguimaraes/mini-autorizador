package br.com.vinicius.miniautorizador.service;

import br.com.vinicius.miniautorizador.controller.request.TransactionRequest;

public interface TransactionService {

    String carryOutTransaction(TransactionRequest request);
}
