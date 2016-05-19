package com.mmt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "observers")
public class Observer {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	@JsonIgnore
	private int id;
	
	@JsonProperty("ip")
	@Column(name="observer_ip",unique=true)
	private String ip;
    
   
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
