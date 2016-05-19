package com.mmt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name="properties",indexes={
		 @Index(name = "NON_REPEAT_FILE",unique=true,  columnList="file_id,prop_key"),
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class Property {
	@Id
    @GeneratedValue
    @Column(name="id")
	@JsonProperty("id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="file_id")
	@JsonIgnore
	private File fileID;
	
	@ManyToOne
	@JoinColumn(name="var_id")
    @JsonIgnore
    private Variant variantID;
	
	@JsonProperty("key")
	@Column(name="prop_key")
	private String key;
	
	@JsonProperty("value")
	@Column(name="prop_value")
	private String value;

	
    
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
   
	public File getFileID() {
		return fileID;
	}

	public void setFileID(File fileID) {
		this.fileID = fileID;
	}
    
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Variant getVariantID() {
		return variantID;
	}

	public void setVariantID(Variant variantID) {
		this.variantID = variantID;
	}
    
	
}
