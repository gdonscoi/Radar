package br.com.radaresmoveisararas.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "radarradarlocal")
public class RadarRadarLocal {
	
	@DatabaseField(generatedId = true)
	public int id;
	
	@DatabaseField(foreign = true,foreignAutoRefresh=true, columnName = "radar_semana")
	public Radar radar;
	
	@DatabaseField(foreign = true,foreignAutoRefresh=true, columnName = "radarlocal_id")
	public RadarLocal radarLocal;
}
