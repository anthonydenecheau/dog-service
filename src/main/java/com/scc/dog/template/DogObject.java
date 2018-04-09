package com.scc.dog.template;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class DogObject {

	@ApiModelProperty(notes = "Dog id", position = 1, allowEmptyValue=true)
	private int id;
	
	@ApiModelProperty(notes = "Dog name", position = 2, allowEmptyValue=true)
	private String name;

	@ApiModelProperty(notes = "Dog gender", position = 3, allowEmptyValue=true)
	private String gender;
	
	@ApiModelProperty(notes = "Dog Date of Birth", position = 4, allowEmptyValue=true)
	private String birthDate;
	
	@ApiModelProperty(notes = "Dog Country of Birth", position = 5, allowEmptyValue=true)
	private String birthCountry;
	
	@ApiModelProperty(notes = "Dog pedigrees", position = 6, allowEmptyValue=true)
	private List<PedigreeObject> pedigrees;

	@ApiModelProperty(dataType = "com.scc.dog.template.swaggerType.Token", notes = "Dog tokens (chip, tatoo)", position = 7, allowEmptyValue=true)
	private List<Map<String, Object>> tokens;

	@ApiModelProperty(notes = "Dog breed", position = 8, allowEmptyValue=true)
	private BreedObject breed;	

	@ApiModelProperty(dataType = "com.scc.dog.template.swaggerType.Father", notes = "Dog father", position = 9, allowEmptyValue=true)
	private Map<String, Object> father;
	
	@ApiModelProperty(dataType = "com.scc.dog.template.swaggerType.Mother", notes = "Dog mother", position = 10, allowEmptyValue=true)
	private Map<String, Object> mother;

	@ApiModelProperty(notes = "Dog breeder", position = 11, allowEmptyValue=true)
	private BreederObject breeder;

	@ApiModelProperty(notes = "Dog owners", position = 12, allowEmptyValue=true)
	private List<OwnerObject> owners;
	
	@ApiModelProperty(notes = "Dog titles", position = 13, allowEmptyValue=true)
	private List<TitleObject> titles;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() { return name;}
	public void setName(String name) { this.name = name;}
	
	public String getGender() { return gender;}
	public void setGender(String gender) { this.gender = gender; }
	
	public String getBirthDate() { return birthDate; }
	public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
	
	public String getBirthCountry() { return birthCountry; }
	public void setBirthCountry(String birthCountry) { this.birthCountry = birthCountry; }
	
	public List<PedigreeObject> getPedigrees() { return pedigrees; }
	public void setPedigrees(List<PedigreeObject> pedigrees) { this.pedigrees = pedigrees; }
	
	public List<Map<String, Object>> getTokens() { return tokens; }
	public void setTokens(List<Map<String, Object>> tokens) { this.tokens = tokens; }
	
	public BreedObject getBreed() { return breed; }
	public void setBreed(BreedObject breed) { this.breed = breed; }
	
	public Map<String, Object> getFather() { return father; }
	public void setFather(Map<String, Object> father) { this.father = father; }
	
	public Map<String, Object> getMother() { return mother; }
	public void setMother(Map<String, Object> mother) { this.mother = mother; }
	
	public BreederObject getBreeder() { return breeder; }
	public void setBreeder(BreederObject breeder) { this.breeder = breeder; }
	
	public List<OwnerObject> getOwners() { return owners; }
	public void setOwners(List<OwnerObject> owners) { this.owners = owners; }
	
	public List<TitleObject> getTitles() { return titles; }
	public void setTitles(List<TitleObject> titles) { this.titles = titles; }
	
	public DogObject withId(int id){ this.setId( id ); return this; }
	public DogObject withName(String name){ this.setName( name ); return this; }
	public DogObject withGender(String gender){ this.setGender( gender ); return this; }
	public DogObject withBirthDate(String birthDate){ this.setBirthDate( birthDate ); return this; }
	public DogObject withBirthCountry(String birthCountry){ this.setBirthCountry( birthCountry ); return this; }
	public DogObject withTokens(List<Map<String, Object>> tokens){ this.setTokens(tokens); return this; }
	public DogObject withPedigrees(List<PedigreeObject> pedigrees){ this.setPedigrees( pedigrees); return this; }
	public DogObject withBreed(BreedObject breed){ this.setBreed( breed ); return this; }
	public DogObject withFather(Map<String, Object> father){ this.setFather( father ); return this; }
	public DogObject withMother(Map<String, Object> mother){ this.setMother( mother ); return this; }
	public DogObject withBreeder(BreederObject breeder){ this.setBreeder( breeder ); return this; }
	public DogObject withOwners(List<OwnerObject> owner){ this.setOwners( owner ); return this; }
	public DogObject withTitles(List<TitleObject> titles){ this.setTitles( titles ); return this; }

}
