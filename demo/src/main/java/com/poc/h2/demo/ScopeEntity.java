package com.poc.h2.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ScopeEntity {
	
	@Id
	@GeneratedValue
	private Long scopeId;
	
	@OneToOne
	@JoinColumn(name="SCE_ID")
	private Scenario sceId;
	
	
	private Long divId;


	public Long getScopeId() {
		return scopeId;
	}


	public void setScopeId(Long scopeId) {
		this.scopeId = scopeId;
	}


	public Long getDivId() {
		return divId;
	}


	public void setDivId(Long divId) {
		this.divId = divId;
	}


	public Scenario getSceId() {
		return sceId;
	}


	public void setSceId(Scenario sceId) {
		this.sceId = sceId;
	}
	
	
	
	

}
