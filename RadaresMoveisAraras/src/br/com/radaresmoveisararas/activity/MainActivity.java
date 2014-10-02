package br.com.radaresmoveisararas.activity;

import java.sql.SQLException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.web.client.RestClientException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;
import br.com.radaresmoveisararas.R;
import br.com.radaresmoveisararas.tasks.ControleVersao;
import br.com.radaresmoveisararas.util.Util;

@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

	@Bean
	ControleVersao controleVersao;
	
	@ViewById
	TextView msg;
	
	MenuAdapter menuAdapter;
	Context context; 
	
	private boolean flagIniciar = false;
	
	@AfterViews
	protected void iniciarClasse() {
		context = this;
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		iniciar(resultCode);
	}
	
	@Background
	public void iniciar(int resultCode){
		try{
			if(resultCode == ConnectionResult.SUCCESS){
				verificaAtualizacao(); 
				carregarRadares();
				finish();
			}else
				erroGooglePlayServices();
		}catch (RestClientException e) {
			exibirMsg("Verifique sua conexão com a internet.");
			atualizarMsg("Verifique sua conexão com a internet.");
		}catch (Exception e) {
			exibirMsg("Erro - " + e.getMessage());
			atualizarMsg("");
		}
	}
	
	//http://maps.googleapis.com/maps/api/directions/json?origin=-22.366442,-47.394140&destination=-22.367149,-47.391506&sensor=true&mode=driving


	
	@UiThread
	public void erroGooglePlayServices(){
		 atualizarMsg("Atualize o Google Play Services.");
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	 builder.setTitle("Google Play Services não encontrado.");
         builder.setMessage("Para utilizar o Radares Móveis Araras, faça o download do Google Play Services.")
                .setPositiveButton("Fazer Download", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.gms&hl=pt_BR");
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    	
                    }
                });
          builder.create().show();
	}
	
	public void carregarRadares() throws Exception{
		atualizarMsg("Carregando Radares.");
		menuAdapter = MenuAdapter.getInstance();
		menuAdapter.iniciar(context);
		MenuActivity_.intent(context).start();
	}
	
	public void verificaAtualizacao() throws RestClientException, NumberFormatException, SQLException{
		if(Util.verificaConexao(context)){
			atualizarMsg("Verificando por atualizações.");
			controleVersao.atualizar();
		}
	}
	
	@UiThread
	public void atualizarMsg(String mensagem){
		msg.setText(mensagem);
	}
	
	@UiThread
	public void exibirMsg(String mensagem){
		Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(flagIniciar)
			iniciarClasse();
		flagIniciar = true;
	}
	
	@Override
	public void onBackPressed() {
	}
}