package com.scc.dog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scc.dog.model.Title;

@Repository
public interface TitleRepository extends CrudRepository<Title, String> {

   public Title findById(long id);

   public List<Title> findByIdDog(int idDog);

   public List<Title> findByObtentionDateGreaterThanEqual(String obtentionDate);

   @Transactional
   public void deleteById(long id);
}
