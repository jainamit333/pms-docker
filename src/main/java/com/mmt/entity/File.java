package com.mmt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmt.adminui.util.NodeIcon;

@Entity
public class File extends FileSystemObject {
	
	public File(){
		this.canHaveChild=false;
		this.icon=NodeIcon.file;
		this.type=NodeIcon.file;
	}
	@OneToMany(mappedBy="fileID",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private List<Property> properties;

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
}
