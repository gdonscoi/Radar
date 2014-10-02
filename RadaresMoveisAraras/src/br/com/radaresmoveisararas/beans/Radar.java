package br.com.radaresmoveisararas.beans;

import java.util.Date;

import org.androidannotations.annotations.EBean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@EBean
@DatabaseTable(tableName = "radares")
public class Radar {
	
	public Radar(){}
	
	@DatabaseField(canBeNull = false,id = true)
	public int semana;
	
	@DatabaseField(canBeNull = false)
	public Date data;
	
}
