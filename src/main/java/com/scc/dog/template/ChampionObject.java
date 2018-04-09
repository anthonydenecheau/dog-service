package com.scc.dog.template;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class ChampionObject {

	@ApiModelProperty(notes = "Dog id", position = 1, allowEmptyValue=true)
	private int id;
	
	@ApiModelProperty(notes = "Dog pedigrees", position = 2, allowEmptyValue=true)
	private List<PedigreeObject> pedigrees;

	@ApiModelProperty(dataType = "com.scc.dog.template.swaggerType.Token", notes = "Dog tokens (chip, tatoo)", position = 3, allowEmptyValue=true)
	private List<Map<String, Object>> tokens;

	@ApiModelProperty(notes = "Dog title", position = 4, allowEmptyValue=true)
	private TitleObject title;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public List<PedigreeObject> getPedigrees() { return pedigrees; }
	public void setPedigrees(List<PedigreeObject> pedigrees) { this.pedigrees = pedigrees; }
	
	public List<Map<String, Object>> getTokens() { return tokens; }
	public void setTokens(List<Map<String, Object>> tokens) { this.tokens = tokens; }
	
	public TitleObject getTitle() { return title; }
	public void setTitle(TitleObject title) { this.title = title; }
	
	public ChampionObject withId(int id){ this.setId( id ); return this; }
	public ChampionObject withTokens(List<Map<String, Object>> tokens){ this.setTokens(tokens); return this; }
	public ChampionObject withPedigrees(List<PedigreeObject> pedigrees){ this.setPedigrees( pedigrees); return this; }
	public ChampionObject withTitle(TitleObject title){ this.setTitle( title ); return this; }

}
