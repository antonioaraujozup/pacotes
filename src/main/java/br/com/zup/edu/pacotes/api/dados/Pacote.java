package br.com.zup.edu.pacotes.api.dados;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pacote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Embedded
    private CPF cpf;

    @Column(nullable = false, length = 14)
    private String celular;

    @Column(nullable = false)
    private Integer quantidadeDados;

    @Column(nullable = false)
    private LocalDate cadastradoEm = LocalDate.now();

    public Pacote(String nome, String cpf, String celular, Integer quantidadeDados) {
        this.nome = nome;
        this.cpf = new CPF(cpf);
        this.celular = celular;
        this.quantidadeDados = quantidadeDados;
    }

    /**
     * @deprecated Construtor para uso exclusivo do Hibernate.
     */
    @Deprecated
    public Pacote() {
    }

    public Long getId() {
        return id;
    }

    public CPF getCpf() {
        return cpf;
    }
}
