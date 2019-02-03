package com.poc.h2.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class StudentRepositoryImpl implements ObjectCustomMethods {

	@PersistenceContext
    private EntityManager em;

	@Override
	public List<Student> getData() {
		// TODO Auto-generated method stub
		
		return em.createQuery("select o from Student",Student.class).getResultList();
	}
	
	
	

}
