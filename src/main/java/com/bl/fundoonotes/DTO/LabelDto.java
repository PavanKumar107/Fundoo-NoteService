package com.bl.fundoonotes.DTO;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 *  
 * Purpose:DTO for the label data
 * 
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 
@Data
public class LabelDto {
	
	@Pattern(regexp = "^[A-Z]{1,}[a-z\\s]{2,}$", message = "Label name is invalid, first letter should be uppercase!")
	private String labelName;
}
