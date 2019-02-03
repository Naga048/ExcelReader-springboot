package com.poc.h2.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ScenarioReporistory extends CrudRepository<Scenario, Long>{

}
