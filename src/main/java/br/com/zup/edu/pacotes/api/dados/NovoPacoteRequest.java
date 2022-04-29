package br.com.zup.edu.pacotes.api.dados;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;

public class NovoPacoteRequest {

    @NotBlank
    private String nome;

    @NotNull
    @CPF
    private String cpf;

    @NotNull
    @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}")
    private String celular;

    @NotNull
    @Min(5)
    @Max(50)
    private Integer quantidadeDados;

    public NovoPacoteRequest(String nome, String cpf, String celular, Integer quantidadeDados) {
        this.nome = nome;
        this.cpf = cpf;
        this.celular = celular;
        this.quantidadeDados = quantidadeDados;
    }

    public NovoPacoteRequest() {
    }

    public Pacote toModel() {
        return new Pacote(nome,cpf,celular,quantidadeDados);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCelular() {
        return celular;
    }

    public Integer getQuantidadeDados() {
        return quantidadeDados;
    }
}
