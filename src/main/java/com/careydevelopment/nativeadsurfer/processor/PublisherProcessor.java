package com.careydevelopment.nativeadsurfer.processor;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.entity.AdCompany;
import com.careydevelopment.nativeadsurfer.entity.Domain;

public abstract class PublisherProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PublisherProcessor.class);
	
	protected EntityManager em;
	
	
	protected AdCompany persistAdCompany(String companyName) {
		AdCompany adCompany = new AdCompany();
		adCompany.setName(companyName);

		em.persist(adCompany);
		
		return adCompany;
	}

	
	protected AdCompany fetchAdCompany(String companyName) {
		Query query = em.createQuery("select a from AdCompany a where a.name = :name");
		query.setParameter("name", companyName);
		
		AdCompany adCompany = null;
		try {
			adCompany = (AdCompany)query.getSingleResult();
		} catch (NoResultException nr) {
			LOGGER.info("No ad company name " + companyName + " adding new one");
			adCompany = persistAdCompany(companyName);
		}
		
		return adCompany;
	}
	
	
	protected Domain persistDomain(String domainName) {
		Domain domain = new Domain();
		domain.setName(domainName);

		em.persist(domain);
		
		return domain;
	}

	
	protected Domain fetchDomain(String domainName) {
		Query query = em.createQuery("select d from Domain d where d.name = :name");
		query.setParameter("name", domainName);
		
		Domain domain = null;
		try {
			domain = (Domain)query.getSingleResult();
		} catch (NoResultException nr) {
			LOGGER.info("No domain name " + domainName + " adding new one");
			domain = persistDomain(domainName);
		}
		
		return domain;
	}
}
