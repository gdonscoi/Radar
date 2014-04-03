package br.com.radaresmoveisararas.activity;

import br.com.radaresmoveisararas.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragmentV2 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance ){
		return inflater.inflate(R.layout.map_fragment, container, false);
	}
}
