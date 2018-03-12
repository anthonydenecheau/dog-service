package com.scc.enci.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.scc.enci.model.Dog;

@Repository
public interface DogRepository extends CrudRepository<Dog, String>, PagingAndSortingRepository<Dog, String>, JpaSpecificationExecutor<Dog>  {
	
    public Dog findById(int id);
    public List<Dog> findByTatouage(String toutage);
    public List<Dog> findByTranspondeur(String transpondeur);
    
}
