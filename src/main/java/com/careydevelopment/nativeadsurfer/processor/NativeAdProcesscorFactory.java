package com.careydevelopment.nativeadsurfer.processor;

import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;

public class NativeAdProcesscorFactory {

	public NativeAdProcessor getNativeAdProcessor(NativeAdCompany reference) throws NativeAdSurferException {
		switch (reference) {
		case OUTBRAIN:
			return new OutbrainProcessor();
		case TABOOLA:
			return new TaboolaProcessor();
		case ZERG:
			return new ZergProcessor();
		default:
			throw new NativeAdSurferException("Unknown native ad reference! " + reference);
		}
	}
}
