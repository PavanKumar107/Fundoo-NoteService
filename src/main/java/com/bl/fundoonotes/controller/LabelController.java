package com.bl.fundoonotes.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bl.fundoonotes.DTO.LabelDto;
import com.bl.fundoonotes.model.LabelModel;
import com.bl.fundoonotes.service.ILabelService;
import com.bl.fundoonotes.util.Response;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	ILabelService labelService;

	/**
	 * Purpose: Create label
	 * @Param: labelDto,token
	 */
	@PostMapping("/addlabel")
	public ResponseEntity<Response> addLabel(@Valid@RequestBody LabelDto labelDto,@RequestHeader String token) {
		LabelModel labelModel = labelService.addLabel(labelDto,token);
		Response response = new Response("Label created successfully", 200, labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Update labels
	 * @Param: labelDto,id,token
	 */
	@PutMapping("/updatelabel/{labelId}")
	public ResponseEntity<Response> updateLabel(@Valid@RequestBody LabelDto labelDto,@PathVariable Long labelId ,@RequestHeader String token) {
		LabelModel labelModel = labelService.updateLabel(labelDto,labelId,token);
		Response response = new Response("Label updated successfully", 200, labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	/**
	 * Purpose: fetch all labels
	 * @Param: token
	 */
	@GetMapping("/getalllabels")
	public ResponseEntity<Response> getAllLabels(@RequestHeader String token) {
		List<LabelModel> labelModel = labelService.getAllLabels(token);
		Response response = new Response("Fetching all labels successfully", 200, labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Delete label by Id
	 * @Param: id,token
	 */
	@DeleteMapping("/deletelabel/{labelId}")
	public ResponseEntity<Response> deleteLabel(@PathVariable Long labelId,@RequestHeader String token) {
		Response labelModel = labelService.deleteLabel(labelId,token);
		Response response = new Response("Label deleted successfully", 200, labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
