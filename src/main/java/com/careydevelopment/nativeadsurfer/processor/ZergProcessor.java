package com.careydevelopment.nativeadsurfer.processor;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;

public class ZergProcessor implements NativeAdProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZergProcessor.class);
	
	private WebDriver driver;
	private String domain;

	public ZergProcessor(WebDriver driver,String domain) {
		this.driver = driver;
		this.domain = domain;
	}

	@Override
	public void process() throws NativeAdSurferException {
		LOGGER.info("Checking for Zerg elements");
		
		List<WebElement> els = driver.findElements(By.className("zerglayoutcl"));
        for (WebElement el : els) {
        	LOGGER.info(el.getText());
        }
	}

}
