package com.careydevelopment.nativeadsurfer.exec;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.processor.DomainProcessor;
import com.careydevelopment.nativeadsurfer.util.DomainsLoader;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class NativeAdSurfer {

	private static final Logger LOGGER = LoggerFactory.getLogger(NativeAdSurfer.class);
	
	public NativeAdSurfer() {
	}

	public static void main(String[] args) {
		NativeAdSurfer surfer = new NativeAdSurfer();
		surfer.go();
	}

	private void go() {
		  LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		  // print logback's internal status
		  StatusPrinter.print(lc);
		  
		LOGGER.info("STARTING...");
		try {
			WebDriver driver = new FirefoxDriver();
			List<String> domains = DomainsLoader.getDomains();
			
			for (String domain : domains) {
				DomainProcessor processor = new DomainProcessor(domain,driver);
				processor.process();
			}
		} catch (NativeAdSurferException ne) {
			LOGGER.error("Problem surfing for native ads!",ne);
		}
	}
}
