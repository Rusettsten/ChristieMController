package com.benjiarrigo.christiecontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProjectorController {
	
	/*
	 * VARIABLES
	 */
	private static Logger logger = LogManager.getLogger();
	
	private ChristieProjector projector;
	private boolean enabledRequired;
	private boolean shutterEnabled;
	private boolean powerEnabled;
	private Boolean enabled;
	private Boolean shuttered;
	private Boolean powered;
	
	
	/*
	 * METHODS - GETTERS
	 */
	public ChristieProjector getProjector() {
		return projector;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isShuttered() {
		return shuttered;
	}
	
	public boolean isPowered() {
		return powered;
	}
	
	
	/*
	 * METHODS - RUNNING
	 */
	public void processData(byte[] data) {
		if(enabledRequired) {
			short enabledData = (short) (data[projector.getEnabledChannel()-1] & 0xFF);
			if(enabledData != 255) {
				return;
			}
		}
		
		if(shutterEnabled) {
			short shutteredData = (short) (data[projector.getShutterChannel()-1] & 0xFF);
			
			if(shuttered == null) {
				if(shutteredData >= 128) {
					sendCommand("(SHU 1)");
					shuttered = true;
				} else {
					sendCommand("(SHU 0)");
					shuttered = false;
				}
			} else if(shuttered) {
				if(shutteredData < 128) {
					sendCommand("(SHU 0)");
					shuttered = false;
				}
			} else if(!shuttered) {
				if(shutteredData >= 128) {
					sendCommand("(SHU 1)");
					shuttered = true;
				}
			}
		}
		
		if(powerEnabled) {
			short poweredData = (short) (data[projector.getPowerChannel()-1] & 0xFF);
			
			if(powered == null) {
				if(poweredData >= 128) {
					sendCommand("(PWR 1)");
					powered = true;
				} else {
					sendCommand("(PWR 0)");
					powered = false;
				}
			} else if(powered) {
				if(poweredData < 128) {
					sendCommand("(PWR 0)");
					powered = false;
				}
			} else if(!powered) {
				if(poweredData >= 128) {
					sendCommand("(PWR 1)");
					powered = true;
				}
			}
		}
	}
	
	public void sendCommand(String command) {
		try {
			Socket skt = new Socket(projector.getProjectorIp(), projector.getProjectorPort());
			PrintWriter dataOut = new PrintWriter(skt.getOutputStream(),true);
			dataOut.print(command);
			dataOut.flush();
			dataOut.close();
			skt.close();
			logger.info("Sent command: \"" + command + "\" to " + projector.getProjectorName());
		} catch (IOException ex) {
			logger.error("Error when sending command: \"" + command + "\" to " + projector.getProjectorName(), ex);
		}
		
	}
	
	
	/*
	 * CONSTRUCTOR
	 */
	public ProjectorController(ChristieProjector projector) {
		this.projector = projector;
		
		// See if any channels need to be disabled by notating a '0' in the configuration.
		if(projector.getEnabledChannel() == 0) {
			enabledRequired = false;
		} else {
			enabledRequired = true;
		}
		
		if(projector.getShutterChannel() == 0) {
			shutterEnabled = false;
		} else {
			shutterEnabled = true;
		}
		
		if(projector.getPowerChannel() == 0) {
			powerEnabled = false;
		} else {
			powerEnabled = true;
		}
	}
	
}
