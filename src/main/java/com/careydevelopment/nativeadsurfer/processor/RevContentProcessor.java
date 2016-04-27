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

public class RevContentProcessor extends PublisherProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RevContentProcessor.class);
	
	public RevContentProcessor(WebDriver driver,String domainName) {
		this.driver = driver;
		this.domainName = domainName;
		this.publisherName = "RevContent";
		
        emf = Persistence.createEntityManagerFactory("NativeAdService");
        em = emf.createEntityManager();
	}	
	
	
	protected List<NativeAd> getNativeAds() {
		List<NativeAd> nativeAds = new ArrayList<NativeAd>();
		
		List<WebElement> els = driver.findElements(By.className("rc-cta"));
        for (WebElement el : els) {
        	String href = el.getAttribute("data-target");
        	LOGGER.info(href);
        	NativeAd nativeAd = new NativeAd();
        	nativeAd.setUrl(trimUrl(href));
        	nativeAds.add(nativeAd);
        }
        
        int i=0;
        els = driver.findElements(By.className("rc-headline"));
        for (WebElement el : els) {
        	String headline = el.getText();
        	NativeAd ad = nativeAds.get(i);
        	LOGGER.info("headline is " + headline);
        	ad.setHeadline(headline);
        	i++;
        }
        
        i=0;
        els = driver.findElements(By.className("rc-photo"));
        for (WebElement el : els) {
        	String imageStyle = el.getAttribute("style");
        	NativeAd ad = nativeAds.get(i);
        	LOGGER.info("before image is " + imageStyle);
        	
        	if (imageStyle.indexOf("http") > -1) {
        		int start = imageStyle.indexOf("http");
        		int end = imageStyle.indexOf("&",start);
        		
        		if (end > start) {
        			String imageUrl = imageStyle.substring(start, end);
        			LOGGER.info("IMAGE URL IS " + imageUrl);
        			ad.setImageUrl(imageUrl);
        		}
        	}
        	
        	i++;
        }
        
        return nativeAds;
	}
}
