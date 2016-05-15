package com.careydevelopment.nativeadsurfer.processor;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;

public class DomainProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DomainProcessor.class);
	
	private static final int MAX_LINKS = 5;
	private static final NativeAdCompany[] COMPANIES = {NativeAdCompany.OUTBRAIN,NativeAdCompany.TABOOLA,NativeAdCompany.REVCONTENT};
	
	private String domain;
	private WebDriver driver;
	private String rootUrl;
	
	public DomainProcessor(String domain) {
		this.domain = domain;
		
		driver = new ChromeDriver();
		
		buildRootUrl();
	}

	
	private void buildRootUrl() {
		StringBuilder builder = new StringBuilder();
		builder.append("http://");
		builder.append(domain);
		
		rootUrl = builder.toString();
	}
	
	
	public void process() throws NativeAdSurferException {
		try {
			LOGGER.info("Loading root url " + rootUrl);
			driver.get(rootUrl);
			
			List<String> validLinks = getValidLinks();
			
			for (String link : validLinks) {
				LOGGER.info("Getting URL " + link);
				try {
					driver.get(link);
					processAllNativeAds();
				} catch (Exception e) {
					LOGGER.error("Problem getting link " + link,e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {		
			driver.close();
		}
	}
	
	
	private void processAllNativeAds() throws NativeAdSurferException {
		for (NativeAdCompany company : COMPANIES) {
			PublisherProcessor processor = NativeAdProcesscorFactory.getNativeAdProcessor(company,driver,domain);
			processor.process();
		}
	}
	
	
	private List<String> getValidLinks() {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		LOGGER.info("There are " + links.size() + " links on the root page");
		
		
		List<String> validLinks = new ArrayList<String>();
		for (WebElement link : links) {
			try {
				String href = link.getAttribute("href");
				if (href != null) {
					if (href.indexOf(domain) > -1 && href.indexOf("category") == -1 && href.length() > domain.length() + 50) {
						if (!validLinks.contains(href)) {
							if (href.indexOf("jpg") == -1 && href.indexOf(".gif") == -1 && href.indexOf(".png") == -1) {
								validLinks.add(href);								
							}
						}
						if (validLinks.size() == MAX_LINKS) break;
					}
				}
			} catch (StaleElementReferenceException se) {
				LOGGER.warn("Problem retrieving native link element for " + domain);
			}
		}
		
		return validLinks;
	}
}
