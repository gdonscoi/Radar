package br.com.radaresmoveisararas.activity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import br.com.radaresmoveisararas.R;
import br.com.radaresmoveisararas.SobreActivity;
import br.com.radaresmoveisararas.bd.ORMLiteHelper;
import br.com.radaresmoveisararas.beans.Radar;
import br.com.radaresmoveisararas.beans.VersaoBD;
import br.com.radaresmoveisararas.util.Util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;

public class MainActivity extends ActionBarActivity{

	 protected LocationManager locationManager;
	 private MeuAdapter meuAdapter;
	 private Button entrar;
	 private Dao<Radar,Integer> radarDao;
	 private Dao<VersaoBD,Integer> versaoBDDao;
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		entrar = (Button) findViewById(R.id.btn_entrar);
	}

	public void onLoginClicked(View v) {
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			new AlertDialog.Builder(this)
	        .setTitle("Aivar GPS")
	        .setMessage("Entre em opções para ativar o GPS.")
	        .setPositiveButton("Opções", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) { 
	            	if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	            		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	            		MainActivity.this.startActivity(intent);
	            	}else{
	            		ChamadaWebService chamadaWebService = new ChamadaWebService(MainActivity.this);
	            		chamadaWebService.execute(0,0,0);
	            	}
	            }
	         })
	         .show();
		}else{
			ChamadaWebService chamadaWebService = new ChamadaWebService(MainActivity.this);
    		chamadaWebService.execute(0,0,0);
		}
		
		
	}
	
	public void onSobreClicked(View v) {
		Intent intent = new Intent(getApplicationContext(),SobreActivity.class);
		MainActivity.this.startActivity(intent);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
	

	 public class ChamadaWebService extends AsyncTask<Integer, String, String> {
		 
	        private Context context;
	        
	        public ChamadaWebService(Context context) {
	            this.context = context;
	        }
	        
	        @Override
	        protected void onPreExecute() {
	        	setSupportProgressBarIndeterminateVisibility(true);
	        	entrar.setEnabled(false);
	        }
	 
	        @SuppressWarnings("unchecked")
			@Override
	        protected String doInBackground(Integer... paramss) {
	        	
	        	try {
	        		radarDao = ORMLiteHelper.getInstance(getApplicationContext()).getRadarDao();
	    			versaoBDDao = ORMLiteHelper.getInstance(getApplicationContext()).getVersaoBDDao();
	    			
	        		if(Util.verificaConexao(context)){
	        			WebServiceCall webService = WebServiceCall.getInstance();
	        			String[] respostaVersao = webService.get("https://raw.githubusercontent.com/gdonscoi/Radar/master/versaobd");
	        			
	        			if(!respostaVersao[0].equals("200"))
	        				throw new Exception("Erro ao obter Radares.");
	        			
	        			Where<VersaoBD, Integer> where  = versaoBDDao.queryBuilder().where().eq("versao", Integer.parseInt(respostaVersao[1].trim()));
    					ArrayList<VersaoBD> versaoBD = new ArrayList<VersaoBD>(where.query());
	        			
	        			if(versaoBD.isEmpty()){
	        				versaoBDDao.delete(versaoBDDao.queryForAll());
	        				VersaoBD novaVersaoBD = new VersaoBD();
	        				novaVersaoBD.versao = Integer.parseInt(respostaVersao[1].trim());
	        				novaVersaoBD.dataUpdate = new Date();
	        				versaoBDDao.create(novaVersaoBD);
	        				
	        				String[] resposta = webService.get("https://raw.githubusercontent.com/gdonscoi/Radar/master/radares");
			        		
			        		if(!resposta[0].equals("200"))
			    				throw new Exception("Erro ao obter Radares.");
			        		
			        		java.lang.reflect.Type listType = new TypeToken<List<Radar>>() {}.getType();
			        		ArrayList<Radar> radares = (ArrayList<Radar>) new Gson().fromJson( resposta[1].trim() , listType);
			        		
			        		radarDao.delete(radarDao.queryForAll());
			        		for(Radar radar:radares){
			        			radarDao.create(radar);
			        		}
	        			}
	        		}
	        		ArrayList<Radar> radaresBanco = new ArrayList<Radar>(radarDao.queryForAll());
	        		
	        		if(radaresBanco.isEmpty()){
	        			return "Você precisa estar na internet para atualizar os dados.";
	        		}
	        		meuAdapter = new MeuAdapter(radaresBanco);
	        	} catch (SQLException e) {
	    			Log.e("ERRO BANCO DE DADOS",e.getMessage());	
	    			return "ERRO Banco de Dados";
				} catch (Exception e) {
					return "ERRO";
				}
	        	return "sucesso";
	        }
	        
	        
	        @Override
	        protected void onPostExecute(String result) {
	        	if(result.equals("sucesso")){
		        	setSupportProgressBarIndeterminateVisibility(false);
		        	Intent in = new Intent(getApplicationContext(), MenuLActivity.class);
		        	in.putExtra("finish", true);
		        	in.putExtra("Radares",(Serializable)meuAdapter);
		    		in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 			startActivity(in);
		 			finish();
	        	}else{
	        		Toast.makeText(context, result + " - Não foi possível entrar no aplicativo.", Toast.LENGTH_LONG).show();
	        		setSupportProgressBarIndeterminateVisibility(false);
	        		entrar.setEnabled(true);
	        	}
	        }
	 
	    }
	 
	 @Override
		public void onBackPressed() {
			new AlertDialog.Builder(this)
	        .setTitle("Sair")
	        .setMessage("Deseja sair do aplicativo ?")
	        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) { 
	            	finish();
	            }
	         })
	        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) { 
	            	return;
	            }
	         })
	         .show();
		}
}
