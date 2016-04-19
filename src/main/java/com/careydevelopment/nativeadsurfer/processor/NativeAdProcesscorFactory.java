package com.careydevelopment.nativeadsurfer.processor;

import org.openqa.selenium.WebDriver;

import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;

public class NativeAdProcesscorFactory {

	public static NativeAdProcessor getNativeAdProcessor(NativeAdCompany reference, WebDriver driver) throws NativeAdSurferException {
		switch (reference) {
		case OUTBRAIN:
			return new OutbrainProcessor(driver);
		case TABOOLA:
			return new TaboolaProcessor(driver);
		case ZERG:
			return new ZergProcessor(driver);
		default:
			throw new NativeAdSurferException("Unknown native ad reference! " + reference);
		}
	}
}
