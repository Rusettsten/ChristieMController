package com.benjiarrigo.christiecontroller;

public class ChristieProjector {

	/*
	 * VARIABLES
	 */
	private String projectorName;
	private int projectorAddress;
	private String projectorIp;
	private short projectorPort;
	private short enabledChannel;
	private short shutterChannel;
	private short powerChannel;
	
	
	/*
	 * METHODS - GETTERS
	 */
	public String getProjectorName() {
		return projectorName;
	}
	
	public int getProjectorAddress() {
		return projectorAddress;
	}
	
	public String getProjectorIp() {
		return projectorIp;
	}

	public short getProjectorPort() {
		return projectorPort;
	}

	public short getEnabledChannel() {
		return enabledChannel;
	}

	public short getShutterChannel() {
		return shutterChannel;
	}

	public short getPowerChannel() {
		return powerChannel;
	}
	
	
	/*
	 * METHODS - SETTERS
	 */
	public void setProjectorName(String projectorName) {
		this.projectorName = projectorName;
	}
	
	public void setProjectorAddress(int projectorAddress) {
		this.projectorAddress = projectorAddress;
	}
	
	public void setProjectorIp(String projectorIp) {
		this.projectorIp = projectorIp;
	}

	public void setProjectorPort(short projectorPort) {
		this.projectorPort = projectorPort;
	}

	public void setEnabledChannel(short enabledChannel) {
		this.enabledChannel = enabledChannel;
	}

	public void setShutterChannel(short shutterChannel) {
		this.shutterChannel = shutterChannel;
	}

	public void setPowerChannel(short powerChannel) {
		this.powerChannel = powerChannel;
	}
	
	
	/*
	 * CONSTRUCTOR
	 */
	public ChristieProjector() {
		
	}
}
