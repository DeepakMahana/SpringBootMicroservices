package com.spring.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/hello-world")
	public String helloworld(){
		return "Hello World";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorld helloworldbean(){
		return new HelloWorld("Hello World");
	}
	
	@GetMapping(path="/hello-pathvariable/{name}")
	public HelloWorld hellopath(@PathVariable String name){
		return new HelloWorld(String.format("Hello World, %s", name));
	}
	
	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}
