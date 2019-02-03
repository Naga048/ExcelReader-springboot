package com.poc.h2.demo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface KeyValueRepository extends CrudRepository<KeyValue, Long> {

	
	@Query(name="select o from KeyValue kv where kv.node.id:=nodeId")
	public List<KeyValue> findByNodeId(Long nodeId);
}
