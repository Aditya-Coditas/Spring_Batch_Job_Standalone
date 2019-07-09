package com.coditas.SpringBatchStandalone.repository;

import com.coditas.SpringBatchStandalone.model.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information,String> {

}
