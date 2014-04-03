package br.com.radaresmoveisararas.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;
/**
 *   Copyright 2013 Gerson Donscoi Junior, Leandro Motta M. Oliveira
 * 
 *   Este arquivo é parte do programa SchoolNet Mobile
 *
 *
 *   SchoolNet Mobile é um software livre; você pode redistribuí-lo e/ou 
 *
 *   modificá-lo dentro dos termos da Licença Pública Geral GNU como 
 *
 *   publicada pela Fundação do Software Livre (FSF); na versão 2 da 
 *
 *   Licença, ou (na sua opinião) qualquer versão.
 *
 *
 *
 *   Este programa é distribuído na esperança de que possa ser  útil, 
 *
 *   mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer
 *
 *   MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a
 *
 *   Licença Pública Geral GNU para maiores detalhes.
 *
 *
 *
 *   Você deve ter recebido uma cópia da Licença Pública Geral GNU
 *
 *   junto com este programa, se não, escreva para a Fundação do Software
 *
 *   Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
public class WebServiceCall {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	public String[] result;
	private static final int JSON_CONNECTION_TIMEOUT = 10000;
	private static final int JSON_SOCKET_TIMEOUT = 10000;
	private DefaultHttpClient httpclient;
	private BasicHttpParams httpParameters;
	private static WebServiceCall instance;
	private Properties props;
	
	private WebServiceCall(){
//		loadProperties();
		httpParameters = new BasicHttpParams();
		HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParameters, HTTP.UTF_8);
		setTimeOut(httpParameters);
		httpclient = new DefaultHttpClient(httpParameters);
	}
	
	public static WebServiceCall getInstance(){
		if(instance == null)
			instance = new WebServiceCall();
		return instance;
	}
	
	public void destroyInstance(){
		instance = null;
	}
	
	private void setTimeOut(HttpParams params) {
		HttpConnectionParams.setConnectionTimeout(params,
				JSON_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, JSON_SOCKET_TIMEOUT);
	}

	
	
	public final String[] get(String url) throws Exception {
		String[] result = new String[2];
		try {

			HttpGet httpGet = new HttpGet(new URI(url));
			httpGet.setHeader("Content-type", "application/json");
			httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
			
			HttpResponse response;
			response = httpclient.execute(httpGet);
			Header[] headers = response.getAllHeaders();
			this.exibeHeaders(headers);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				result[0] = String.valueOf(response.getStatusLine().getStatusCode());
				InputStream instream = entity.getContent();
				result[1] = toString(instream);
				instream.close();
			}
			return result;
			
		} catch (ConnectTimeoutException e) {
			throw new ConnectTimeoutException("Obteve o tempo limite de conexão.");

		} catch (Exception e) {
			throw new ConnectTimeoutException("Falha ao acessar o Web Service.");
		}

	}
	
	private String toString(InputStream is) throws IOException {

		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0) {
			baos.write(bytes, 0, lidos);
		}
		return new String(baos.toByteArray());
	}

	private void exibeHeaders(Header[] headers) {
		for (Header header : headers) {
			Log.d("Key : ", header.getName() + header.getValue());
		}
	}
	
	private void loadProperties(){
		try {
			this.props=new Properties();
			InputStream inputStream = 
			this.getClass().getClassLoader().getResourceAsStream("config/webservice.properties");
			props.load(inputStream);
		} catch (IOException e) {
			Log.e("ERRO AO CARREGAR PROPERTIES",e.getMessage());
		}
	}
}
