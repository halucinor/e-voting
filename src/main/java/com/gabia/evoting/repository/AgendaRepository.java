package com.gabia.evoting.repository;

import com.gabia.evoting.domain.AgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<AgendaModel, Long> {
    @Override
//    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
//    @Query("select agenda from AGENDA agenda where agenda.id = :agendaId")
    Optional<AgendaModel> findById(Long agendaId);

    List<AgendaModel> findAll();

}
