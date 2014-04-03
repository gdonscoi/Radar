package br.com.radaresmoveisararas.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import br.com.radaresmoveisararas.beans.Radar;

public class MeuAdapter extends  BaseExpandableListAdapter implements Serializable {

	private static final long serialVersionUID = 1L;

	Context context;

	public static ArrayList<String> listaPai = new ArrayList<String>();
	
	
	public static ArrayList<ArrayList<String>> listafilho = new ArrayList<ArrayList<String>>() ;
	
	
	public static Radar[][] listaCoordenadas = null;
	
	
	public static HashMap<Integer,Radar> coordenadas;
	
	public MeuAdapter(ArrayList<Radar> radares){
		criaCoordenadas(radares);
	}
	
	public void createContext(Context context){
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listafilho.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		TextView textViewSubLista = new TextView(context);
		textViewSubLista.setText(listafilho.get(groupPosition).get(childPosition));
		textViewSubLista.setPadding(10, 5, 0, 5);
		textViewSubLista.setTextColor(Color.parseColor("#dcdcdc"));
		return textViewSubLista;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
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
		textViewCategorias.setText(listaPai.get(groupPosition));
		textViewCategorias.setPadding(30, 5, 0, 5);
		textViewCategorias.setTextSize(20);
		textViewCategorias.setTypeface(null, Typeface.BOLD);
		textViewCategorias.setTextColor(Color.parseColor("#bababa"));
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
	
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer,Radar> criaCoordenadas(ArrayList<Radar> radares){
		
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
		
		int[] semana = new int[7];
		for(Radar radar:radares){
			if(semana[radar.data] != 1){
				listaPai.set(radar.data,listaPai.get(radar.data) + ", " + radar.dia + "/" + radar.mes);
				semana[radar.data] = 1;
			}
		}
		
		listafilho.add(new ArrayList<String>());
		listafilho.add(new ArrayList<String>());
		listafilho.add(new ArrayList<String>());
		listafilho.add(new ArrayList<String>());
		listafilho.add(new ArrayList<String>());
		listafilho.add(new ArrayList<String>());
		listafilho.add(new ArrayList<String>());
		
		listaCoordenadas = new Radar[7][7];
		
		for (Radar radar : radares){
			listafilho.get(radar.data).add(radar.nomeRua);
			listaCoordenadas[radar.data][listafilho.get(radar.data).size()-1] = radar;
		}	
		
		HashMap<Integer,Radar> mapaCoordenadas = new HashMap<Integer,Radar>();
		
		for(int i = 0; i < listaPai.size(); i++){
			for(int k = 0; k < listafilho.get(i).size(); k++){
				mapaCoordenadas.put((i*10)+k,listaCoordenadas[i][k]);
			}
		}
		this.coordenadas=  mapaCoordenadas;
		return mapaCoordenadas;
	}

}
//http://lincolnminto.wordpress.com/tag/expandablelistview/