package com.scc.dog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scc.dog.model.Breeder;

@Repository
public interface BreederRepository extends CrudRepository<Breeder,String>  {
	
    public Breeder findById(int id);
    public Breeder findByIdDog(int idDog);

}
