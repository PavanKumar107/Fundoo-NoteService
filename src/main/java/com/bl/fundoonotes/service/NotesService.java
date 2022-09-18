package com.bl.fundoonotes.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 **/

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
			Long userId = tokenUtil.decodeToken(token);
			NotesModel model = new NotesModel(notesDto);
			model.setRegisterDate(LocalDateTime.now());
			model.setUserId(userId);
			notesRepository.save(model);
			return model;
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Service to update note
	@Override
	public NotesModel updateNote(NotesDto notesDto, Long notesId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				isNoteAndUserIdPresent.get().setTitle(notesDto.getTitle());
				isNoteAndUserIdPresent.get().setDescription(notesDto.getDescription());
				isNoteAndUserIdPresent.get().setLabelId(notesDto.getLabelId());
				isNoteAndUserIdPresent.get().setColor(notesDto.getColor());
				isNoteAndUserIdPresent.get().setUpdateDate(LocalDateTime.now());
				isNoteAndUserIdPresent.get().setUserId(userId);
				notesRepository.save(isNoteAndUserIdPresent.get());
				return isNoteAndUserIdPresent.get();
			}
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Service to read all notes
	@Override
	public List<NotesModel> readAllNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			List<NotesModel> readAllNotes = notesRepository.findByUserId(userId);
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
	public Optional<NotesModel> readNotesById(Long notesId,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				return isNoteAndUserIdPresent;
			}else {
				throw new NotesNotFoundException(400,"Notes not present");
			}
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to archive notes
	@Override
	public NotesModel archeiveNoteById(Long notesId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				isNoteAndUserIdPresent.get().setArchieve(true);
				notesRepository.save(isNoteAndUserIdPresent.get());
				return isNoteAndUserIdPresent.get();
			}	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to unarchive notes
	@Override
	public NotesModel unArcheiveNoteById(Long notesId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				isNoteAndUserIdPresent.get().setArchieve(false);
				notesRepository.save(isNoteAndUserIdPresent.get());
				return isNoteAndUserIdPresent.get();
			}
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to add note to the trash
	@Override
	public NotesModel trashNote(Long notesId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				isNoteAndUserIdPresent.get().setTrash(true);
				notesRepository.save(isNoteAndUserIdPresent.get());
				return isNoteAndUserIdPresent.get();
			}
		}
		throw new NotesNotFoundException(400,"Token not find");
	}

	//Purpose:Method to restore the notes
	@Override
	public NotesModel restoreNote(Long notesId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				isNoteAndUserIdPresent.get().setTrash(false);
				notesRepository.save(isNoteAndUserIdPresent.get());
				return isNoteAndUserIdPresent.get();
			}
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to delete the note
	@Override
	public NotesModel deleteNote(Long notesId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent() && isNoteAndUserIdPresent.get().isTrash() == true) {
				notesRepository.delete(isNoteAndUserIdPresent.get());
				return isNoteAndUserIdPresent.get();
			} 
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to change the note color
	@Override
	public NotesModel changeNoteColor(Long notesId,String color,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				isNoteAndUserIdPresent.get().setColor(color);
				notesRepository.save(isNoteAndUserIdPresent.get());
				return isNoteAndUserIdPresent.get();
			}	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to pin the note
	@Override
	public NotesModel pinNote(Long notesId,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				isNoteAndUserIdPresent.get().setPin(true);
				notesRepository.save(isNoteAndUserIdPresent.get());
				return isNoteAndUserIdPresent.get();
			}
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to unpin the note
	@Override
	public NotesModel unPinNote(Long notesId,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				isNoteAndUserIdPresent.get().setPin(false);
				notesRepository.save(isNoteAndUserIdPresent.get());
				return isNoteAndUserIdPresent.get();
			}	
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to fethch all pinned notes
	@Override
	public List<NotesModel> getAllPinnedNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			List<NotesModel> user= notesRepository.findByUserId(userId);
			if(user.size()>0) {
				List<NotesModel> isNotesPresent = notesRepository.findAllByPin();
				if (isNotesPresent.size()>0) {
					return isNotesPresent;
				}
			}
		}
		throw new NotesNotFoundException(400,"Invalid token");
	}

	//Purpose:Method to fetch all archieved notes
	@Override
	public List<NotesModel> getAllArchievedNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			List<NotesModel> user = notesRepository.findByUserId(userId);
			if(user.size()>0) {
				List<NotesModel> isNotePresent = notesRepository.findAllByisArchieve();
				if(isNotePresent.size() > 0) {
					return isNotePresent;
				}
			}
			throw new NotesNotFoundException(400,"User Id not present");
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}

	//Purpose:Method to fetch all notes from the trash
	@Override
	public List<NotesModel> getAllTrashNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			List<NotesModel> user = notesRepository.findByUserId(userId);
			if(user.size()>0) {
				List<NotesModel> isNotePresent = notesRepository.findAllByTrash();
				if(isNotePresent.size() > 0) {
					return isNotePresent;
				}	
			}
			throw new NotesNotFoundException(400,"User Id not present");
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}

	//Purpose:Method to many to many map notes and label
	@Override
	public NotesModel notesLabelList(Long notesId,Long labelId,String token) {
		Long userId = tokenUtil.decodeToken(token);		
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<LabelModel> label = new ArrayList<>();
			List<NotesModel> notes = new ArrayList<>();
			Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
			if(isLabelPresent.isPresent()) {
				label.add(isLabelPresent.get());
			}
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			if(isNoteAndUserIdPresent.isPresent()) {
				isNoteAndUserIdPresent.get().setLabelList(label);
				notes.add(isNoteAndUserIdPresent.get());
				isLabelPresent.get().setNotes(notes);
				notesRepository.save(isNoteAndUserIdPresent.get());
				labelRepository.save(isLabelPresent.get());
				return isNoteAndUserIdPresent.get();
			}
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}

	//Purpose:Method to add collaborator
	@Override
	public NotesModel addCollaborator(Long notesId,String collaborator,String token) {
		Long userId = tokenUtil.decodeToken(token);
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNoteAndUserIdPresent = notesRepository.findByUserIdAndNotesId(userId, notesId);
			List<String> collaboratorList = new ArrayList<>();
			if (isNoteAndUserIdPresent.isPresent()) {
				boolean isEmailIdPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateemail/" + collaborator, Boolean.class);
				collaboratorList.add(collaborator);
				isNoteAndUserIdPresent.get().setCollaborator(collaboratorList);
				notesRepository.save(isNoteAndUserIdPresent.get());
				List<String> noteList = new ArrayList<>();
				noteList.add(isNoteAndUserIdPresent.get().getEmailId());
				NotesModel note = new NotesModel();
				note.setTitle(isNoteAndUserIdPresent.get().getTitle());
				note.setArchieve(isNoteAndUserIdPresent.get().isArchieve());
				note.setCollaborator(isNoteAndUserIdPresent.get().getCollaborator());
				note.setColor(isNoteAndUserIdPresent.get().getColor());
				note.setDescription(isNoteAndUserIdPresent.get().getDescription());
				note.setEmailId(isNoteAndUserIdPresent.get().getEmailId());
				note.setLabelId(isNoteAndUserIdPresent.get().getLabelId());
				note.setNotesId(isNoteAndUserIdPresent.get().getNotesId());
				note.setPin(isNoteAndUserIdPresent.get().isPin());
				note.setRegisterDate(isNoteAndUserIdPresent.get().getRegisterDate());
				note.setReminderTime(isNoteAndUserIdPresent.get().getReminderTime());
				note.setTrash(isNoteAndUserIdPresent.get().isTrash());
				note.setUpdateDate(isNoteAndUserIdPresent.get().getUpdateDate());
				note.setUserId(isNoteAndUserIdPresent.get().getUserId());
				notesRepository.save(note);
				return isNoteAndUserIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Collaborator not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	//Purpose:Method to add remaindertime
	@Override
	public NotesModel setRemainderTime(String remainderTime,String token,Long notesId) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNotesPresent = notesRepository.findById(notesId);
			if(isNotesPresent.isPresent()) {
				LocalDate today = LocalDate.now();
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				LocalDate remainder = LocalDate.parse(remainderTime, dateTimeFormatter);
				if(remainder.isBefore(today)) {
					throw new NotesNotFoundException(400, "invalid remainder time");
				}
				isNotesPresent.get().setReminderTime(remainderTime);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}
}

