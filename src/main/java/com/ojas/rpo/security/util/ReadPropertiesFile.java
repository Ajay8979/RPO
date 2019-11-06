package com.ojas.rpo.security.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ReadPropertiesFile {
	public static EmailEntity readConfig() {
		EmailEntity emailEntity = new EmailEntity();
		System.out.println("setReadConfig");

		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties"));
			emailEntity.setFrom((String) properties.get("setFrom"));
			emailEntity.setPassword((String) properties.get("setPassword"));
			//emailEntity.setCc((String) properties.get("CC"));
			//emailEntity.setBcc((String) properties.get("BCC"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return emailEntity;
	}

	public static HashMap<String, String> getMailAddress() {

		Properties properties = new Properties();
		HashMap<String, String> mailList = new HashMap<String, String>();
		String address[] = { "HR", "Finance", "CEO"};
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties"));
			for (String addr : address) {
				mailList.put(addr, properties.getProperty(addr));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mailList;
	}

}