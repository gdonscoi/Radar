package br.com.radaresmoveisararas.tasks;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import br.com.radaresmoveisararas.activity.MainActivity;
import br.com.radaresmoveisararas.bd.ORMLiteHelper;
import br.com.radaresmoveisararas.beans.Radar;
import br.com.radaresmoveisararas.beans.RadarConversao;
import br.com.radaresmoveisararas.beans.RadarLocal;
import br.com.radaresmoveisararas.beans.RadarRadarLocal;
import br.com.radaresmoveisararas.beans.VersaoRadarLocal;
import br.com.radaresmoveisararas.beans.VersaoRadares;
import br.com.radaresmoveisararas.webservice.WebService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;

@EBean
public class ControleVersao {

	@Bean
	VersaoRadares versaoRadares;

	@Bean
	VersaoRadarLocal versaoRadarLocal;
	
	@Bean
	RadarLocal radarLocal;
	
	@Bean
	Radar radar;

	@RootContext
	protected MainActivity context;

	@RestService
	protected WebService webService;
	
	private Gson gson;

	private boolean isRadarLocalAtualizado(String versao)
			throws RestClientException, SQLException {
		Dao<VersaoRadarLocal, Integer> versaoRadarLocalDao = ORMLiteHelper.getInstance(context)
				.getVersaoRadarLocalBDDao();
		Where<VersaoRadarLocal, Integer> where = versaoRadarLocalDao.queryBuilder()
				.where().eq("versao", Integer.parseInt(versao));
		if (where.query().isEmpty())
			return false;
		return true;

	}

	private void atualizarRadarLocal(String versaoRadarLocal) throws NumberFormatException, SQLException,
			RestClientException {
		
		String  radaresConversaoString = webService.obterRadarLocal();
		
		Type listType = new TypeToken<List<RadarLocal>>() {}.getType();
		List<RadarLocal> radaresLocal = gson.fromJson(radaresConversaoString, listType);
		
		Dao<RadarLocal, Integer> radarLocalDao = ORMLiteHelper.getInstance(context).getRadarLocalDao();
		
		radarLocalDao.delete(radarLocalDao.queryForAll());
		for(RadarLocal radarLocal : radaresLocal){
			radarLocalDao.create(radarLocal);
		}
		
		Dao<VersaoRadarLocal, Integer> versaoRadarLocalDao = ORMLiteHelper.getInstance(context).getVersaoRadarLocalBDDao();
		versaoRadarLocalDao.delete(versaoRadarLocalDao.queryForAll());
		VersaoRadarLocal novaVersaoBD = new VersaoRadarLocal();
		novaVersaoBD.versao = Integer.parseInt(versaoRadarLocal);
		novaVersaoBD.dataUpdate = new Date();
		versaoRadarLocalDao.create(novaVersaoBD);
	}
	
	
	private boolean isRadarAtualizado(String versao)
			throws RestClientException, SQLException {
		Dao<VersaoRadares, Integer> versaoRadarDao = ORMLiteHelper.getInstance(context)
				.getVersaoRadarBDDao();
		Where<VersaoRadares, Integer> where = versaoRadarDao.queryBuilder()
				.where().eq("versao", Integer.parseInt(versao));
		if (versaoRadarDao.query(where.prepare()).isEmpty())
			return false;
		return true;

	}

	private void atualizarRadar(String versaoRadar) throws NumberFormatException, SQLException,
			RestClientException {
		
		String radaresConversaoString = webService.obterRadares();
		
		Type listType = new TypeToken<List<RadarConversao>>() {}.getType();
		List<RadarConversao> radaresConversao = gson.fromJson(radaresConversaoString, listType);
		
		Dao<Radar, Integer> radarDao = ORMLiteHelper.getInstance(context).getRadarDao();
		Dao<RadarRadarLocal, Integer> radarRadarLocalDao = ORMLiteHelper.getInstance(context).getRadarRadarLocalBDDao();
		
		radarDao.delete(radarDao.queryForAll());
		radarRadarLocalDao.delete(radarRadarLocalDao.queryForAll());
		
		for(RadarConversao radarConversao: radaresConversao){
			Radar radar = radarConversao.converterRadar();
			radarDao.create(radar);
			RadarRadarLocal radarRadarLocal = new RadarRadarLocal();
			radarRadarLocal.radar = radar;
			
			ArrayList<RadarLocal> radaresLocal = radarConversao.converterRadarLocal();
			if(radaresLocal.isEmpty())
				radarRadarLocalDao.create(radarRadarLocal);
			
			for(RadarLocal radarLocal :radaresLocal){
				radarRadarLocal.radarLocal = radarLocal;
				radarRadarLocalDao.create(radarRadarLocal);
			}
		}

		Dao<VersaoRadares, Integer> versaoRadarDao = ORMLiteHelper.getInstance(context).getVersaoRadarBDDao();
		versaoRadarDao.delete(versaoRadarDao.queryForAll());
		VersaoRadares novaVersaoBD = new VersaoRadares();
		novaVersaoBD.versao = Integer.parseInt(versaoRadar);
		novaVersaoBD.dataUpdate = new Date();
		versaoRadarDao.create(novaVersaoBD);
	}

	public void atualizar() throws RestClientException, NumberFormatException, SQLException {
			gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			
			String versaoRadarLocal = webService.obterVersaoRadarLocal().trim();
			if (!isRadarLocalAtualizado(versaoRadarLocal))
				atualizarRadarLocal(versaoRadarLocal);
		
			String versaoRadar = webService.obterVersaoRadares().trim();
			if (!isRadarAtualizado(versaoRadar))
				atualizarRadar(versaoRadar);
	}
}
