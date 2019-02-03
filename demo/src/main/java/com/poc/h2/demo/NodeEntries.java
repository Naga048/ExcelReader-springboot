package com.poc.h2.demo;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class NodeEntries extends BaseEntity implements Comparator<NodeEntries> {
	
	@Id
	@GeneratedValue
	private Long nodeEntryId;
	@ManyToOne
	@JoinColumn(name="ID")
	private Node nodeId;
	
	private String nodeEntryName;
	
	private double entryValue;

	public Long getNodeEntryId() {
		return nodeEntryId;
	}

	public void setNodeEntryId(Long nodeEntryId) {
		this.nodeEntryId = nodeEntryId;
	}

	public Node getNodeId() {
		return nodeId;
	}

	public void setNodeId(Node nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeEntryName() {
		return nodeEntryName;
	}

	public void setNodeEntryName(String nodeEntryName) {
		this.nodeEntryName = nodeEntryName;
	}

	public double getEntryValue() {
		return entryValue;
	}

	public void setEntryValue(double entryValue) {
		this.entryValue = entryValue;
	}

	@Override
	public int compare(NodeEntries o1, NodeEntries o2) {
		// TODO Auto-generated method stub
		return o1.getNodeEntryId().compareTo(o2.getNodeEntryId());
	}
	
	
	
	

}
