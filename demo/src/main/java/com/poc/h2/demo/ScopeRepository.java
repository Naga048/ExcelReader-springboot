package com.poc.h2.demo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface ScopeRepository extends CrudRepository<ScopeEntity, Long>{
	
	@Query("select s from ScopeEntity s where s.sceId =:sceId")
	public List<ScopeEntity> findBySceId(@Param("sceId") Scenario sceId);

}
