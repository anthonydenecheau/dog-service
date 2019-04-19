package com.scc.dog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scc.dog.model.Pedigree;

@Repository
public interface PedigreeRepository extends CrudRepository<Pedigree, String> {

   public Pedigree findById(long id);

   public List<Pedigree> findByIdDog(int idDog);

   @Transactional
   public void deleteById(long id);
}
