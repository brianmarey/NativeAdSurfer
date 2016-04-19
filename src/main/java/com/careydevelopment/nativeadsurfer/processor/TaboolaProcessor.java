package com.careydevelopment.nativeadsurfer.processor;

import org.openqa.selenium.WebDriver;

import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;

public class TaboolaProcessor implements NativeAdProcessor {
	
	private WebDriver driver;

	public TaboolaProcessor(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public void process() throws NativeAdSurferException {
		// TODO Auto-generated method stub

	}

}
