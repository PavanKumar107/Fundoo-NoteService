package com.bl.fundoonotes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.bl.fundoonotes.DTO.LabelDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  
 * Purpose:Model for the label data
 * 
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 
@Entity
@Table(name = "label")
@Data
@NoArgsConstructor
public class LabelModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String labelName;

	private Long userId;
	
	private Long noteId;
	
	private LocalDateTime registerDate;

	private LocalDateTime updateDate;

	public LabelModel(LabelDto labelDto) {
		this.labelName = labelDto.getLabelName();
	}
}
