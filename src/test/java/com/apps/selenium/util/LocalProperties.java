/**
 * 
 */
package com.apps.selenium.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author oliver
 *
 */
public class LocalProperties {

	java.util.Properties properties = new java.util.Properties();
	InputStream input = null;

	public LocalProperties() throws IOException {
		input = new FileInputStream("src/main/resources/local.properties");
		properties.load(input);
	}

	public String getValue(String localProperty) {
		return properties.getProperty(localProperty);
	}

}
