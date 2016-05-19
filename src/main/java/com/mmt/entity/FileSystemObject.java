package com.mmt.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmt.adminui.util.NodeIcon;

@Entity
@Table(name = "filesystem", indexes = { @Index(name = "NON_REPEAT_DIR", unique = true, columnList = "name,parent_id")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class FileSystemObject {
	@Id
	@GeneratedValue
	@Column(name = "id")
	@JsonProperty("id")
	private int id;

	@JsonProperty("text")
	@Column(name = "name", nullable = false)
	private String selfName;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Directory parentDir;

	// non persisted properties
	@Transient
	@JsonProperty("state")
	private Map<String, Boolean> stateMap;
	@Transient
	@JsonProperty("icon")
	protected NodeIcon icon;
	@Transient
	@JsonProperty("type")
	protected NodeIcon type;
	@Transient
	@JsonProperty("children")
	protected Boolean canHaveChild;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSelfName() {
		return selfName;
	}

	public void setSelfName(String selfName) {
		this.selfName = selfName;
	}

	public Directory getParentDir() {
		return parentDir;
	}

	public void setParentDir(Directory parentDir) {
		this.parentDir = parentDir;
	}

	public Map<String, Boolean> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<String, Boolean> stateMap) {
		this.stateMap = stateMap;
	}

}
