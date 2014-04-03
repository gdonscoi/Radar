package br.com.radaresmoveisararas.activity;

import mock.DataUnit;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.radaresmoveisararas.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MenuActivity extends FragmentActivity  {

	private SlidingPaneLayout mSlidingLayout;
	private ListView mList;
	private GoogleMap googleMap;
	private TextView descricaoMapa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
		mList = (ListView) findViewById(R.id.left_pane);
		mSlidingLayout.setPanelSlideListener(new SliderListener());
		mList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.list_menu, DataUnit.TITLES));
		mList.setOnItemClickListener(new ListItemClickListener());
		descricaoMapa = (TextView) findViewById(R.id.descricao_mapa);
//		getSupportActionBar().setTitle("SalsichApp");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(mSlidingLayout.isOpen() == false)
			mSlidingLayout.openPane();
		else
			mSlidingLayout.closePane();
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	private class SliderListener extends SlidingPaneLayout.SimplePanelSlideListener {
		@Override
		public void onPanelOpened(View panel) {
		//	mActionBar.onPanelOpened();
		}
		
		@Override
		public void onPanelClosed(View panel) {
		//	mActionBar.onPanelClosed();
		}
	}
	
	 private class ListItemClickListener implements ListView.OnItemClickListener {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				selectItem(position);
				mSlidingLayout.closePane();
			}
		}
	 
	 private void selectItem(int position) {
		 try{
			 
			 if(googleMap == null) {
				 googleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fmap)).getMap();
			 }else{
				 LatLng location;
				 switch (position){
				 case 0: 
					 location = new LatLng(-30.035662, -51.235472);
					 googleMap.addMarker(new MarkerOptions().position(location).title("Sua localização")); //podendo passar varios locations diferentes para o mesmo mapa
					 googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 50));
					 googleMap.animateCamera(CameraUpdateFactory.zoomTo(20),100,null);
					 descricaoMapa.setText("BLA BLA BLA 1");
					 break;
					 
				 case 1:
					 location = new LatLng(-22.3743608,-47.3697969);
					 googleMap.addMarker(new MarkerOptions().position(location).title("Sua localização")); //podendo passar varios locations diferentes para o mesmo mapa
					 googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 50));
					 googleMap.animateCamera(CameraUpdateFactory.zoomTo(20),100,null);
					 descricaoMapa.setText("TALZ TALZ  2");
					 break;
				 }
			 }
		 	
		 	
		 }catch(Exception e ){
			e.printStackTrace();
		 }
	    }
}
