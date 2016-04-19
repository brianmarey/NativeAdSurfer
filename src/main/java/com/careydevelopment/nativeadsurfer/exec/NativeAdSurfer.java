package com.careydevelopment.nativeadsurfer.exec;

import java.util.List;

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
	}

	private void go() {
		try {
			List<String> domains = DomainsLoader.getDomains();
			
			for (String domain : domains) {
				DomainProcessor processor = new DomainProcessor(domain);
				processor.process();
			}
		} catch (NativeAdSurferException ne) {
			LOGGER.error("Problem surfing for native ads!",ne);
		}
	}
}
