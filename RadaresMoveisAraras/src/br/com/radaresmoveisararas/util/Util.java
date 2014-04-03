package br.com.radaresmoveisararas.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class Util {

	public static  boolean verificaConexao(Context context) {
	    boolean conectado;  
	    ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    if (conectivtyManager.getActiveNetworkInfo() != null  
	            && conectivtyManager.getActiveNetworkInfo().isAvailable()  
	            && conectivtyManager.getActiveNetworkInfo().isConnected()) {  
	        conectado = true;  
	    } else {  
	        conectado = false;  
	    }  
	    return conectado;  
	} 
}
