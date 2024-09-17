package br.com.fiap.java_challenge.repository;

import br.com.fiap.java_challenge.entity.PreferenciaViagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenciaViagemRepository extends JpaRepository<PreferenciaViagem, Long> {
}