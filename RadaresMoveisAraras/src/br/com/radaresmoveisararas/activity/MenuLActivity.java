/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.radaresmoveisararas.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.TextView;
import br.com.radaresmoveisararas.R;
import br.com.radaresmoveisararas.util.Util;
import br.directions.route.Routing;
import br.directions.route.RoutingListener;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

	public class MenuLActivity extends ActionBarActivity  implements RoutingListener{
    private static DrawerLayout mDrawerLayout;
    private static ExpandableListView listView;
    private ActionBarDrawerToggle mDrawerToggle;
    private MeuAdapter meuAdapter;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    static GoogleMap mp;
    static TextView descricaoMapa;
    private LocationRequest requestLocation;
	protected LocationManager locationManager;
	 
    protected LatLng start;
    protected LatLng end;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_slide);
        descricaoMapa = (TextView) findViewById(R.id.descricao_mapa);
        mTitle = getResources().getText(R.string.app_name);
        mDrawerTitle = getResources().getText(R.string.menu_descricao);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        Intent intent = getIntent();
        meuAdapter = (MeuAdapter) intent.getSerializableExtra("Radares");
        
        meuAdapter.createContext(this);
        listView = (ExpandableListView) findViewById(R.id.left_drawer);
        listView.setAdapter(meuAdapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            	meuAdapter.listafilho.get(groupPosition).get(childPosition).toString();
            	selectItem(groupPosition,childPosition);
				return false;
               
            }
        });
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
            	getSupportActionBar().setTitle(mTitle);
            	supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
            	getSupportActionBar().setTitle(mDrawerTitle);
            	supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (mp == null){
    		mp = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.smap)).getMap();
    	}
        requestLocation = LocationRequest.create();
        requestLocation.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        requestLocation.setInterval(100);
        
        mp.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-22.360826, -47.383250), 50));
        mp.animateCamera(CameraUpdateFactory.zoomTo(13),100,null);
        descricaoMapa.setText("Use o menu ao lado esquerdo para localizar os radares");
        mDrawerLayout.openDrawer(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()) {
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public  void selectItem(int pai,int filho) {
    	if(verificaConexaoWifi() == true){
    		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
    			new AlertDialog.Builder(this)
    	        .setTitle("Aivar GPS")
    	        .setMessage("Entre em opções para ativar o GPS.")
    	        .setPositiveButton("Opções", new DialogInterface.OnClickListener() {
    	            public void onClick(DialogInterface dialog, int which) { 
    	            	if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
    	            		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    	            		MenuLActivity.this.startActivity(intent);
    	            	}
    	            }
    	         }).show();
    		}else{
    			if(Util.verificaConexao(this)){
    		    	int position = pai*10+filho;
    			    descricaoMapa.setText(meuAdapter.coordenadas.get(position).detalhes());
    			    descricaoMapa.setVisibility(View.INVISIBLE);
    		        mp.clear();
    		       
    		        start = criaLocatingInicio(position);
    			    end = criaLocatingFim(position);
    		
    			    MarkerOptions marker = new MarkerOptions().position(start).title("Extremidade A.");
    			    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    			    mp.addMarker(marker);
    			    
    			    MarkerOptions marker2 = new MarkerOptions().position(end).title("Extremidade B.");
    			    marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    			    mp.addMarker(marker2);
    		        
    		        mp.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 50));
    		        mp.animateCamera(CameraUpdateFactory.zoomTo(15),100,null);
    		        mp.setMyLocationEnabled(true);
    		        mp.getUiSettings().isMyLocationButtonEnabled();
    		        mp.getUiSettings().setCompassEnabled(true);
    		        mDrawerLayout.closeDrawer(listView);
    		        
    		        Routing routing = new Routing(Routing.TravelMode.DRIVING);
    		        routing.registerListener(this);
    		        routing.execute(start, end);
    			}else{
    				int position = pai*10+filho;
    				descricaoMapa.setText(meuAdapter.coordenadas.get(position).detalhes());
    				descricaoMapa.setVisibility(View.VISIBLE);
    			    mp.clear();
    			        
    			    start = criaLocatingInicio(position);
    			    end = criaLocatingFim(position);
    			
    			    MarkerOptions marker = new MarkerOptions().position(start).title("Extremidade A da rua.");
    			    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    			    mp.addMarker(marker);
    			    
    			    MarkerOptions marker2 = new MarkerOptions().position(end).title("Extremidade B da rua.");
    			    marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    			    mp.addMarker(marker2);
    			        
    		        mp.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 50));
    		        mp.animateCamera(CameraUpdateFactory.zoomTo(15),100,null);
    		        mp.setMyLocationEnabled(true);
    		        mp.getUiSettings().isMyLocationButtonEnabled();
    		        mp.getUiSettings().setCompassEnabled(true);
    		        mDrawerLayout.closeDrawer(listView);
    			}
    		}    		
    		
    	}else{
    		new AlertDialog.Builder(this)
	        .setTitle("Aivar Wifi")
	        .setMessage("Ative o Wifi para obter uma localização mais precisa. (mesmo não conectado em nenhuma rede)")
	        .setPositiveButton("Opções", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) { 
	            	if(verificaConexaoWifi() != true){
	            		Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
	            		MenuLActivity.this.startActivity(intent);
	            	}
	            }
	         }).show();
    	}
    }
    
    public LatLng criaLocatingInicio(int posicao){
    	String[] posicoes = meuAdapter.coordenadas.get(posicao).localizacaoInicio.split(",");
    	return new LatLng(Double.parseDouble(posicoes[0]) , Double.parseDouble(posicoes[1]));
    }

    public LatLng criaLocatingFim(int posicao){
    	String[] posicoes = meuAdapter.coordenadas.get(posicao).localizacaoFim.split(",");
    	return new LatLng(Double.parseDouble(posicoes[0]) , Double.parseDouble(posicoes[1]));
    }
    

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	@Override
	public void onRoutingFailure() {
		descricaoMapa.setVisibility(View.VISIBLE);
		   descricaoMapa.setText("Falha na rede, tente novamente.");
		   setSupportProgressBarIndeterminateVisibility(false);
		
	}

	@Override
	public void onRoutingStart() {
		setSupportProgressBarIndeterminateVisibility(true);
		
	}

	@Override
	public void onRoutingSuccess(PolylineOptions mPolyOptions) {
		  PolylineOptions polyoptions = new PolylineOptions();
	      polyoptions.color(Color.BLUE);
	      polyoptions.width(4);
	      polyoptions.addAll(mPolyOptions.getPoints());
	      mp.addPolyline(polyoptions);
	      descricaoMapa.setVisibility(View.VISIBLE);
	      setSupportProgressBarIndeterminateVisibility(false);
		
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
        .setTitle("Sair")
        .setMessage("Deseja sair do aplicativo ?")
        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	try {
            		finalize();
            		finish();
            		meuAdapter = null;
            		mp = null;
				} catch (Throwable e) {
					e.printStackTrace();
				}
            }
         })
        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	return;
            }
         })
         .show();
	}
	
	public  boolean verificaConexaoWifi() {  
		WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()==true) {
          return true;
        } else {
        	return false;
        }
	}  
}