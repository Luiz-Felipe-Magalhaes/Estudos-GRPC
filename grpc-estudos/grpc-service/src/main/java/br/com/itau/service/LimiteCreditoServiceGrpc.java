package br.com.itau.service;

import br.com.itau.LimiteCreditoGrpc;
import br.com.itau.LimiteCreditoInput;
import br.com.itau.LimiteCreditoOutput;
import br.com.itau.service.CalculoLimiteCreditoService;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

import javax.inject.Inject;
import java.time.Instant;
import java.util.UUID;

@GrpcService
public class LimiteCreditoServiceGrpc extends LimiteCreditoGrpc.LimiteCreditoImplBase {

    //@Inject
    //QuarkusLibLogger log;

    @Inject
    CalculoLimiteCreditoService calculoLimiteCreditoService;

    @Override
    public void getLimiteCredito(LimiteCreditoInput request, StreamObserver<LimiteCreditoOutput> responseObserver) {

        //QuarkusLibLogger.setInitProcessingTime();
        //log.info("Mensagem recebida");

        var percentual = calculoLimiteCreditoService.calculoNovoLimiteCredito(request.getEndividamento().getValorTotal(),
                request.getLimiteCredito().getValorTotal(),
                (long) request.getScore(),
                request.getContaCorrente().getSaldo());

        var novoLimite = createObjectPessoaFromNovoLimite(UUID.fromString(request.getIdPessoa()), percentual);

        responseObserver.onNext(novoLimite);
        responseObserver.onCompleted();
    }

    public static LimiteCreditoOutput createObjectPessoaFromNovoLimite(final UUID idPessoa, final Double novoLimiteCreditoPessoa) {
        var novoLimite = LimiteCreditoOutput.Limite_credito.newBuilder().setValorTotal(novoLimiteCreditoPessoa).build();

        Instant time = Instant.now();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(time.getEpochSecond()).setNanos(time.getNano()).build();

        return LimiteCreditoOutput.newBuilder()
                .setIdPessoa(idPessoa.toString())
                .setLimiteCredito(novoLimite)
                .setDataCalculo(timestamp)
                .build();
    }


}
