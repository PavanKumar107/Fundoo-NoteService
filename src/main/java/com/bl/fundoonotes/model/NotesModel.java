package com.bl.fundoonotes.model;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.bl.fundoonotes.DTO.NotesDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
public class NotesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
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

//	List<String> collaborator;

	public NotesModel(NotesDto notesDto) {
		this.title = notesDto.getTitle();
		this.description = notesDto.getDescription();
		this.userId = notesDto.getUserId();
		this.registerDate = notesDto.getRegisterDate().now();
		this.updateDate = notesDto.getUpdateDate();
		this.trash = notesDto.isTrash();
		this.isArchieve = notesDto.isArchieve();
		this.pin = notesDto.isPin();
		this.labelId = notesDto.getLabelId();
		this.emailId = notesDto.getEmailId();
		this.color = notesDto.getColor();
		this.remindertime = notesDto.getRemindertime();
	}
}
