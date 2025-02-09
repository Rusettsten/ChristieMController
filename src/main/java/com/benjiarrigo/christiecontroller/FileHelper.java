package com.benjiarrigo.christiecontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class FileHelper {

	/*
	 * VARIABLES
	 */
	private static Logger logger = LogManager.getLogger();
	
	
	/*
	 * METHODS
	 */
	public static void createPropertiesFolder() {
		try {
			String folderPath = new File(".").getAbsolutePath() + File.separator + "ChristieController";
			Files.createDirectories(Paths.get(folderPath));
		} catch (IOException ex) {
			logger.fatal("Could not access application data directory!", ex);
		}
	}
	
	public static UserProperties loadPropertiesFile() {
		//make sure the user folder is created
		createPropertiesFolder();
		
		//find the file and process or pull template as needed
		final File propertiesFile;
		InputStream yamlStream;
		try {
			String propertiesLocation = new File(".").getAbsolutePath() + File.separator + "ChristieController" + File.separator + "properties.yml";
			propertiesFile = new File(propertiesLocation);
			
			if(!propertiesFile.exists()) {
				URL templateUrl = Main.class.getResource("/properties.yml");
				FileUtils.copyURLToFile(templateUrl, propertiesFile);
				logger.fatal("Properties file does not exist! Please fill it out and re-launch.");
				System.exit(0);
			}
			
			LoaderOptions loaderConfig = new LoaderOptions();
			Yaml yaml = new Yaml(new Constructor(UserProperties.class, loaderConfig));
			yamlStream = new FileInputStream(propertiesFile);
			UserProperties currentProperties = yaml.load(yamlStream);
			logger.info("Successfully loaded properties file from " + propertiesLocation);
			
			return currentProperties;
			
		} catch(IOException ex) {
			logger.fatal("Could not load properties file!", ex);
			System.exit(0);
		}
		
		return null;
	}
	
}
