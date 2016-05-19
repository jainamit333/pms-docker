package com.mmt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "variant")
public class Variant {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "file_id")
	@JsonIgnore
	private File fileID;
	
    @ManyToMany(fetch = FetchType.LAZY)
	private List<Observer> observers;
    
    @OneToMany(mappedBy="variantID",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Property> properties;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public File getFileID() {
		return fileID;
	}

	public void setFileID(File fileID) {
		this.fileID = fileID;
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	

}
