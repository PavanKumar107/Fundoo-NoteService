package com.bl.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.fundoonotes.model.LabelModel;

public interface LabelRepository extends JpaRepository<LabelModel, Long> {

}
