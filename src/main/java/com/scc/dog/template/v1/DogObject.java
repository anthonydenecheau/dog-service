package com.scc.dog.template.v1;

import io.swagger.annotations.ApiModelProperty;

public class DogObject {

   @ApiModelProperty(notes = "Dog id", position = 1, allowEmptyValue = true)
   private int id;

   @ApiModelProperty(notes = "Dog name", position = 2, allowEmptyValue = true)
   private String nom;

   @ApiModelProperty(notes = "Dog gender", position = 3, allowEmptyValue = true)
   private String sexe;

   @ApiModelProperty(notes = "Dog Date of Birth", position = 4, allowEmptyValue = true)
   private String dateNaissance;

   @ApiModelProperty(notes = "Dog french pedigree", position = 5, allowEmptyValue = true)
   private String lof;

   @ApiModelProperty(notes = "Dog tatoo", position = 6, allowEmptyValue = true)
   private String tatouage;

   @ApiModelProperty(notes = "Dog chip", position = 7, allowEmptyValue = true)
   private String transpondeur;

   @ApiModelProperty(notes = "Dog breed", position = 8, allowEmptyValue = true)
   private String race;

   @ApiModelProperty(notes = "Dog breed variety", position = 9, allowEmptyValue = true)
   private String variete;

   @ApiModelProperty(notes = "Dog color", position = 10, allowEmptyValue = true)
   private String couleur;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getNom() {
      return nom;
   }

   public void setNom(String nom) {
      this.nom = nom;
   }

   public String getSexe() {
      return sexe;
   }

   public void setSexe(String sexe) {
      this.sexe = sexe;
   }

   public String getDateNaissance() {
      return dateNaissance;
   }

   public void setDateNaissance(String dateNaissance) {
      this.dateNaissance = dateNaissance;
   }

   public String getLof() {
      return lof;
   }

   public void setLof(String lof) {
      this.lof = lof;
   }

   public String getTatouage() {
      return tatouage;
   }

   public void setTatouage(String tatouage) {
      this.tatouage = tatouage;
   }

   public String getTranspondeur() {
      return transpondeur;
   }

   public void setTranspondeur(String transpondeur) {
      this.transpondeur = transpondeur;
   }

   public String getRace() {
      return race;
   }

   public void setRace(String race) {
      this.race = race;
   }

   public String getVariete() {
      return variete;
   }

   public void setVariete(String variete) {
      this.variete = variete;
   }

   public String getCouleur() {
      return couleur;
   }

   public void setCouleur(String couleur) {
      this.couleur = couleur;
   }

   public DogObject withId(int id) {
      this.setId(id);
      return this;
   }

   public DogObject withNom(String Nom) {
      this.setNom(Nom);
      return this;
   }

   public DogObject withSexe(String sexe) {
      this.setSexe(sexe);
      return this;
   }

   public DogObject withDateNaissance(String dateNaissance) {
      this.setDateNaissance(dateNaissance);
      return this;
   }

   public DogObject withLof(String lof) {
      this.setLof(lof);
      return this;
   }

   public DogObject withTatouage(String tatouage) {
      this.setTatouage(tatouage);
      return this;
   }

   public DogObject withTranspondeur(String transpondeur) {
      this.setTranspondeur(transpondeur);
      return this;
   }

   public DogObject withRace(String race) {
      this.setRace(race);
      return this;
   }

   public DogObject withVariete(String variete) {
      this.setVariete(variete);
      return this;
   }

   public DogObject withCouleur(String couleur) {
      this.setCouleur(couleur);
      return this;
   }

}
