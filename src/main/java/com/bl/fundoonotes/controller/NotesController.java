package com.bl.fundoonotes.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bl.fundoonotes.DTO.NotesDto;
import com.bl.fundoonotes.model.NotesModel;
import com.bl.fundoonotes.service.INotesService;
import com.bl.fundoonotes.util.Response;

/**
 * Purpose: Notes Controller to process Note APIs.
 * @version: 4.15.1.RELEASE
 * @author: Pavan Kumar G V  
 */
@RestController
@RequestMapping("/notes")
public class NotesController {

	@Autowired
	INotesService notesService;

	/**
	 * Purpose: Create Note
	 * @Param: noteDto,token
	 */
	@PostMapping("/createnote")
	public ResponseEntity<Response> createNote(@RequestBody NotesDto notesDto,@RequestHeader String token) {
		NotesModel notesModel = notesService.createNote(notesDto,token);
		Response response = new Response("Note created successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 * Purpose: Update Note
	 * @Param: noteDto,id,token
	 */
	@PutMapping("/updatenote/{id}")
	public ResponseEntity<Response> updateNote(@RequestBody NotesDto notesDto,@PathVariable Long id,@RequestHeader String token) {
		NotesModel notesModel = notesService.updateNote(notesDto,id,token);
		Response response = new Response("Note updated successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Read all Notes
	 * @Param: token
	 */
	@GetMapping("/readnotes")
	public ResponseEntity<Response> readAllNotes(@RequestHeader String token){
		List<NotesModel> notesModel = notesService.readAllNotes(token);
		Response response = new Response("Fetching all notes successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Read Note by Id
	 * @Param: id,token
	 */
	@GetMapping("/readnotesbyid/{id}")
	public ResponseEntity<Response> readNotesById(@PathVariable Long id,@RequestHeader String token){
		Optional<NotesModel> notesModel = notesService.readNotesById(id,token);
		Response response = new Response("Fetching notes by id successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Archeive Note by Id
	 * @Param: id,token
	 */
	@PutMapping("/archeivenote/{id}")
	public ResponseEntity<Response> archeiveNoteById(@PathVariable Long id,@RequestHeader String token) {
		NotesModel notesModel = notesService.archeiveNoteById(id,token);
		Response response = new Response("Archeived notes by id successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Unarcheive Note by Id
	 * @Param: id,token
	 */
	@PutMapping("/unarcheivenote/{id}")
	public ResponseEntity<Response> unArcheiveNoteById(@PathVariable Long id,@RequestHeader String token) {
		NotesModel notesModel = notesService.unArcheiveNoteById(id,token);
		Response response = new Response("Unarcheived notes by id successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:Add note to trash by Id
	 * @Param: id,token
	 */
	@PutMapping("/trashnote/{id}")
	public ResponseEntity<Response> trashNote(@PathVariable Long id,@RequestHeader String token) {
		NotesModel notesModel = notesService.trashNote(id,token);
		Response response = new Response("Notes added to trash successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Restore Note by Id
	 * @Param: id,token
	 */
	@PutMapping("/restorenote/{id}")
	public ResponseEntity<Response> restoreNote(@PathVariable Long id,@RequestHeader String token) {
		NotesModel notesModel = notesService.restoreNote(id,token);
		Response response = new Response("Note restored successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose: Delete Note by Id
	 * @Param: id,token
	 */
	@DeleteMapping("/deletenote/{id}")
	public ResponseEntity<Response> deleteNote(@PathVariable Long id,@RequestHeader String token) {
		Response notesModel = notesService.deleteNote(id,token);
		Response response = new Response("Note deleted successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose: Change Note color by id
	 * @Param: id,token,color
	 */
	@PutMapping("/changenotecolor/{id}")
	public ResponseEntity<Response> changeNoteColor(@PathVariable Long id,@RequestParam String color,@RequestHeader  String token) {
		NotesModel notesModel = notesService.changeNoteColor(id,color,token);
		Response response = new Response("Note color changed successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose: Pin Note by Id
	 * @Param: id,token
	 */
	@PutMapping("/pinnote/{id}")
	public ResponseEntity<Response> pinNote(@PathVariable Long id,@RequestHeader  String token) {
		NotesModel notesModel = notesService.pinNote(id,token);
		Response response = new Response("Note pinned successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose: Unpin Note by Id
	 * @Param: id,token
	 */
	@PutMapping("/unpinnote/{id}")
	public ResponseEntity<Response> unPinNote(@PathVariable Long id,@RequestHeader  String token) {
		NotesModel notesModel = notesService.unPinNote(id,token);
		Response response = new Response("Note unpinned successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
