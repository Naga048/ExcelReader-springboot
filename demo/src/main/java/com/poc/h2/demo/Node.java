package com.poc.h2.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Node extends BaseEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String nodeName;
	private String headerCount;
	private String nodeType;
	
	@OneToOne
	@JoinColumn(name="P_ID")
	private ParameterSet parameterSet;
	

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getHeaderCount() {
		return headerCount;
	}
	public void setHeaderCount(String headerCount) {
		this.headerCount = headerCount;
	}
	public ParameterSet getParameterSet() {
		return parameterSet;
	}
	public void setParameterSet(ParameterSet parameterSet) {
		this.parameterSet = parameterSet;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	
	
	
	
}
