package com.vbashur.votum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import com.vbashur.votum.domain.Voting;

@PreAuthorize("hasRole('ROLE_USER')")
public interface VotingRepository extends CrudRepository<Voting, Long>  {
	
	public Voting findByUser(@Param("user") String user);

}
