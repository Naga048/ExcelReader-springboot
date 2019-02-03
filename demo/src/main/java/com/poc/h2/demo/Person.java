package com.poc.h2.demo;

public class Person {
	
	private String name; 
	private String city; 
	private int age; 
	
	public Person(String name, String city, int age) 
	{
		this.name = name; this.city = city; this.age = age; 
	}
	
	public Person() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	@Override public String toString() { return String.format("%s,%s,%d)", name, city, age); }

	
	

}
