package com.bl.fundoonotes.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.fundoonotes.model.NotesModel;

@Repository
public interface NotesRepository extends JpaRepository<NotesModel, Long>{

}