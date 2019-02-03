package com.poc.h2.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class ParameterSet {
	
	@Id
	@GeneratedValue
	private Long pId;
	
	@ManyToOne
	@JoinColumn(name="SCOPE_ID")
	private ScopeEntity scopeId;
	
	@ManyToOne
	@JoinColumn(name="SCE_ID")
	private Scenario sceId;
	
	@Lob
	private byte[] data;
	
	private String fileName;

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ScopeEntity getScopeId() {
		return scopeId;
	}

	public void setScopeId(ScopeEntity scopeId) {
		this.scopeId = scopeId;
	}

	public Scenario getSceId() {
		return sceId;
	}

	public void setSceId(Scenario sceId) {
		this.sceId = sceId;
	}
	
	
	
	
	
	
	

}
