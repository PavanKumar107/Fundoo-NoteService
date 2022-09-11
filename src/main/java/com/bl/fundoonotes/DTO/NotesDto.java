package com.bl.fundoonotes.DTO;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NotesDto {

	private String title;

	private String description;

	private long userId;

	private LocalDateTime registerDate;

	private LocalDateTime updateDate;

	private boolean trash;

	private boolean isArchieve;

	private boolean pin;

	private Long labelId;

	private String emailId;

	private String color;

	private LocalDateTime remindertime;
	
}
