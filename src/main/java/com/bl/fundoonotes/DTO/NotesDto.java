package com.bl.fundoonotes.DTO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 *  
 * Purpose:DTO for the notes data
 * 
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 
@Data
public class NotesDto {

	@Pattern(regexp = "^[A-Z]{1,}[a-z\\s]{2,}$", message = "Title name is invalid, first letter should be uppercase!")
	private String title;

	@NotBlank(message = "description cannot be empty")
	private String description;

	private boolean Trash;

	private boolean isArchieve ;

	private boolean pin;

	private Long labelId;

	@Pattern(regexp = "([a-zA-Z0-9./.-])+.([a-zA-Z0-9./.-])?@([a-z]{2,7})+.([a-z]{2,4})+.([a-z]{2,4})?", message = "Valid format is: abc.xyz@gmail.com")
	private String emailId;

	@NotBlank(message = "Color cannot be empty")
	private String color;

}
