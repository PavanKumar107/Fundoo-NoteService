package com.bl.fundoonotes.service;
import java.util.List;

import com.bl.fundoonotes.DTO.LabelDto;
import com.bl.fundoonotes.model.LabelModel;
import com.bl.fundoonotes.util.Response;

/**
 *  
 * Purpose:Label Service Interface
 * 
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/

//all the method for the service are registered here
public interface ILabelService {

	LabelModel addLabel(LabelDto labelDto, String token);

	LabelModel updateLabel(LabelDto labelDto, Long id, String token);

	List<LabelModel> getAllLabels(String token);

	Response deleteLabel(Long id, String token);
}
