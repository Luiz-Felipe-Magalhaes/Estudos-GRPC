package br.com.itau;

import io.quarkus.grpc.GrpcClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/obtemlimite")
public class LimiteCreditoClientGrpc {

    @GrpcClient("credito")
    LimiteCreditoGrpc.LimiteCreditoBlockingStub limiteCreditoBlockingStub;

    @GET
    @Path("/credito")
    public Double limiteCredito(LimiteCreditoInput limiteCreditoInput) {
        return limiteCreditoBlockingStub.getLimiteCredito(LimiteCreditoInput.newBuilder()
                .setLimiteCredito()
                .setContaCorrente()
                .setEndividamento()
                .build()).getLimiteCredito();
    }
}
