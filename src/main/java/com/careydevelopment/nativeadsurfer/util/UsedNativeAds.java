package com.careydevelopment.nativeadsurfer.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.entity.NativeAd;

public class UsedNativeAds {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsedNativeAds.class);
	
	private static UsedNativeAds INSTANCE;
	
	private List<NativeAd> usedNativeAds = new ArrayList<NativeAd>();
	
	
	private UsedNativeAds() {
		
	}
	
	
	public static UsedNativeAds getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UsedNativeAds();
		}
		
		return INSTANCE;
	}

	
	public boolean isInList(NativeAd ad) {
		boolean inList = false;
		
		for (NativeAd a : usedNativeAds) {
			if (a.getId() == ad.getId()) {
				inList = true;
				break;
			}
		}
		
		return inList;
	}
	
	
	public void addAd(NativeAd ad) {
		usedNativeAds.add(ad);
	}
}
