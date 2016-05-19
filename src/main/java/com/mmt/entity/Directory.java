package com.mmt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmt.adminui.util.NodeIcon;

@Entity  
public class Directory extends FileSystemObject {
	
	public Directory(){
		this.canHaveChild=true;
		this.icon=NodeIcon.folder;
		this.type=NodeIcon.folder;
	}
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "parentDir", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<FileSystemObject> subObjects;

	public List<FileSystemObject> getSubObjects() {
		return subObjects;
	}

	public void setSubObjects(List<FileSystemObject> subdirs) {
		this.subObjects = subdirs;
	}
    

}
