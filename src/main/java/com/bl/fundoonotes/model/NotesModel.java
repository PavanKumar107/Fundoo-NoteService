package com.bl.fundoonotes.model;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.bl.fundoonotes.DTO.NotesDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  
 * Purpose:Model for the notes data
 * 
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 
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

	private boolean Trash;

	private boolean isArchieve ;

	private boolean pin;

	private Long labelId;

	private String emailId;

	private String color;

	private String reminderTime;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private List<LabelModel> labelList;
	
	@ElementCollection(targetClass = String.class)
	private  List<String> collaborator;

	public NotesModel(NotesDto notesDto) {
		this.title = notesDto.getTitle();
		this.description = notesDto.getDescription();
		this.Trash = notesDto.isTrash();
		this.isArchieve = notesDto.isArchieve();
		this.pin = notesDto.isPin();
		this.labelId = notesDto.getLabelId();
		this.emailId = notesDto.getEmailId();
		this.color = notesDto.getColor();
	}
}
