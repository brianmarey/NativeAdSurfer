package com.careydevelopment.nativeadsurfer.processor;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.entity.AdCompany;
import com.careydevelopment.nativeadsurfer.entity.Domain;
import com.careydevelopment.nativeadsurfer.entity.DomainAd;
import com.careydevelopment.nativeadsurfer.entity.NativeAd;
import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;
import com.careydevelopment.nativeadsurfer.util.UsedNativeAds;

public abstract class PublisherProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PublisherProcessor.class);
	
	protected EntityManager em;
	protected EntityManagerFactory emf;
	protected WebDriver driver;
	protected String domainName;
	protected String publisherName;
	
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
		em.getTransaction().begin();
		
		try {
			persistNativeAdIfNecessary(ad);
			updateDomainAd(ad,domain);	
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}
	
	
	private void updateDomainAd(NativeAd ad, Domain domain) {
		Query query = em.createQuery("select da from DomainAd da where da.domain.name = :domainName and da.nativeAd.url = :url");
		query.setParameter("domainName", domain.getName());
		query.setParameter("url", ad.getUrl());
		
		try {
			DomainAd domainAd = (DomainAd)query.getSingleResult();
			updateLastSeenDate(domainAd);
		} catch (NoResultException nr) {
			LOGGER.info("No domain ad for domain " + domain.getName() + " and url " + ad.getUrl() + " adding it");
			persistDomainAd(ad,domain);
		}
	}

	private void updateLastSeenDate(DomainAd domainAd) {
		domainAd.setLastSeen(new Date());
	}
	
	private void persistNativeAdIfNecessary(NativeAd ad) {
		Query query = em.createQuery("select n from NativeAd n where n.adCompany.name = :companyName and n.url = :url and n.headline = :headline and n.imageUrl = :imageUrl");
		query.setParameter("companyName", ad.getAdCompany().getName());
		query.setParameter("url", ad.getUrl());
		query.setParameter("headline", ad.getHeadline());
		query.setParameter("imageUrl", ad.getImageUrl());
		
		
		try {
			NativeAd nativeAd = (NativeAd)query.getSingleResult();
			
			if (!UsedNativeAds.getInstance().isInList(nativeAd)) {
				LOGGER.info("Haven't seen this ad yet: " + nativeAd.getHeadline() + " " + nativeAd.getId() + " " + nativeAd.getUrl() + " " + nativeAd.getImageUrl());
				nativeAd.setDaysSeen(nativeAd.getDaysSeen() + 1);
				UsedNativeAds.getInstance().addAd(nativeAd);
				em.merge(nativeAd);
			}
		} catch (NoResultException nr) {
			LOGGER.info("No native ad for  " + ad.getHeadline() + " and url " + ad.getUrl() + " adding it");
			persistNativeAd(ad);
			LOGGER.info("Now ad id is " + ad.getId());
		}
	}
	
	
	private void persistNativeAd(NativeAd ad) {
		LOGGER.info("Persisting ad " + ad.getHeadline());
		ad.setDaysSeen(1);
		em.persist(ad);
		UsedNativeAds.getInstance().addAd(ad);
	}
	
	
	private Domain persistDomainAd(NativeAd ad, Domain domain) {
		if (ad.getId() != null) {
			LOGGER.info("domain is " + domain.getId());
			DomainAd domainAd = new DomainAd();
			domainAd.setDomain(domain);
			domainAd.setNativeAd(ad);
			domainAd.setFirstSeen(new Date());
			domainAd.setLastSeen(new Date());
			
			LOGGER.info("Native Ad is " + ad.getId());
	
			em.persist(domainAd);
		}
		
		return domain;
	}
	
	public void process() throws NativeAdSurferException {
		LOGGER.info("Checking for " + publisherName + " elements");
		
		List<NativeAd> nativeAds = getNativeAds();
		LOGGER.info("Native ads size is " + nativeAds.size());
        if (nativeAds.size() > 0) persistNativeAds(nativeAds);
        if (em.isOpen()) em.close();
        if (emf.isOpen()) emf.close();
	}
	

	private void persistNativeAds(List<NativeAd> nativeAds) {
        
        try {
	        AdCompany company = fetchAdCompany(publisherName);
	        Domain domain = fetchDomain(domainName);

	        for (NativeAd ad : nativeAds) {
	        	//ignore ads that aren't really native ads
	        	if (processWorthy(ad)) {
		        	LOGGER.info("Looking at " + ad.getHeadline());
		        	ad.setAdCompany(company);
		        	persistAd(ad,domain);
	        	}
	        }
	        
	        
	        LOGGER.info("done");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        em.close();
	}
	
	
	private boolean processWorthy(NativeAd ad) {
		boolean processWorthy = false;
		
		String url = ad.getUrl();
		String imageUrl = ad.getImageUrl();
		String headline = ad.getHeadline();
		if (url != null && url.length() > 2 && url.indexOf(domainName) == -1) {
			if (imageUrl != null && imageUrl.length() > 2) {
				if (headline != null && headline.length() > 2) {
					processWorthy = true;
				}
			}
		}
		
		return processWorthy;
	}
	
	
	//gets rid of native ad request parameters 
	//advertisers don't get charged when user clicks on link
	protected String trimUrl(String href) {
		if (href != null) {
			int questionMark = href.indexOf("?");
			if (questionMark > -1) {
				href = href.substring(0,questionMark);
			}
		}
		
		return href;
	}
	
	
	protected abstract List<NativeAd> getNativeAds();

}
