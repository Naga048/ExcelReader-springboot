package com.poc.h2.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class NodeEntryXref extends BaseEntity {
	
	@Id
	@GeneratedValue
	private Long xrefId;
	@ManyToOne
	@JoinColumn(name="NODE_ENTRY_ID")
	private NodeEntries childNodeId;
	
	private Long parentNodeId;

	public Long getXrefId() {
		return xrefId;
	}

	public void setXrefId(Long xrefId) {
		this.xrefId = xrefId;
	}

	public NodeEntries getChildNodeId() {
		return childNodeId;
	}

	public void setChildNodeId(NodeEntries childNodeId) {
		this.childNodeId = childNodeId;
	}

	public Long getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(Long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	
	
	

}
