package com.bl.fundoonotes.DTO;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NotesDto {

	private String title;

	private String description;

	private boolean Trash;

	private boolean isArchieve ;

	private boolean pin;

	private Long labelId;

	private String emailId;

	private String color;
	
}
