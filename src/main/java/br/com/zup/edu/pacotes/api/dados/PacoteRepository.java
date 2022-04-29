package br.com.zup.edu.pacotes.api.dados;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacoteRepository extends JpaRepository<Pacote,Long> {
    boolean existsByCpfHash(String hash);
}
