package br.com.radaresmoveisararas.beans;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "radares")
public class Radar {
	
	public Radar(){}
	
	@DatabaseField(generatedId = true)
	public int id;
	
	@DatabaseField(canBeNull = false)
	public String nomeRua;
	
	@DatabaseField(canBeNull = false)
	public String localizacaoInicio;
	
	@DatabaseField(canBeNull = false)
	public String localizacaoFim;

	@DatabaseField(canBeNull = false)
	public int data;
	
	@DatabaseField(canBeNull = false)
	public int dia;
	
	@DatabaseField(canBeNull = false)
	public int mes;
	
	@DatabaseField(canBeNull = false)
	public String descricao;
	
	public String detalhes(){
		return this.nomeRua+ " - " + this.descricao; 
	}
}
