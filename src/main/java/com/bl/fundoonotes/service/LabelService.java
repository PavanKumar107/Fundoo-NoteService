package com.bl.fundoonotes.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bl.fundoonotes.DTO.LabelDto;
import com.bl.fundoonotes.DTO.NotesDto;
import com.bl.fundoonotes.exception.NotesNotFoundException;
import com.bl.fundoonotes.model.LabelModel;
import com.bl.fundoonotes.model.NotesModel;
import com.bl.fundoonotes.repository.LabelRepository;
import com.bl.fundoonotes.util.Response;
import com.bl.fundoonotes.util.TokenUtil;

/**
 *  
 * Purpose:Service implementation of the Label
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 */
@Service
public class LabelService implements ILabelService {

	@Autowired
	LabelRepository labelRepository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	MailService mailService;

	@Autowired
	RestTemplate restTemplate;

	//Purpose:Service to add label
	@Override
	public LabelModel addLabel(LabelDto labelDto, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			LabelModel model = new LabelModel(labelDto);
			model.setRegisterDate(LocalDateTime.now());
			model.setUserId(userId);
			labelRepository.save(model);
			return model;
		}
		throw new NotesNotFoundException(400,"Token Invalid");
	}

	//Purpose:Service to update label
	@Override
	public LabelModel updateLabel(LabelDto labelDto, Long labelId,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<LabelModel>isLabelPresent = labelRepository.findById(labelId);
			if(isLabelPresent.isPresent()) {
				isLabelPresent.get().setLabelName(labelDto.getLabelName());
				isLabelPresent.get().setUpdateDate(LocalDateTime.now());
				labelRepository.save(isLabelPresent.get());
				return isLabelPresent.get();
			}
			throw new NotesNotFoundException(400,"Label not present");
		}
		throw new NotesNotFoundException(400,"Token Invalid");
	}

	//Purpose:Service to fetch all the labels
	@Override
	public List<LabelModel> getAllLabels(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<LabelModel> getAllLabels = labelRepository.findAll();
			if(getAllLabels.size()>0) {
				return getAllLabels;	
			}else {
				throw new NotesNotFoundException(400,"Label not present");
			}
		}
		throw new NotesNotFoundException(400,"Token Invalid");
	}

	//Purpose:Method to delete the label
	@Override
	public Response deleteLabel(Long labelId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<LabelModel> isIdPresent = labelRepository.findById(labelId);
			if(isIdPresent.isPresent()) {
				return new Response("label deleted Successfully", 200, isIdPresent.get());
			} else {
				throw new NotesNotFoundException(400, "Label not found");
			}		
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}
}