package com.datapine.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.datapine.domain.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {
	
	@PersistenceContext
    protected EntityManager entityManager;
	
	@Transactional
	@Override
	public void add(Role role) {
		entityManager.persist(role);		
	}

	@Transactional
	@Override
	public void delete(String email) {	
		Query query = entityManager.createQuery("DELETE from com.datapine.domain.Role r where r.email = :email");
		query.setParameter("email", email);
		query.executeUpdate();
		
	}

}
