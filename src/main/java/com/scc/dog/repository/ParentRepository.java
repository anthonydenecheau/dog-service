package com.scc.dog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scc.dog.model.Parent;

@Repository
public interface ParentRepository extends CrudRepository<Parent,String>  {
	
    public Parent findById(int id);
    
    @Transactional
    public void deleteById(int id);
}
