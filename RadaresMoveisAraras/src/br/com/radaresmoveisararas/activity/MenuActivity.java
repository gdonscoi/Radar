package br.com.radaresmoveisararas.activity;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;
import br.com.radaresmoveisararas.R;
import br.com.radaresmoveisararas.beans.RadarRadarLocal;
import br.com.radaresmoveisararas.util.Util;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_slide)
public class MenuActivity extends ActionBarActivity {

	private static ExpandableListView listView;
	private ActionBarDrawerToggle mDrawerToggle;
	private MenuAdapter menuAdapter;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	static GoogleMap mp;
	protected LocationManager locationManager;

	protected LatLng start;
	protected LatLng end;

	Context context;

	@ViewById(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;

	@ViewById(R.id.adMob)
	LinearLayout adMobLayout;

	@OptionsItem(R.id.action_settings)
	void clickTodosRadares() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);
		builder.setTitle(R.string.escolha).setItems(
				R.array.string_array_semana,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						try {
							ArrayList<RadarRadarLocal> radaresSemana = menuAdapter
									.getTodosRadaresSemana(which);
							if(radaresSemana != null){
								mp.clear();
								for(RadarRadarLocal radar:radaresSemana){
									start = criaLocating(radar.radarLocal.localizacaoInicio);
									end = criaLocating(radar.radarLocal.localizacaoFim);
	
									MarkerOptions marker = new MarkerOptions().position(start).title(
											radar.radarLocal.nomeRua);
									marker.icon(BitmapDescriptorFactory
											.defaultMarker(BitmapDescriptorFactory.HUE_RED));
									marker.snippet(radar.radarLocal.descricao);
									mp.addMarker(marker);
	
									MarkerOptions marker2 = new MarkerOptions().position(end).title(
											radar.radarLocal.nomeRua);
									marker2.icon(BitmapDescriptorFactory
											.defaultMarker(BitmapDescriptorFactory.HUE_RED));
									marker2.snippet(radar.radarLocal.descricao);
									mp.addMarker(marker2);
	
									mp.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 50));
									mp.animateCamera(CameraUpdateFactory.zoomTo(15), 100, null);
									mp.setMyLocationEnabled(true);
									mp.getUiSettings().isMyLocationButtonEnabled();
									mp.getUiSettings().setCompassEnabled(true);
									mDrawerLayout.closeDrawer(listView);
	
									PolylineOptions polyoptions = new PolylineOptions();
									polyoptions.color(Color.BLUE);
									polyoptions.width(4);
									polyoptions.addAll(Util.decode(radar.radarLocal.pontos));
									mp.addPolyline(polyoptions);
								}
							}
						} catch (Exception e) {
							exibirMsg("Erro ao exibir radares da semana.");
						}
					}
				});
		builder.create().show();
		;
	}

	@AfterViews
	protected void carregarInformacoes() {
		context = this;
		mTitle = getResources().getText(R.string.app_name);
		mDrawerTitle = getResources().getText(R.string.menu_descricao);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		menuAdapter = MenuAdapter.getInstance();
		listView = (ExpandableListView) findViewById(R.id.left_drawer);
		listView.setAdapter(menuAdapter);
		listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				selectItem(groupPosition, childPosition);
				return false;
			}
		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (mp == null) {
			mp = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.smap)).getMap();
		}
		mp.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-22.360826,
				-47.383250), 50));
		mp.animateCamera(CameraUpdateFactory.zoomTo(13), 100, null);
		mDrawerLayout.openDrawer(listView);

		AdView adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-7094070848163350/8607972025");
		adView.setAdSize(AdSize.BANNER);

		adMobLayout.addView(adView);
		AdRequest request = new AdRequest.Builder().build();
		adView.loadAd(request);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void selectItem(int pai, int filho) {
		mp.clear();
		RadarRadarLocal radar = (RadarRadarLocal) menuAdapter.getChild(pai,
				filho);
		if (radar.radarLocal == null)
			return;

		start = criaLocating(radar.radarLocal.localizacaoInicio);
		end = criaLocating(radar.radarLocal.localizacaoFim);

		MarkerOptions marker = new MarkerOptions().position(start).title(
				radar.radarLocal.nomeRua);
		marker.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		marker.snippet(radar.radarLocal.descricao);
		mp.addMarker(marker).showInfoWindow();

		MarkerOptions marker2 = new MarkerOptions().position(end).title(
				radar.radarLocal.nomeRua);
		marker2.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		marker2.snippet(radar.radarLocal.descricao);
		mp.addMarker(marker2);

		mp.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 50));
		mp.animateCamera(CameraUpdateFactory.zoomTo(15), 100, null);
		mp.setMyLocationEnabled(true);
		mp.getUiSettings().isMyLocationButtonEnabled();
		mp.getUiSettings().setCompassEnabled(true);
		mDrawerLayout.closeDrawer(listView);

		PolylineOptions polyoptions = new PolylineOptions();
		polyoptions.color(Color.BLUE);
		polyoptions.width(4);
		polyoptions.addAll(Util.decode(radar.radarLocal.pontos));
		mp.addPolyline(polyoptions);
	}

	public LatLng criaLocating(String local) {
		String[] posicoes = local.split(",");
		return new LatLng(Double.parseDouble(posicoes[0]),
				Double.parseDouble(posicoes[1]));
	}

	@UiThread
	public void exibirMsg(String mensagem) {
		Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setTitle("Sair")
				.setMessage("Deseja sair do aplicativo ?")
				.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									finalize();
									finish();
									menuAdapter = null;
									mp = null;
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}
						})
				.setNegativeButton("Não",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						}).show();
	}

}