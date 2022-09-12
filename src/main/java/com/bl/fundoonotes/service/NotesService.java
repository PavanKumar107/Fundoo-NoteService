package com.bl.fundoonotes.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bl.fundoonotes.DTO.NotesDto;
import com.bl.fundoonotes.exception.NotesNotFoundException;
import com.bl.fundoonotes.model.NotesModel;
import com.bl.fundoonotes.repository.NotesRepository;
import com.bl.fundoonotes.util.Response;
import com.bl.fundoonotes.util.TokenUtil;

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

	@Override
	public NotesModel createNote(NotesDto notesDto,String token) {
		boolean isUserPresent = restTemplate.getForObject("http://Fundoo-UserService:8066/user/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			NotesModel model = new NotesModel(notesDto);
			notesRepository.save(model);
			String body = "Note created successfully with Id"+model.getId();
			String subject = "Note created Successfully";
			mailService.send(model.getEmailId(), subject, body);
			return model;
		}
		throw new NotesNotFoundException(400,"Token not find");
	}

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
				notesRepository.save(isNotePresent.get());
				String body = "Note updated successfully with Id"+isNotePresent.get().getId();
				String subject = "Note updated Successfully";
				mailService.send(isNotePresent.get().getEmailId(), subject, body);
				return isNotePresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");
		}
		throw new NotesNotFoundException(400,"Token not find");
	}

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
		throw new NotesNotFoundException(400,"Token not find");
	}

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
		throw new NotesNotFoundException(400,"Token not find");
	}

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
		throw new NotesNotFoundException(400,"Token not find");
	}

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
		throw new NotesNotFoundException(400,"Token not find");
	}

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
		throw new NotesNotFoundException(400,"Token not find");
	}

	@Override
	public Response deleteNote(Long id, String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<NotesModel> isNotePresent = notesRepository.findById(userId);
		if(isNotePresent.isPresent()) {
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
	
	@Override
	public NotesModel changeNoteColor(Long id,String color,String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<NotesModel> isNotePresent = notesRepository.findById(userId);
		if(isNotePresent.isPresent()) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setColor(color);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Token not find");
	}
	
	@Override
	public NotesModel pinNote(Long id,String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<NotesModel> isNotePresent = notesRepository.findById(userId);
		if(isNotePresent.isPresent()) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setPin(true);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Token not find");
	}
	
	@Override
	public NotesModel unPinNote(Long id,String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<NotesModel> isNotePresent = notesRepository.findById(userId);
		if(isNotePresent.isPresent()) {
			Optional<NotesModel>isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setPin(false);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400,"Notes not present");	
		}
		throw new NotesNotFoundException(400,"Token not find");
	}
}