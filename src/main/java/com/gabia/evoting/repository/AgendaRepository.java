package com.gabia.evoting.repository;

import com.gabia.evoting.domain.AgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<AgendaModel, Long> {
    @Override
    Optional<AgendaModel> findById(Long agendaId);

    List<AgendaModel> findAll();
}
