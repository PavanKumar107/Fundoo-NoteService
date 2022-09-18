package com.bl.fundoonotes.service;
import java.util.List;
import java.util.Optional;
import com.bl.fundoonotes.DTO.NotesDto;
import com.bl.fundoonotes.model.NotesModel;
import com.bl.fundoonotes.util.Response;

/**
 *  
 * Purpose:Notes Service Interface
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/

//all the method for the service are registered here
public interface INotesService {

	NotesModel createNote(NotesDto notesDto,String token);

	NotesModel updateNote(NotesDto notesDto,Long notesId,String token);

	List<NotesModel> readAllNotes(String token);

	Optional<NotesModel> readNotesById(Long notesId,String token);

	NotesModel archeiveNoteById(Long notesId, String token);

	NotesModel unArcheiveNoteById(Long notesId, String token);

	NotesModel trashNote(Long notesId, String token);

	NotesModel restoreNote(Long notesId, String token);

	NotesModel deleteNote(Long notesId, String token);

	NotesModel changeNoteColor(Long notesId, String color,String token);

	NotesModel pinNote(Long notesId, String token);

	NotesModel unPinNote(Long notesId, String token);

	List<NotesModel> getAllPinnedNotes(String token);

	List<NotesModel> getAllArchievedNotes(String token);

	List<NotesModel> getAllTrashNotes(String token);

	NotesModel notesLabelList(Long notesId, Long labelId, String token);

	NotesModel setRemainderTime(String remainderTime, String token, Long id);

	NotesModel addCollaborator(Long notesId, String collaborator, String token);

}
