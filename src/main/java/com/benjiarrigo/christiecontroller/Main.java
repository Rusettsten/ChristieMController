package com.benjiarrigo.christiecontroller;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bildspur.artnet.ArtNetClient;

public class Main {

	/*
	 * VARIABLES
	 */
	private static Logger logger = LogManager.getLogger();
	public static UserProperties properties;
	
	public static ArrayList<ProjectorController> projectorControllers = new ArrayList<ProjectorController>();
	
	/*
	 * METHODS - MAIN
	 */
	public static void main(String[] args) {
		
		//logger.info(" ██████ ██   ██ ██████  ██ ███████ ████████ ██ ███████");
		//logger.info("██      ██   ██ ██   ██ ██ ██         ██    ██ ██     ");
		//logger.info("██      ███████ ██████  ██ ███████    ██    ██ █████  ");
		//logger.info("██      ██   ██ ██   ██ ██      ██    ██    ██ ██     ");
		//logger.info(" ██████ ██   ██ ██   ██ ██ ███████    ██    ██ ███████");
		//logger.info("                                                       ");
		//logger.info("                                                       ");
		//logger.info(" ██████  ██████  ███    ██ ████████ ██████   ██████  ██      ██      ███████ ██████ ");
		//logger.info("██      ██    ██ ████   ██    ██    ██   ██ ██    ██ ██      ██      ██      ██   ██");
		//logger.info("██      ██    ██ ██ ██  ██    ██    ██████  ██    ██ ██      ██      █████   ██████ ");
		//logger.info("██      ██    ██ ██  ██ ██    ██    ██   ██ ██    ██ ██      ██      ██      ██   ██");
		//logger.info(" ██████  ██████  ██   ████    ██    ██   ██  ██████  ███████ ███████ ███████ ██   ██");
		//logger.info("                                                       ");
		logger.info("Christie M Controller 1.0.2. Compiled 2/7/2025 for the Odyssey at ART.");
		logger.info("Created by Benji Arrigo. Designed for Christie M-Series Projectors.");
		
		//get user properties
		properties = FileHelper.loadPropertiesFile();
		
		//create projector controllers
		Iterator<ChristieProjector> pjIter = properties.getProjectors().iterator();
		while(pjIter.hasNext()) {
			ChristieProjector workingPj = pjIter.next();
			ProjectorController workingController = new ProjectorController(workingPj);
			projectorControllers.add(workingController);
			logger.info("Registered Christie Projector: "
					+ "\nName: " + workingPj.getProjectorName() 
					+ "\nIP: " + workingPj.getProjectorIp()
					+ "\nPort: " + workingPj.getProjectorPort()
					+ "\nEnable Channel Addr: " + workingPj.getEnabledChannel()
					+ "\nShutter Channel Addr: " + workingPj.getShutterChannel()
					+ "\nPower Channel Addr: " + workingPj.getPowerChannel());
		}
		
		//start Art-Net reception
		ArtNetClient artnet = new ArtNetClient();
		
		if(properties.getNetworkInterface() != null) {
			try {
				NetworkInterface ni = NetworkInterface.getByName(properties.getNetworkInterface());
				InetAddress address = ni.getInetAddresses().nextElement();
				artnet.start(address);
			} catch (SocketException ex) {
				logger.error("Error when binding to network interface: " + properties.getNetworkInterface(), ex);
				System.exit(0);
			} catch (NullPointerException ex) {
				logger.error("Error when binding to network interface. Couldn't find it! See list below.");
				try {
					logger.error("---> BEGIN LIST");
					Iterator<NetworkInterface> niIter = NetworkInterface.getNetworkInterfaces().asIterator();
					while(niIter.hasNext()) {
						NetworkInterface workingInter = niIter.next();
						logger.info(workingInter.getName() + " <-> " + workingInter.getDisplayName());
					}
					logger.error("---> END LIST");
					
				} catch (SocketException ex2) {
					logger.error("Error when trying to get list of network interfaces.", ex2);
				}
				System.exit(0);
			}
			
		} else {
			artnet.start();
		}
		
		//loop
		while(true) {
			byte[] data = artnet.readDmxData(properties.getDmxSubnet(), properties.getDmxUniverse());
			projectorControllers.forEach(pc -> pc.processData(data));
		}
		
	}

}
