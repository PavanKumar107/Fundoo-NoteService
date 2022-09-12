package com.bl.fundoonotes.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.bl.fundoonotes.DTO.LabelDto;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	
//	@ManyToMany(mappedBy = "labellist")
//	private List<Note> notes;

	public LabelModel(LabelDto labelDto) {
		this.labelName = labelDto.getLabelName();
	}
}
