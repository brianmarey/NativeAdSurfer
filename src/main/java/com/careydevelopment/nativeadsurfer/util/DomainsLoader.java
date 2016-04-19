package com.careydevelopment.nativeadsurfer.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.nativeadsurfer.exec.NativeAdSurferException;


public class DomainsLoader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DomainsLoader.class);
	
	private static final String DOMAINS_FILE = "/etc/tomcat8/resources/nativeaddomains.properties";

	public static List<String> getDomains() throws NativeAdSurferException {
		List<String> list = new ArrayList<String>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(DOMAINS_FILE))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        list.add(line);
		        LOGGER.info("just added " + line);
		    }
		} catch (IOException ie) {
			throw new NativeAdSurferException("Can't find properties file!", ie);
		}
		
		return list;
	}

}
