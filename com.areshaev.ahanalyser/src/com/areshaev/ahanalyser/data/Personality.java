package com.areshaev.ahanalyser.data;

import javax.persistence.Id;

public class Personality {
	@Id String name;
	private byte[] data;
	
	public Personality() {
	}
	
	public Personality(String name, byte[] data) {
		this.name = name;
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
