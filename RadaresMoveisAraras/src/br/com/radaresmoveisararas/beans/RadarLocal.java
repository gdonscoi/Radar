package br.com.radaresmoveisararas.beans;

import org.androidannotations.annotations.EBean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@EBean
@DatabaseTable(tableName = "radarlocal")
public class RadarLocal {
	@DatabaseField(canBeNull = false, id = true)
	public int id;

	@DatabaseField(canBeNull = false)
	public String nomeRua;

	@DatabaseField(canBeNull = false)
	public String localizacaoInicio;

	@DatabaseField(canBeNull = false)
	public String localizacaoFim;

	@DatabaseField(canBeNull = false)
	public String descricao;
	
	@DatabaseField
    public String pontos;

}
