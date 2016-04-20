package com.careydevelopment.nativeadsurfer.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerTest {

	public EntityManagerTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NativeAdService");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        try {
        AdCompany company = new AdCompany();
        company.setName("Taboola");
        em.persist(company);
        em.getTransaction().commit();
        
        System.err.println("done");
        } catch (Exception e) {
        	e.printStackTrace();
        	em.getTransaction().rollback();
        }
        
        em.close();
        emf.close();
	}

}
