package com.bl.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.fundoonotes.model.LabelModel;

/**
 *  
 * Purpose:Repository connected to the database
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 
public interface LabelRepository extends JpaRepository<LabelModel, Long> {

}
