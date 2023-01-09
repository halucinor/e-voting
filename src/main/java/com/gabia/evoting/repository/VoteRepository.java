package com.gabia.evoting.repository;

import com.gabia.evoting.domain.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<VoteModel, Long>{

}
