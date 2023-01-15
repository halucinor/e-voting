package com.gabia.evoting.repository;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.domain.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteModel, Long>{
    @Override
    Optional<VoteModel> findById(Long voteId);

//    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select vote from VOTE vote where vote.agenda.id =:agendaId")
    List<VoteModel> findAllByAgendaId(Long agendaId);
}
