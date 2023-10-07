package br.com.itau.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pessoa {

    @JsonProperty("id_pessoa")
    private UUID idPessoa;

    @JsonProperty("score")
    private Long score;

    @JsonProperty("limite_credito")
    private LimiteCredito limiteCredito;

    @JsonProperty("conta_corrente")
    private ContaCorrente contaCorrente;

    @JsonProperty("endividamento")
    private Endividamento endividamento;

    @JsonProperty("data_calculo")
    private Date dataCalculo;

    public static Pessoa createObjectPessoaFromNovoLimite(final UUID idPessoa, final Double novoLimiteCredito) {
        var novoLimite = LimiteCredito.builder().valorTotal(novoLimiteCredito).build();

        return Pessoa.builder()
                .idPessoa(idPessoa)
                .limiteCredito(novoLimite)
                .dataCalculo(new Date())
                .build();
    }
}
