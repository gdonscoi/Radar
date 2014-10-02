package br.com.radaresmoveisararas.beans;

import java.util.Date;

import org.androidannotations.annotations.EBean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@EBean
@DatabaseTable(tableName = "versaoradarlocal")
public class VersaoRadarLocal{

	public VersaoRadarLocal(){}
	
	@DatabaseField(generatedId = true)
	public int id;
	
	@DatabaseField(canBeNull = false)
	public int versao;

	@DatabaseField(canBeNull = false)
	public Date dataUpdate;
}
