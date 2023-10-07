package br.com.itau.controller;

import br.com.itau.schema.Pessoa;
import br.com.itau.service.CalculoLimiteCreditoService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/credito")
public class LimiteCreditoController {

    @Inject
    CalculoLimiteCreditoService calculoLimiteCreditoService;

    @POST
    @Path("/disponibilidade")
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Pessoa pessoa) {

        var resultadoCalculo = calculoLimiteCreditoService.calculoNovoLimiteCredito(
                pessoa.getEndividamento().getValorTotal(),
                pessoa.getLimiteCredito().getValorTotal(),
                pessoa.getScore(),
                pessoa.getContaCorrente().getSaldo());

        var novoLimite = Pessoa.createObjectPessoaFromNovoLimite(pessoa.getIdPessoa(), resultadoCalculo);

        return Response.ok(novoLimite).build();

    }

}
