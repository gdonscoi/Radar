//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package br.com.radaresmoveisararas.tasks;

import android.content.Context;
import android.util.Log;
import br.com.radaresmoveisararas.activity.MainActivity;
import br.com.radaresmoveisararas.beans.RadarLocal_;
import br.com.radaresmoveisararas.beans.Radar_;
import br.com.radaresmoveisararas.beans.VersaoRadarLocal_;
import br.com.radaresmoveisararas.beans.VersaoRadares_;
import br.com.radaresmoveisararas.webservice.WebService_;

public final class ControleVersao_
    extends ControleVersao
{

    private Context context_;

    private ControleVersao_(Context context) {
        context_ = context;
        init_();
    }

    public static ControleVersao_ getInstance_(Context context) {
        return new ControleVersao_(context);
    }

    private void init_() {
        webService = new WebService_();
        if (context_ instanceof MainActivity) {
            context = ((MainActivity) context_);
        } else {
            Log.w("ControleVersao_", (("Due to Context class "+ context_.getClass().getSimpleName())+", the @RootContext MainActivity won't be populated"));
        }
        versaoRadares = VersaoRadares_.getInstance_(context_);
        radar = Radar_.getInstance_(context_);
        versaoRadarLocal = VersaoRadarLocal_.getInstance_(context_);
        radarLocal = RadarLocal_.getInstance_(context_);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}