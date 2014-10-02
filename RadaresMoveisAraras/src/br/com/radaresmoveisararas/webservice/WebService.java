package br.com.radaresmoveisararas.webservice;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Rest(rootUrl = "https://raw.githubusercontent.com/gdonscoi/Radar/master/", converters = { StringHttpMessageConverter.class ,GsonHttpMessageConverter.class})
public interface WebService extends RestClientErrorHandling {
	
	@Get("VersaoRadar")
	String obterVersaoRadares();
	
	@Get("Radar")
	String obterRadares();
	
	@Get("VersaoRadarLocal")
	String obterVersaoRadarLocal();
	
	@Get("RadarLocal")
	String obterRadarLocal();
}