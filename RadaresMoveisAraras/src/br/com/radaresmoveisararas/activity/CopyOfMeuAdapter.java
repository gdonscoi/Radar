//package br.com.radaresmoveisararas.activity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//import mock.DataUnit;
//
//import br.com.radaresmoveisararas.beans.Radar;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import android.content.Context;
//import android.graphics.Typeface;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class CopyOfMeuAdapter extends  BaseExpandableListAdapter {
//
//	Context context;
//
//	// Definindo o conteúdo de nossa lista e sub-lista
//
//	public static String[] listaPai = null;
//	
//	
//	public static String[][] listafilho = null ;
//	
//	
//	public static Radar[][] listaCoordenadas = null;
//	
//	
//	public static HashMap<Integer,Radar> coordenadas;
//	
//	
//	public CopyOfMeuAdapter(Context context) {
//		this.context = context;
//		coordenadas = criaCoordenadas();
//	}
//
//	@Override
//	public Object getChild(int groupPosition, int childPosition) {
//		// TODO Auto-generated method stub
//		return listafilho[groupPosition][childPosition];
//	}
//
//	@Override
//	public long getChildId(int groupPosition, int childPosition) {
//		// TODO Auto-generated method stub
//		return childPosition;
//	}
//
//	@Override
//	public View getChildView(int groupPosition, int childPosition,
//			boolean isLastChild, View convertView, ViewGroup parent) {
//
//		// Criamos um TextView que conterá as informações da listafilho que
//		// criamos
//		TextView textViewSubLista = new TextView(context);
//		textViewSubLista.setText(listafilho[groupPosition][childPosition]);
//		// Definimos um alinhamento
//		textViewSubLista.setPadding(10, 5, 0, 5);
//		
//		return textViewSubLista;
//	}
//
//	@Override
//	public int getChildrenCount(int groupPosition) {
//		// TODO Auto-generated method stub
//		return listafilho[groupPosition].length;
//	}
//
//	@Override
//	public Object getGroup(int groupPosition) {
//		// TODO Auto-generated method stub
//		return listaPai[groupPosition];
//	}
//
//	@Override
//	public int getGroupCount() {
//		// TODO Auto-generated method stub
//		return listaPai.length;
//	}
//
//	@Override
//	public long getGroupId(int groupPosition) {
//		// TODO Auto-generated method stub
//		return groupPosition;
//	}
//
//	@Override
//	public View getGroupView(int groupPosition, boolean isExpanded,
//			View convertView, ViewGroup parent) {
//
//		// Criamos um TextView que conterá as informações da listaPai que
//		// criamos
//		TextView textViewCategorias = new TextView(context);
//		textViewCategorias.setText(listaPai[groupPosition]);
//		// Definimos um alinhamento
//		textViewCategorias.setPadding(30, 5, 0, 5);
//		// Definimos o tamanho do texto
//		textViewCategorias.setTextSize(20);
//		// Definimos que o texto estará em negrito
//		textViewCategorias.setTypeface(null, Typeface.BOLD);
//
//		return textViewCategorias;
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isChildSelectable(int groupPosition, int childPosition) {
//		// Defina o return como sendo true se vc desejar que sua sub-lista seja selecionável
//		return true;
//	}
//	
//	private HashMap<Integer,Radar> criaCoordenadas(){
//		
//		List<Radar> radares = new ArrayList<Radar>();
//		
//		Radar radar1 = new Radar();
//		radar1.nomeRua = "Av. Loreto";
//		radar1.descricao = "limite 40 km/h";
//		radar1.localizacao = "-22.360604, -47.366046";
//		radar1.data = 0;
//		radar1.dia = 23;
//		radar1.dia = 2;
//		
//		Radar radar8 = new Radar();
//		radar8.nomeRua = "Av. Fábio da Silva Prado";
//		radar8.descricao = "limite 60 km/h";
//		radar8.localizacao = "-22.346190, -47.373753";
//		radar8.data = 0;
//		radar8.dia = 23;
//		radar8.dia = 2;
//		
//		Radar radar2 = new Radar();
//		radar2.nomeRua = "Av. Dona Renata";
//		radar2.descricao = "limite 50 km/h";
//		radar2.localizacao = "-22.360535, -47.391250";
//		radar2.data = 1;
//		radar2.dia = 24;
//		radar1.dia = 2;
//		
//		Radar radar3 = new Radar();
//		radar3.nomeRua = "Rua dos Coroados";
//		radar3.descricao = "limite 50 km/h";
//		radar3.localizacao = "-22.360580, -47.373594";
//		radar3.data = 2;
//		radar3.dia = 25;
//		radar1.dia = 2;
//		
//		Radar radar4 = new Radar();
//		radar4.nomeRua = "Av. Maria Aparecida Muniz Michelin";
//		radar4.descricao = "limite 60 km/h";
//		radar4.localizacao = "-22.343338, -47.385204";
//		radar4.data = 3;
//		radar4.dia = 26;
//		radar1.dia = 2;
//		
//		Radar radar5 = new Radar();
//		radar5.nomeRua = "Av. Fábio da Silva Prado";
//		radar5.descricao = "limite 60 km/h";
//		radar5.localizacao = "-22.346190, -47.373753";
//		radar5.data = 4;
//		radar5.dia = 27;
//		radar1.dia = 2;
//		
//		Radar radar6 = new Radar();
//		radar6.nomeRua = "Rua Emilio Pacagnella";
//		radar6.descricao = "limite 50 km/h";
//		radar6.localizacao = "-22.330505, -47.382936";
//		radar6.data = 5;
//		radar6.dia = 28;
//		radar1.dia = 2;
//		
//		Radar radar7 = new Radar();
//		radar7.nomeRua = "Estrada Cascata";
//		radar7.descricao = "limite 80 km/h";
//		radar7.localizacao = "-22.286513, -47.290798";
//		radar7.data = 6;
//		radar7.dia = 1;
//		radar1.dia = 3;
//		
//		radares.add(radar1);
//		radares.add(radar8);
//		radares.add(radar2);
//		radares.add(radar3);
//		radares.add(radar4);
//		radares.add(radar5);
//		radares.add(radar6);
//		radares.add(radar7);
//		
//		
//		
//		listaPai = new String[] { "Domingo","Segunda-feira", "Terça-feira", "Quarta-feira" ,"Quinta-feira","Sexta-feira", "Sábado" };
//		
////		listafilho = new  String[][] { { "Av. Fábio da Silva Prado" , "Av. José Pavan" },
////				{ "Av. Pedro Mello" }, { "Av. Padre Atílio" } };
//		
////		listaCoordenadas = new String[][] { { "-22.346107,-47.373862" , "-22.362519,-47.354625" },
////				{ "-22.359205,-47.365215" }, { "-22.363894,-47.387371" } };
//		
//		listaCoordenadas = new Radar[7][7];
//		listafilho = new  String[7][];
//		
//		int cont;
//		for (Radar radar : radares){
//			cont = 0;
//			if(listafilho[radar.data] == null)
//				listafilho[radar.data] = new String[1];
//			while (listafilho[radar.data][cont] != null){
//				cont++;
//			}
//			listafilho[radar.data][cont] = radar.nomeRua;
//			listaCoordenadas[radar.data][cont] = radar;
//		}	
//		
//		HashMap<Integer,Radar> mapaCoordenadas = new HashMap<Integer,Radar>();
//		
//		for(int i = 0; i < listaPai.length; i++){
//			for(int k = 0; k < listafilho[i].length; k++){
//				mapaCoordenadas.put((i*10)+k,listaCoordenadas[i][k]);
//			}			
//		}
//		return mapaCoordenadas;
//	}
//	
//}
////http://lincolnminto.wordpress.com/tag/expandablelistview/