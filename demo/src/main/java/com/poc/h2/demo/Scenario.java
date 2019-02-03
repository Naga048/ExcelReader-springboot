package com.poc.h2.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Scenario extends BaseEntity {
	
	@Id
	@GeneratedValue
	private Long sceId;
	private String sceName;
	public Long getSceId() {
		return sceId;
	}
	public void setSceId(Long sceId) {
		this.sceId = sceId;
	}
	public String getSceName() {
		return sceName;
	}
	public void setSceName(String sceName) {
		this.sceName = sceName;
	}
	
	
	

}
