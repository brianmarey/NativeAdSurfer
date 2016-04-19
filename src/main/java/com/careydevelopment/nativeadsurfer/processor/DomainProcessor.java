package com.careydevelopment.nativeadsurfer.processor;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;

public class DomainProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DomainProcessor.class);
	
	private static final int MAX_LINKS = 5;
	private static final NativeAdCompany[] COMPANIES = {NativeAdCompany.OUTBRAIN,NativeAdCompany.TABOOLA,NativeAdCompany.ZERG};
	
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
		LOGGER.info("Loading root url " + rootUrl);
		driver.get(rootUrl);
		
		List<String> validLinks = getValidLinks();
		
		for (String link : validLinks) {
			LOGGER.info("Getting URL " + link);
			driver.get(link);
			processAllNativeAds();
		}
	}
	
	
	private void processAllNativeAds() throws NativeAdSurferException {
		for (NativeAdCompany company : COMPANIES) {
			NativeAdProcessor processor = NativeAdProcesscorFactory.getNativeAdProcessor(company,driver);
			processor.process();
		}
	}
	
	
	private List<String> getValidLinks() {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		LOGGER.info("There are " + links.size() + " links on the root page");
		
		
		List<String> validLinks = new ArrayList<String>();
		for (WebElement link : links) {
			String href = link.getAttribute("href");
			if (href.indexOf(domain) > -1) {
				LOGGER.info("Adding link " + href);
				validLinks.add(href);
				if (validLinks.size() == MAX_LINKS) break;
			}
		}
		
		return validLinks;
	}
}
