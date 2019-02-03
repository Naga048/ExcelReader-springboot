package com.poc.h2.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan("com.poc.h2.demo")
@EnableAutoConfiguration
@EnableJpaAuditing
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
		
		/*Process p=null;
		try {
			p = Runtime.getRuntime().exec("python C:/Users/Naga/Desktop/R_folder/script.py");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String ret=null;
		try {
			Stream<String> lines = in.lines();
			Iterator<String>  it =  lines.iterator();
			while (it.hasNext()) {
				String string = (String) it.next();
				System.out.println("string  : "+string);
				
			}
			//System.out.println("lines  : "+lines.count());
			ret = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("value is : "+ret);
		String totalPriceStr = "Your have paid £154.75666 for this order";
		// or String totalPriceStr = “Total Price: £154.75”;

		double totalPrice = Double.parseDouble(totalPriceStr.replaceAll("[^0-9\\.]+", ""));
		System.out.println("totalPrice  : "+totalPrice);
*/	}
	
	
			
	

}

