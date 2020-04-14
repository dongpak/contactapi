/**
 * 
 */
package com.churchclerk.contactapi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author dongp
 *
 */
@ComponentScan({"com.churchclerk"})
@SpringBootApplication
public class ContactApiApplication {

	private static Logger logger = LoggerFactory.getLogger(ContactApiApplication.class);

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ContactApiApplication.class, args);
	}

}
