package com.poc.h2.demo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface ParameterRepository  extends CrudRepository<ParameterSet, Long> {
	
	@Query("select s from ParameterSet s where s.sceId =:sceId")
	public List<ParameterSet> findBySceId(@Param("sceId") Scenario sceId);

}
