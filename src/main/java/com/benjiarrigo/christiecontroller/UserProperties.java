package com.benjiarrigo.christiecontroller;

import java.util.ArrayList;

public class UserProperties {

	/*
	 * VARIABLES
	 */
	private String networkInterface;
	private short dmxSubnet;
	private short dmxUniverse;
	private ArrayList<ChristieProjector> projectors;
	
	/*
	 * METHODS - GETTERS
	 */
	public String getNetworkInterface() {
		return networkInterface;
	}
	
	public short getDmxSubnet() {
		return dmxSubnet;
	}
	
	public short getDmxUniverse() {
		return dmxUniverse;
	}
	
	public ArrayList<ChristieProjector> getProjectors() {
		return projectors;
	}
	
	
	/*
	 * METHODS - SETTERS
	 */
	public void setNetworkInterface(String networkInterface) {
		this.networkInterface = networkInterface;
	}
	
	public void setDmxSubnet(short dmxSubnet) {
		this.dmxSubnet = dmxSubnet;
	}
	
	public void setDmxUniverse(short dmxUniverse) {
		this.dmxUniverse = dmxUniverse;
	}
	
	public void setProjectors(ArrayList<ChristieProjector> projectors) {
		this.projectors = projectors;
	}
	
	
	/*
	 * CONSTRUCTOR
	 */
	public UserProperties() {
		
	}
}
