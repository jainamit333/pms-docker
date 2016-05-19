package com.mmt.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "subscription", indexes = {
		@Index(name = "UNIQUE_QUALIFIER", unique = true, columnList = "observer_id,qualifier") })
public class Subscription {
	@JsonIgnore
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@JsonProperty("observer")
	@OneToOne
	@JoinColumn(name = "observer_id")
	private Observer observer;
	
	@JsonIgnore
	@Column(name = "prefix")
	private String prefix;
	
	@JsonIgnore
	@Column(name = "suffix")
	private String suffix;
	
	@JsonProperty("qualifier")
	@Column(name = "qualifier",nullable=false)
	private String subsQualifier;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	private List<File> files;


	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubsQualifier() {
		return subsQualifier;
	}

	public void setSubsQualifier(String subsQualifier) {
		this.subsQualifier = subsQualifier;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public Observer getObserver() {
		return observer;
	}

	public void setObserver(Observer observer) {
		this.observer = observer;
	}

}
