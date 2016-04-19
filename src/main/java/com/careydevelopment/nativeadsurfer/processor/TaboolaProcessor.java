package com.careydevelopment.nativeadsurfer.processor;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;

public class TaboolaProcessor implements NativeAdProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaboolaProcessor.class);
	
	private WebDriver driver;

	public TaboolaProcessor(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public void process() throws NativeAdSurferException {
		LOGGER.info("Checking for Taboola elements");
		
		List<WebElement> els = driver.findElements(By.className("trc_rbox_outer"));
        for (WebElement el : els) {
        	LOGGER.info(el.getText());
        }
	}

}
