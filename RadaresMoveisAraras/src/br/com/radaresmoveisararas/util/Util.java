package br.com.radaresmoveisararas.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.android.gms.maps.model.LatLng;

public class Util {

	public static boolean verificaConexao(Context context) {
		boolean conectado;
		ConnectivityManager conectivtyManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conectivtyManager.getActiveNetworkInfo() != null
				&& conectivtyManager.getActiveNetworkInfo().isAvailable()
				&& conectivtyManager.getActiveNetworkInfo().isConnected()) {
			conectado = true;
		} else {
			conectado = false;
		}
		return conectado;
	}

	public static String formatDate(Date data) {
		SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM",
				java.util.Locale.getDefault());
		return dt1.format(data);
	}
	
	 public static List<LatLng> decode(final String encodedPath) {
		 List<LatLng> track = new ArrayList<LatLng>();
		    int index = 0;
		    int lat = 0, lng = 0;

		    while (index < encodedPath.length()) {
		        int b = 0, shift = 0, result = 0;
		        do {
		        	if(index < encodedPath.length()){
			            b = encodedPath.charAt(index++) - 63;
			            result |= (b & 0x1f) << shift;
			            shift += 5;
		        	}
		        } while (b >= 0x20);
		        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		        lat += dlat;

		        shift = 0;
		        result = 0;
		        do {
		        	if(index < encodedPath.length()){
		        		b = encodedPath.charAt(index++) - 63; // Error at this line.
		            	result |= (b & 0x1f) << shift;
		            	shift += 5;
		        	}
		        } while (b >= 0x20);
		        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		        lng += dlng;

		        LatLng p = new LatLng( (double)lat/1E5, (double)lng/1E5 );
		        track.add(p);
		    }
		    return track;
		}
	 
	 public static String encode(final List<LatLng> path) {
	        long lastLat = 0;
	        long lastLng = 0;

	        final StringBuffer result = new StringBuffer();

	        for (final LatLng point : path) {
	            long lat = Math.round(point.latitude * 1e5);
	            long lng = Math.round(point.longitude * 1e5);

	            long dLat = lat - lastLat;
	            long dLng = lng - lastLng;

	            encode(dLat, result);
	            encode(dLng, result);

	            lastLat = lat;
	            lastLng = lng;
	        }
	        return result.toString();
	    }

	    private static void encode(long v, StringBuffer result) {
	        v = v < 0 ? ~(v << 1) : v << 1;
	        while (v >= 0x20) {
	            result.append(Character.toChars((int) ((0x20 | (v & 0x1f)) + 63)));
	            v >>= 5;
	        }
	        result.append(Character.toChars((int) (v + 63)));
	    }
}
