package br.com.radaresmoveisararas.beans;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "versaobd")
public class VersaoBD {

	public VersaoBD(){}
	
	@DatabaseField(generatedId = true)
	public int id;
	
	@DatabaseField(canBeNull = false)
	public int versao;

	@DatabaseField(canBeNull = false)
	public Date dataUpdate;
}
