package com.vbashur.votum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.vbashur.votum.domain.Meal;

@PreAuthorize("hasRole('ROLE_USER')")
public interface MealRepository extends CrudRepository<Meal, Long> {

}
