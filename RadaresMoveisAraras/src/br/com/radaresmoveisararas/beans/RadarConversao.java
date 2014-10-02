package br.com.radaresmoveisararas.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class RadarConversao implements Serializable {

	private static final long serialVersionUID = 1L;

	public ArrayList<Integer> radarLocal;

	public int semana;
	
	public Date data;

	public Radar converterRadar() throws SQLException{
		Radar radar = new Radar();
		radar.semana = this.semana;
		radar.data = this.data;
		return radar;
	}
	
	public ArrayList<RadarLocal> converterRadarLocal() throws SQLException{
		ArrayList<RadarLocal> radaresLocais = new ArrayList<RadarLocal>();
		for(Integer id : this.radarLocal){
			RadarLocal radarLocal = new RadarLocal();
			radarLocal.id = id.intValue();
			radaresLocais.add(radarLocal);
		}
		return radaresLocais;
	}
}
