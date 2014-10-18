package br.com.radaresmoveisararas.activity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.radaresmoveisararas.bd.ORMLiteHelper;
import br.com.radaresmoveisararas.beans.Radar;
import br.com.radaresmoveisararas.beans.RadarRadarLocal;
import br.com.radaresmoveisararas.util.Util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;

public class MenuAdapter extends  BaseExpandableListAdapter implements Serializable {

	private static final long serialVersionUID = 1L;

	Context context;
	
	private ArrayList<String> listaPai = new ArrayList<String>();
	
	private ArrayList<ArrayList<RadarRadarLocal>> listafilho = new ArrayList<ArrayList<RadarRadarLocal>>() ;
	
	public static Radar[][] listaCoordenadas = null;
	
	public static HashMap<Integer,Radar> coordenadas;
	
	private static MenuAdapter instance;
	
	private MenuAdapter(){
	}
	
	public static MenuAdapter getInstance(){
		if(instance == null)
			instance = new MenuAdapter();
		return instance;
	}
	
	public void iniciar(Context context) throws Exception{
		this.context = context;
		criaCoordenadas();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listafilho.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(listafilho.get(groupPosition).get(childPosition).radarLocal == null){
			TextView textViewSubListaNome = new TextView(context);
			textViewSubListaNome.setText("Não possui radar essa semana.");
			textViewSubListaNome.setPadding(35, 6, 0, 0);
			textViewSubListaNome.setTextColor(Color.parseColor("#1C1C1C"));
			return textViewSubListaNome;
		}
		
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		TextView textViewSubListaNome = new TextView(context);
		textViewSubListaNome.setText(listafilho.get(groupPosition).get(childPosition).radarLocal.nomeRua);
		textViewSubListaNome.setPadding(35, 6, 0, 0);
		textViewSubListaNome.setTextColor(Color.parseColor("#1C1C1C"));
		
		TextView textViewSubListaDescricao = new TextView(context);
		textViewSubListaDescricao.setText(listafilho.get(groupPosition).get(childPosition).radarLocal.descricao);
		textViewSubListaDescricao.setPadding(35, 0, 0, 6);
		textViewSubListaDescricao.setTextColor(Color.parseColor("#1C1C1C"));
		
		linearLayout.addView(textViewSubListaNome);
		linearLayout.addView(textViewSubListaDescricao);
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		layoutParams.height =1;
		layoutParams.setMargins(20,0,20,0);
		LinearLayout linearLayoutBorda = new LinearLayout(context);
		linearLayoutBorda.setLayoutParams(layoutParams);
		linearLayoutBorda.setOrientation(LinearLayout.VERTICAL);
		RectShape rect = new RectShape();
		ShapeDrawable rectShapeDrawable = new ShapeDrawable(rect);
		Paint paint = rectShapeDrawable.getPaint();
		paint.setColor(Color.GRAY);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(4);
		linearLayoutBorda.setBackgroundDrawable(rectShapeDrawable);
		linearLayout.addView(linearLayoutBorda);
		
		return linearLayout;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return listafilho.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return listaPai.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return listaPai.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		TextView textViewCategorias = new TextView(context);
		textViewCategorias.setText(listaPai.get(groupPosition) + "-" + Util.formatDate(listafilho.get(groupPosition).get(0).radar.data));
		textViewCategorias.setPadding(50, 10, 0, 5);
		textViewCategorias.setTextSize(20);
		textViewCategorias.setTypeface(null, Typeface.BOLD);
		textViewCategorias.setTextColor(Color.parseColor("#000000"));
		return textViewCategorias;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public void teste(){
		this.listaPai.size();
	}
	
	
	@SuppressLint("UseSparseArrays")
	public void criaCoordenadas() throws Exception {
		try{
			Dao<RadarRadarLocal, Integer> radarRadarLocalDao = ORMLiteHelper.getInstance(context).getRadarRadarLocalBDDao();
			
			if(radarRadarLocalDao.countOf() <= 0)
				throw new SQLException("Estabelaça uma conexão com a internet para atualizar o aplicativo.");
			
			listaPai.clear();
			listafilho.clear();
			listaCoordenadas = null;
			
			listaPai.add("Domingo");
			listaPai.add("Segunda-feira");
			listaPai.add("Terça-feira");
			listaPai.add("Quarta-feira");
			listaPai.add("Quinta-feira");
			listaPai.add("Sexta-feira");
			listaPai.add("Sábado");
		
			for(int i=0;i<=7;i++){
				Where<RadarRadarLocal, Integer> where = radarRadarLocalDao.queryBuilder()
						.where().eq("radar_semana", i);
				ArrayList<RadarRadarLocal> radarRadarLocals = new ArrayList<RadarRadarLocal>(radarRadarLocalDao.query(where.prepare()));
				listafilho.add(radarRadarLocals);
			}
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	
	public ArrayList<RadarRadarLocal> getTodosRadaresSemana(int idSemana) throws SQLException{
		Dao<RadarRadarLocal, Integer> radarRadarLocalDao = ORMLiteHelper.getInstance(context).getRadarRadarLocalBDDao();
		
		Where<RadarRadarLocal, Integer> where = radarRadarLocalDao.queryBuilder()
				.where().eq("radar_semana", idSemana);
		return new ArrayList<RadarRadarLocal>(radarRadarLocalDao.query(where.prepare()));
	}

}
