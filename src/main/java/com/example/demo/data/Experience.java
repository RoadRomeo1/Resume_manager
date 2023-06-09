package com.example.demo.data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Experience {
	
	@NotBlank(message = "Designation can not be blank.")
	private String designation;
	
	@NotBlank(message = "Company name can not be blank.")
	private String company;
	
	@Max(value = 2023, message = "Starting Year can not be in future.")
	@Min(value = 1850, message = "Starting year should not be before 1850's.") 
	private int startingYear;
	
	private int endingYear;
	
	private boolean currentlyWorking;
	
	@NotBlank(message = "Description can not be empty.")
	private String description;
}
