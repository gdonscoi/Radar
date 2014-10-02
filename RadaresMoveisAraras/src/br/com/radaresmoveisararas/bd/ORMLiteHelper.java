package br.com.radaresmoveisararas.bd;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.radaresmoveisararas.beans.Radar;
import br.com.radaresmoveisararas.beans.RadarLocal;
import br.com.radaresmoveisararas.beans.RadarRadarLocal;
import br.com.radaresmoveisararas.beans.VersaoRadarLocal;
import br.com.radaresmoveisararas.beans.VersaoRadares;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ORMLiteHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "radaresmoveis.db";

    private static final int DATABASE_VERSION = 20;

    private static ORMLiteHelper mInstance = null;
      
    private Dao<Radar, Integer> radarDao = null;
    private Dao<RadarLocal, Integer> radarLocalDao = null;
    private Dao<RadarRadarLocal, Integer> radarRadarLocalDao = null;
    private Dao<VersaoRadares, Integer> versaoRadarDao = null;
    private Dao<VersaoRadarLocal, Integer> versaoRadarLocalDao = null;
    
    public ORMLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Radar.class);
            TableUtils.createTable(connectionSource, RadarLocal.class);
            TableUtils.createTable(connectionSource, RadarRadarLocal.class);
            TableUtils.createTable(connectionSource, VersaoRadares.class);
            TableUtils.createTable(connectionSource, VersaoRadarLocal.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
            int oldVersion, int newVersion) {
        try {           
            TableUtils.dropTable(connectionSource, Radar.class, true);
            TableUtils.dropTable(connectionSource, RadarLocal.class, true);
            TableUtils.dropTable(connectionSource, RadarRadarLocal.class, true);
            TableUtils.dropTable(connectionSource, VersaoRadares.class, true);
            TableUtils.dropTable(connectionSource, VersaoRadarLocal.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ORMLiteHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ORMLiteHelper(context.getApplicationContext());
        }
        return mInstance;
    }
   
    public Dao<Radar, Integer> getRadarDao() throws SQLException {
        if (radarDao == null) {
        	radarDao = getDao(Radar.class);
        }
        return radarDao;
    }
    
    public Dao<RadarLocal, Integer> getRadarLocalDao() throws SQLException {
        if (radarLocalDao == null){ 
        	radarLocalDao = getDao(RadarLocal.class);
        }
        return radarLocalDao;
    }

    public Dao<VersaoRadares, Integer> getVersaoRadarBDDao() throws SQLException {
        if (versaoRadarDao == null) {
        	versaoRadarDao = getDao(VersaoRadares.class);
        }
        return versaoRadarDao;
    }
    
    public Dao<VersaoRadarLocal, Integer> getVersaoRadarLocalBDDao() throws SQLException {
        if (versaoRadarLocalDao == null) {
        	versaoRadarLocalDao = getDao(VersaoRadarLocal.class);
        }
        return versaoRadarLocalDao;
    }
    
    public Dao<RadarRadarLocal, Integer> getRadarRadarLocalBDDao() throws SQLException {
        if (radarRadarLocalDao == null) {
        	radarRadarLocalDao = getDao(RadarRadarLocal.class);
        }
        return radarRadarLocalDao;
    }

}