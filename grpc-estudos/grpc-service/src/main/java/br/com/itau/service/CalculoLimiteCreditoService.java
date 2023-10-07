package br.com.itau.service;

import javax.enterprise.context.ApplicationScoped;
import java.util.stream.Stream;

@ApplicationScoped
public class CalculoLimiteCreditoService {

    //@Ingect
    //QuarkusLibLogger log;

    public Double calculoNovoLimiteCredito(final Double endividamentoTotal, final Double limiteCredito, final Long score, final Double saldoContaCorrente) {
        var coeficientes = Stream.of(calculoCoeficienteEndividamento(endividamentoTotal),
                calculoCoeficienteLimiteCredito(limiteCredito),
                calculoCoeficienteScore(score),
                calculoCoeficienteConta(saldoContaCorrente)).reduce(0.0, CalculoLimiteCreditoService::add);

        //log.info("Percentual de aumento de limite: " + (coeficiente * 100) + "%");

        var valorAumento = limiteCredito * coeficientes;
        var novoLimiteCredito = limiteCredito + valorAumento;

        //log.info("Limite atual: " + LimiteCredito + ", Valor de Aumento: " + valorAumento + ", " + "Novo limite: " + + novoLimiteCredito);

        return novoLimiteCredito;
    }

    private Double calculoCoeficienteEndividamento(final Double endividamentoTotal) {
        if (endividamentoTotal == 0) {
            //log.info("coeficiente de endividamento: 0.5");
            return 0.5;
        }
        if (endividamentoTotal >= 1000.0 && endividamentoTotal <= 3000) {
            //log.info("coeficiente de endividamento: 0.1");
            return 0.1;
        }
        if (endividamentoTotal >= 3000.01) {
            //log.info("coeficiente de endividamento: 0.0");
            return 0.0;
        }
        //log.info("coeficiente de endividamento: 0.3);
        return 0.3;
    }

    private Double calculoCoeficienteLimiteCredito(final Double limiteCredito) {
        if (limiteCredito == 0) {
            //log.info("coeficiente de limite de credito: 0.3");
            return 0.3;
        }
        if (limiteCredito >= 1000.0 && limiteCredito <= 3000) {
            //log.info("coeficiente de limite de credito: 0.5");
            return 0.5;
        }
        //log.info("coeficiente de limite de credito: 0.1);
        return 0.1;
    }

    private Double calculoCoeficienteScore(final Long score) {
        if (score >= 0 && score <= 300) {
            //log.info("coeficiente de score: 0.1");
            return 0.1;
        }
        if (score >= 301 && score <= 700) {
            //log.info("coeficiente de score: 0.15");
            return 0.15;
        }
        //log.info("coeficiente de limite de credito: 0.2);
        return 0.2;
    }

    private Double calculoCoeficienteConta(final Double saldoContaCorrente) {
        if (saldoContaCorrente >= 0 && saldoContaCorrente <= 1000.00) {
            //log.info("coeficiente de saldo de conta corrente: 0.2");
            return 0.2;
        }
        if (saldoContaCorrente >= 1001.00 && saldoContaCorrente <= 5000.00) {
            //log.info("coeficiente de saldo de conta corrente: 0.1");
            return 0.5;
        }
        //log.info("coeficiente de limite de credito: 0.2);
        return 0.1;
    }

    private static Double add(Double a, Double b) {
        return a + b;
    }

}
