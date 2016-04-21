package com.careydevelopment.nativeadsurfer.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityManagerTest {

	public EntityManagerTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NativeAdService");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        try {
        Query query = em.createQuery ("SELECT distinct(da.nativeAd) FROM DomainAd da order by da.lastSeen desc");
        //Query query = em.createQuery ("SELECT na FROM NativeAd na");
        query.setMaxResults(12);
        List ads = query.getResultList();
        
        System.err.println("done");
        } catch (Exception e) {
        	e.printStackTrace();
        	em.getTransaction().rollback();
        }
        
        em.close();
        emf.close();
	}

}
