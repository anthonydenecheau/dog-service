package com.scc.dog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scc.dog.model.Dog;

@Repository
public interface DogRepository extends CrudRepository<Dog, String> {
	
    public Dog findById(int id);
    public List<Dog> findByTatouageIgnoreCase(String toutage);
    public List<Dog> findByTranspondeur(String transpondeur);
    
    @Transactional
    public void deleteById(int id);
}
