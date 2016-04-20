package com.careydevelopment.nativeadsurfer.processor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.entity.AdCompany;
import com.careydevelopment.nativeadsurfer.entity.Domain;
import com.careydevelopment.nativeadsurfer.entity.DomainAd;
import com.careydevelopment.nativeadsurfer.entity.NativeAd;

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
	
	
	protected void persistAd(NativeAd ad, Domain domain) {
		Query query = em.createQuery("select da from DomainAd da where da.domain.name = :domainName and da.nativeAd.url = :url");
		query.setParameter("domainName", domain.getName());
		query.setParameter("url", ad.getUrl());
		
		try {
			DomainAd domainAd = (DomainAd)query.getSingleResult();
			LOGGER.info("\n\n\nI GOT IT!!");
		} catch (NoResultException nr) {
			LOGGER.info("No domain ad for domain " + domain.getName() + " and url " + ad.getUrl() + " adding it");
			persistDomainAd(ad,domain);
		}
	}
	
	
	protected Domain persistDomainAd(NativeAd ad, Domain domain) {
		DomainAd domainAd = new DomainAd();
		domainAd.setDomain(domain);
		domainAd.setNativeAd(ad);

		em.persist(domainAd);
		
		return domain;
	}
}
