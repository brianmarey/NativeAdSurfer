package com.careydevelopment.nativeadsurfer.processor;

import org.openqa.selenium.WebDriver;

import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;

public class ZergProcessor implements NativeAdProcessor {
	
	private WebDriver driver;

	public ZergProcessor(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public void process() throws NativeAdSurferException {
		// TODO Auto-generated method stub

	}

}
