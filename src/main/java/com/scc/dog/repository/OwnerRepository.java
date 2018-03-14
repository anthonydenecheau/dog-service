package com.scc.dog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scc.dog.model.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner,String>  {
	
	public Owner findById(int id);
    public Owner findByIdDog(int idDog);

}
