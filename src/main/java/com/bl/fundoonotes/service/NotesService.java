package com.bl.fundoonotes.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bl.fundoonotes.DTO.NotesDto;
import com.bl.fundoonotes.exception.NotesNotFoundException;
import com.bl.fundoonotes.model.LabelModel;
import com.bl.fundoonotes.model.NotesModel;
import com.bl.fundoonotes.repository.LabelRepository;
import com.bl.fundoonotes.repository.NotesRepository;
import com.bl.fundoonotes.util.Response;
import com.bl.fundoonotes.util.TokenUtil;


/**
 *  
 * Purpose:Service implementation of the Notes
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 */
@Service
public class NotesService implements INotesService{

	@Autowired
	NotesRepository notesRepository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	MailService mailService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	LabelRepository labelRepository;

	
	//Purpose:Service to create note
	@Override
	public NotesModel createNote(NotesDto notesDto,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			NotesModel model = new NotesModel(notesDto);
			model.setRegisterDate(LocalDateTime.now());
			notesRepository.save(model);
			String body = "Note created successfully with Id"+model.getId();
			String subject = "Note created Successfully";
			mailService.send(model.getEmailId(), subject, body);
			return model;
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Service to update note
	@Override
	public NotesModel updateNote(NotesDto notesDto, Long id,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotePresent = notesRepository.findById(id);
			if(isNotePresent.isPresent()) {
				isNotePresent.get().setTitle(notesDto.getTitle());
				isNotePresent.get().setDescription(notesDto.getDescription());
				isNotePresent.get().setLabelId(notesDto.getLabelId());
				isNotePresent.get().setEmailId(notesDto.getEmailId());
				isNotePresent.get().setColor(notesDto.getColor());
				isNotePresent.get().setUpdateDate(LocalDateTime.now());
				notesRepository.save(isNotePresent.get());
				String body = "Note updated successfully with Id"+isNotePresent.get().getId();
				String subject = "Note updated Successfully";
				mailService.send(isNotePresent.get().getEmailId(), subject, body);
				return isNotePresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Service to add read all notes
	@Override
	public List<NotesModel> readAllNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<NotesModel> readAllNotes = notesRepository.findAll();
			if(readAllNotes.size()>0) {
				return readAllNotes;	
			}else {
				throw new NotesNotFoundException(400,"Notes not present");
			}
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to read notes by id
	@Override
	public Optional<NotesModel> readNotesById(Long id,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				return isNotesPresent;
			}else {
				throw new NotesNotFoundException(400,"Notes not present");
			}
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to archive notes
	@Override
	public NotesModel archeiveNoteById(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setArchieve(true);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to unarchive notes
	@Override
	public NotesModel unArcheiveNoteById(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setArchieve(false);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to add note to the trash
	@Override
	public NotesModel trashNote(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setTrash(true);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Token not find");
	}

	//Purpose:Method to restore the notes
	@Override
	public NotesModel restoreNote(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setTrash(false);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to delete the note
	@Override
	public Response deleteNote(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isIdPresent = notesRepository.findById(id);
			if(isIdPresent.isPresent()) {
				notesRepository.delete(isIdPresent.get());
				String body = "Note deleted successfully with Id"+isIdPresent.get().getId();
				String subject = "Note deleted Successfully";
				mailService.send(isIdPresent.get().getEmailId(), subject, body);
				return new Response("Success", 200, isIdPresent.get());
			} else {
				throw new NotesNotFoundException(400, "Notes not found");
			}		
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}

	//Purpose:Method to change the note color
	@Override
	public NotesModel changeNoteColor(Long id,String color,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setColor(color);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to pin the note
	@Override
	public NotesModel pinNote(Long id,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setPin(true);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to unpin the note
	@Override
	public NotesModel unPinNote(Long id,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setPin(false);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to set remainder time
	@Override
	public NotesModel setRemainderTime(Long id,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setRemindertime(LocalDateTime.now());
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}
	//Purpose:Method to fethch all pinned notes
	@Override
	public List<NotesModel> getAllPinnedNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<NotesModel> isNotesPresent = notesRepository.findAllByPin();
			if (isNotesPresent.size()>0) {
				return isNotesPresent;
			}
			throw new NotesNotFoundException(400,"Notes not present");
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to fetch all archieved notes
	@Override
	public List<NotesModel> getAllArchievedNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<NotesModel> isNotePresent = notesRepository.findAllByisArchieve();
			if(isNotePresent.size() > 0) {
				return isNotePresent;
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}

	//Purpose:Method to fetch all notes from the trash
	@Override
	public List<NotesModel> getAllTrashNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<NotesModel> isNotePresent = notesRepository.findAllByTrash();
			if(isNotePresent.size() > 0) {
				return isNotePresent;
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}

	//Purpose:Method to many to many map notes and label
	@Override
	public NotesModel notesLabelList(Long notesId,Long labelId,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<LabelModel> label = new ArrayList<>();
			Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
			if(isLabelPresent.isPresent()) {
				label.add(isLabelPresent.get());
				Optional<NotesModel>isNotePresent = notesRepository.findById(notesId);
				if(isNotePresent.isPresent()) {
					isNotePresent.get().setLabelList(label);
					notesRepository.save(isNotePresent.get());
					return isNotePresent.get();
				}
				throw new NotesNotFoundException(400, "Notes not present");
			}
			throw new NotesNotFoundException(400, "label id invalid");
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}

	//Purpose:Method to add collaborator
//	@Override
//	public NotesModel addCollaborator(String emailId,Long id) {
//		List<NotesModel> model = new ArrayList<>();
//		Optional<NotesModel> isNotesPresent = notesRepository.findById(id);
}
