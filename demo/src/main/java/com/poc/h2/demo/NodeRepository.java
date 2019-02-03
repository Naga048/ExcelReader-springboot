package com.poc.h2.demo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface NodeRepository extends CrudRepository<Node, Long>  {
	
	@Query("select s from Node s where s.parameterSet.pId =:parameterId")
	public List<Node> findByParamId(@Param("parameterId") Long parameterSet);
	
	@Query("select s from Node s where s.nodeName =:name and s.parameterSet.pId =:pId")
	public Node findByNameAndPid(@Param("name") String name,@Param("pId") Long pId);

}
