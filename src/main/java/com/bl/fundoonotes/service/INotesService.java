package com.bl.fundoonotes.service;
import java.util.List;
import java.util.Optional;
import com.bl.fundoonotes.DTO.NotesDto;
import com.bl.fundoonotes.model.NotesModel;
import com.bl.fundoonotes.util.Response;

public interface INotesService {
	
	NotesModel createNote(NotesDto notesDto,String token);
	
	NotesModel updateNote(NotesDto notesDto,Long id,String token);
	
	List<NotesModel> readAllNotes(String token);
	
	Optional<NotesModel> readNotesById(Long id,String token);

	NotesModel archeiveNoteById(Long id, String token);

	NotesModel unArcheiveNoteById(Long id, String token);

	NotesModel trashNote(Long id, String token);

	NotesModel restoreNote(Long id, String token);

	Response deleteNote(Long id, String token);

	NotesModel changeNoteColor(Long id, String color,String token);

	NotesModel pinNote(Long id, String token);

	NotesModel unPinNote(Long id, String token);

}
