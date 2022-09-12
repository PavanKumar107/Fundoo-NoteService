package com.bl.fundoonotes.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bl.fundoonotes.model.NotesModel;

@Repository
public interface NotesRepository extends JpaRepository<NotesModel, Long>{

	@Query(value = "select * from notes where pin = true", nativeQuery = true)
	List<NotesModel> findAllByPin();

	@Query(value = "select * from notes where is_archieve  = true", nativeQuery = true)
	List<NotesModel> findAllByisArchieve();
	
	@Query(value = "select * from notes where Trash = true", nativeQuery = true)
	List<NotesModel> findAllByTrash();
}
