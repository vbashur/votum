package com.vbashur.votum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.vbashur.votum.domain.Restaurant;

@PreAuthorize("hasRole('ROLE_USER')")
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

	@SuppressWarnings("unchecked")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	Restaurant save(Restaurant restaurant);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	void delete(Long aLong);


}
