package br.com.radaresmoveisararas.beans;

import java.util.Date;

import org.androidannotations.annotations.EBean;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@EBean
@DatabaseTable(tableName = "versaoradares")
public class VersaoRadares {

	public VersaoRadares(){}
	
	@DatabaseField(generatedId = true)
	public int id;
	
    @SerializedName("versao")
	@DatabaseField(canBeNull = false)
	public int versao;

	@DatabaseField(canBeNull = false)
	public Date dataUpdate;
	
}
