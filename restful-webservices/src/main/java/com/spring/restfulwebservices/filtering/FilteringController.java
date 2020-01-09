package com.spring.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	// Allow only field1 and field2
	@GetMapping("/filtering")
	public MappingJacksonValue retrieveSomeBean(){
		DemoBean demoBean = new DemoBean("value1", "value2", "value3");
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("DemoBeanFiler", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(demoBean);
		mapping.setFilters(filters);
		return mapping;
	}
	
	// Allow only field2 and field3
	@GetMapping("/filtering-list")
	public List<DemoBean> retrieveListOfSomeBean(){
		List<DemoBean> list = Arrays.asList(new DemoBean("value1", "value2", "value3"),
				new DemoBean("value1", "value2", "value3"));
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("DemoBeanFiler", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(list);
		mapping.setFilters(filters);
		return list;
	}

}
