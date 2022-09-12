package com.bl.fundoonotes.service;

import java.util.List;

import com.bl.fundoonotes.DTO.LabelDto;
import com.bl.fundoonotes.model.LabelModel;

public interface ILabelService {

	LabelModel addLabel(LabelDto labelDto, String token);

	LabelModel updateLabel(LabelDto labelDto, Long id, String token);

	List<LabelModel> getAllLabels(String token);
}
