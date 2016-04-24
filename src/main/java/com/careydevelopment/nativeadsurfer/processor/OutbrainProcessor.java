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

import com.careydevelopment.nativeadsurfer.entity.NativeAd;

public class OutbrainProcessor extends PublisherProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OutbrainProcessor.class);
	
	public OutbrainProcessor(WebDriver driver,String domainName) {
		this.driver = driver;
		this.domainName = domainName;
		this.publisherName = "Outbrain";
		
        emf = Persistence.createEntityManagerFactory("NativeAdService");
        em = emf.createEntityManager();
	}	
	
	
	protected List<NativeAd> getNativeAds() {
		List<NativeAd> nativeAds = new ArrayList<NativeAd>();
		
		List<WebElement> els = driver.findElements(By.className("ob-dynamic-rec-link"));
        for (WebElement el : els) {
        	String href = el.getAttribute("href");
        	String text = el.getText();
        	String[] parts = text.split("\n");
        	String headline = parts[0];
        	LOGGER.info(headline);
        	NativeAd nativeAd = new NativeAd();
        	nativeAd.setUrl(trimUrl(href));
        	nativeAd.setHeadline(headline);
        	nativeAds.add(nativeAd);
        }
        
        int i=0;
        els = driver.findElements(By.className("ob-rec-image"));
        for (WebElement el : els) {
        	String imageUrl = el.getAttribute("src");
        	NativeAd ad = nativeAds.get(i);
        	LOGGER.info("image url is " + imageUrl);
        	ad.setImageUrl(imageUrl);
        	i++;
        }
        
        return nativeAds;
	}
}
