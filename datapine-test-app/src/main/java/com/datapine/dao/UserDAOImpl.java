package com.datapine.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.datapine.domain.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@PersistenceContext
    protected EntityManager entityManager;
	
	public UserDAOImpl() {
		
	}

	@Transactional
	@Override
	public void save(User user) {		
		entityManager.persist(user);	
	}

	@Transactional
	@Override	
	public User update(User user) {		
		return entityManager.merge(user);		
	}

	@Transactional
	@Override
	public void delete(User user) {
		delete(user.getId());			
	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		Query query = entityManager.createQuery("DELETE from com.datapine.domain.User u where u.id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}


	@Transactional(readOnly = true)
	@Override	
	public User findById(Long id) {
		TypedQuery<User> query = entityManager.createQuery("Select u from com.datapine.domain.User u where u.id = :id", User.class);
		query.setParameter("id", id);
		User res = query.getSingleResult();		
		return res;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByEmail(String email) {
		TypedQuery<User> query = entityManager.createQuery("Select u from com.datapine.domain.User u where u.email = :email", User.class);
		query.setParameter("email", email);
		User res = query.getSingleResult();		
		return res;
	}

	@Transactional(readOnly = true)
	@Override
	public Iterator<User> findAllOrderById() {
		TypedQuery<User> query = entityManager.createQuery("Select u from com.datapine.domain.User u ORDER BY u.id ASC", User.class);
		List<User> resList = query.getResultList();
		return resList.iterator();
	}

}