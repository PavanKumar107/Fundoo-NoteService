package com.bl.fundoonotes.controller;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
	public ResponseEntity<Response> createNote(@Valid@RequestBody NotesDto notesDto,@RequestHeader String token) {
		NotesModel notesModel = notesService.createNote(notesDto,token);
		Response response = new Response("Note created successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 * Purpose: Update Note
	 * @Param: noteDto,id,token
	 */
	@PutMapping("/updatenote/{notesId}")
	public ResponseEntity<Response> updateNote(@Valid@RequestBody NotesDto notesDto,@PathVariable Long notesId,@RequestHeader String token) {
		NotesModel notesModel = notesService.updateNote(notesDto,notesId,token);
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
	 * Purpose: Read Note by notesId
	 * @Param: notesId,token
	 */
	@GetMapping("/readnotesbyid/{notesId}")
	public ResponseEntity<Response> readNotesById(@PathVariable Long notesId,@RequestHeader String token){
		Optional<NotesModel> notesModel = notesService.readNotesById(notesId,token);
		Response response = new Response("Fetching notes by id successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Archeive Note by notesId
	 * @Param: notesId,token
	 */
	@PutMapping("/archeivenote/{notesId}")
	public ResponseEntity<Response> archeiveNoteById(@PathVariable Long notesId,@RequestHeader String token) {
		NotesModel notesModel = notesService.archeiveNoteById(notesId,token);
		Response response = new Response("Archeived notes by id successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Unarcheive Note by notesId
	 * @Param: notesId,token
	 */
	@PutMapping("/unarcheivenote/{notesId}")
	public ResponseEntity<Response> unArcheiveNoteById(@PathVariable Long notesId,@RequestHeader String token) {
		NotesModel notesModel = notesService.unArcheiveNoteById(notesId,token);
		Response response = new Response("Unarcheived notes by id successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:Add note to trash by notesId
	 * @Param: notesId,token
	 */
	@PutMapping("/trashnote/{notesId}")
	public ResponseEntity<Response> trashNote(@PathVariable Long notesId,@RequestHeader String token) {
		NotesModel notesModel = notesService.trashNote(notesId,token);
		Response response = new Response("Notes added to trash successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Restore Note by notesId
	 * @Param: notesId,token
	 */
	@PutMapping("/restorenote/{notesId}")
	public ResponseEntity<Response> restoreNote(@PathVariable Long notesId,@RequestHeader String token) {
		NotesModel notesModel = notesService.restoreNote(notesId,token);
		Response response = new Response("Note restored successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Delete Note by notesId
	 * @Param: notesId,token
	 */
	@DeleteMapping("/deletenote/{notesId}")
	public ResponseEntity<Response> deleteNote(@PathVariable Long notesId,@RequestHeader String token) {
		NotesModel notesModel = notesService.deleteNote(notesId,token);
		Response response = new Response("Note deleted successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Change Note color by notesId
	 * @Param: notesId,token,color
	 */
	@PutMapping("/changenotecolor/{notesId}")
	public ResponseEntity<Response> changeNoteColor(@PathVariable Long notesId,@RequestParam String color,@RequestHeader  String token) {
		NotesModel notesModel = notesService.changeNoteColor(notesId,color,token);
		Response response = new Response("Note color changed successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Pin Note by notesId
	 * @Param: notesId,token
	 */
	@PutMapping("/pinnote/{notesId}")
	public ResponseEntity<Response> pinNote(@PathVariable Long notesId,@RequestHeader  String token) {
		NotesModel notesModel = notesService.pinNote(notesId,token);
		Response response = new Response("Note pinned successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Unpin Note by notesId
	 * @Param: notesId,token
	 */
	@PutMapping("/unpinnote/{notesId}")
	public ResponseEntity<Response> unPinNote(@PathVariable Long notesId,@RequestHeader  String token) {
		NotesModel notesModel = notesService.unPinNote(notesId,token);
		Response response = new Response("Note unpinned successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Fetching pinned notes
	 * @Param: token
	 */
	@GetMapping("/getallpinnednoted")
	public ResponseEntity<Response> getAllPinnedNotes(@RequestHeader String token) {
		List<NotesModel> notesModel = notesService.getAllPinnedNotes(token);
		Response response = new Response("Fetching all pinned notes successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Fetching pinned notes
	 * @Param: token
	 */
	@GetMapping("/getallarchievednotes")
	public ResponseEntity<Response> getAllArchievedNotes(@RequestHeader String token) {
		List<NotesModel> notesModel = notesService.getAllArchievedNotes(token);
		Response response = new Response("Fetching all archieved notes sucessfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Fetching all notes from trash
	 * @Param: token
	 */
	@GetMapping("/getalltrashtotes")
	public ResponseEntity<Response> getAllTrashNotes(@RequestHeader String token) {
		List<NotesModel> notesModel = notesService.getAllTrashNotes(token);
		Response response = new Response("Fetching all notes from trash successfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: notes and label many to many mapping
	 * @Param: notesId,labelId,token
	 */
	@GetMapping("/notesLabelList")
	public ResponseEntity<Response> notesLabelList(@RequestParam Long notesId,@RequestParam Long labelId,@RequestHeader String token) {
		NotesModel notesModel = notesService.notesLabelList(notesId,labelId,token);
		Response response = new Response("notes and label mapping Successfull", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: to add collaborators to notes 
	 * @Param: emailid,notesId,collaborator
	 */
	@PutMapping("/addcollaborator/{emailId}")
	public ResponseEntity<Response> addCollaborator(@RequestParam Long notesId,@RequestParam String collaborator,@RequestHeader String token ) {
		NotesModel notesModel = notesService.addCollaborator(notesId,collaborator,token);
		Response response = new Response("collaborated sucessfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: To set remainder time
	 * @Param: remainder time,token,notesId
	 */
	@PutMapping("/setremaindertime/{notesId}")
	public ResponseEntity<Response> setRemainderTime(@RequestParam String remainderTime,@RequestHeader String token,@PathVariable Long notesId) {
		NotesModel notesModel = notesService.setRemainderTime(remainderTime,token,notesId);
		Response response = new Response("Remainder time set sucessfully", 200, notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
