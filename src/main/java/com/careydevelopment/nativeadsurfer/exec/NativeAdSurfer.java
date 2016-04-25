package com.careydevelopment.nativeadsurfer.exec;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.processor.DomainProcessor;
import com.careydevelopment.nativeadsurfer.util.DomainsLoader;

public class NativeAdSurfer {

	private static final Logger LOGGER = LoggerFactory.getLogger(NativeAdSurfer.class);
	
	public NativeAdSurfer() {
	}

	public static void main(String[] args) {
		NativeAdSurfer surfer = new NativeAdSurfer();
		surfer.go();
		System.exit(0);
	}

	private void go() {
		//LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		//StatusPrinter.print(lc);
		  
		LOGGER.info("STARTING...");
		try {
			//WebDriver driver = new FirefoxDriver();
			
			/* FirefoxBinary ffBinary = new FirefoxBinary();
			 FirefoxProfile ffProfile = new FirefoxProfile();
	         ffBinary.setTimeout(TimeUnit.SECONDS.toMillis(180));
	         WebDriver driver = new FirefoxDriver(ffBinary, ffProfile);*/
			
			System.setProperty("webdriver.chrome.driver", "c:/tmp/chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			
			List<String> domains = DomainsLoader.getDomains();
			
			for (String domain : domains) {
				DomainProcessor processor = new DomainProcessor(domain,driver);
				processor.process();
			}
		} catch (NativeAdSurferException ne) {
			LOGGER.error("Problem surfing for native ads!",ne);
		}
		
		LOGGER.info("COMPLETE");
	}
}
