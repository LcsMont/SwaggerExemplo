package com.sis.swaggerexemplo.repository;

import com.sis.swaggerexemplo.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {
}
