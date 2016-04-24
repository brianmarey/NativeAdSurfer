package com.careydevelopment.nativeadsurfer.processor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.entity.AdCompany;
import com.careydevelopment.nativeadsurfer.entity.Domain;
import com.careydevelopment.nativeadsurfer.entity.NativeAd;
import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;

public class TaboolaProcessor extends PublisherProcessor implements NativeAdProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaboolaProcessor.class);
	
	public TaboolaProcessor(WebDriver driver,String domain) {
		this.driver = driver;
		this.domainName = domain;
		this.publisherName = "Taboola";
		
        emf = Persistence.createEntityManagerFactory("NativeAdService");
        em = emf.createEntityManager();
	}
	
	protected List<NativeAd> getNativeAds() {
		List<NativeAd> nativeAds = new ArrayList<NativeAd>();
		
		List<WebElement> els = driver.findElements(By.className("item-thumbnail-href"));
        for (WebElement el : els) {
        	String href = el.getAttribute("href");
        	LOGGER.info(href);
        	NativeAd nativeAd = new NativeAd();
        	nativeAd.setUrl(trimUrl(href));
        	nativeAds.add(nativeAd);
        }
        
        int i=0;
        els = driver.findElements(By.className("thumbnail_top"));
        for (WebElement el : els) {
        	String imageUrl = el.getAttribute("data-item-thumb");
        	String headline = el.getAttribute("data-item-title");
        	NativeAd ad = nativeAds.get(i);
        	ad.setHeadline(headline);
        	ad.setImageUrl(imageUrl);
        	i++;
        }
        
        return nativeAds;
	}
}
