package com.gabia.evoting.repository;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.domain.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteModel, Long>{
    @Override
    Optional<VoteModel> findById(Long voteId);

}
