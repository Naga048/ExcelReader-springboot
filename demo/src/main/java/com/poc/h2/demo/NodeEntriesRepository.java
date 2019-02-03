package com.poc.h2.demo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface NodeEntriesRepository extends CrudRepository<NodeEntries, Long> {

	@Query("select o from NodeEntries o where o.nodeEntryName =:headerName")
	public List<NodeEntries> findByNodeName(@Param("headerName") String headerName);
	
	@Query("select o from NodeEntries o where o.nodeId.id =:nodeId")
	public List<NodeEntries> findByNid(@Param("nodeId") Long nodeId);
	
}
