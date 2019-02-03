package com.poc.h2.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

public class GroupByDemoInJava8 {
	
	public static void main(String[] args) throws JSONException {
		List<Person> people = new ArrayList<>(); 
		people.add(new Person("John", "London", 21)); 
		people.add(new Person("Swann", "London", 21)); 
		people.add(new Person("Kevin", "London", 23)); 
		people.add(new Person("Monobo", "Tokyo", 23)); 
		people.add(new Person("Sam", "Paris", 23)); 
		people.add(new Person("Nadal", "Paris", 31));
		
		Map<Integer,Long> personByAge = people.stream() .
				collect(Collectors.groupingBy(Person::getAge,Collectors.counting())); 
		
		Map<Integer,Map<String,List<Person>>> personByAge1  = people.stream().collect(
				Collectors.groupingBy(
				Person::getAge,
				Collectors.groupingBy(
						Person::getCity
						/*Collectors.mapping(
								Person::getName, 
								Collectors.joining(", ")
								)*/
						)
				)
				);
		
		JSONObject obj = new JSONObject();
		for (Map.Entry<Integer,Map<String,List<Person>>> set : personByAge1.entrySet()) {
			obj.put("age", set.getKey());
			System.out.println(set.getKey());
			//System.out.println(set.getValue());
			Map<String,List<Person>> map = set.getValue();
			JSONObject obj1 = new JSONObject();
			for (Map.Entry<String,List<Person>> person : map.entrySet()) {
				obj1.put("city", person.getKey());
				
				System.out.println(person.getKey());
				List<Person> persons = person.getValue();
				JSONObject obj2 = new JSONObject();
				for (Person person2 : persons) {
					obj2.put("name", person2.getName());
					System.out.println(person2.getName());
				}
				
			}
			
		}
		
		//System.out.println("Person grouped by age in Java 8: " + personByAge);
		/*System.out.println("Person grouped by age in Java 8: " + personByAge1);
		Gson gson = new Gson(); 
		String json = gson.toJson(personByAge1); 
		System.out.println("json: " + json);*/
	}
	

	

}
