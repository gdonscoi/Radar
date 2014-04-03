package br.com.radaresmoveisararas.bd;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.radaresmoveisararas.beans.Radar;
import br.com.radaresmoveisararas.beans.VersaoBD;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ORMLiteHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "radaresmoveis.db";

    private static final int DATABASE_VERSION = 3;

    private static ORMLiteHelper mInstance = null;
      
    private Dao<Radar, Integer> radarDao = null;
    private Dao<VersaoBD, Integer> versaoDao = null;
    
    public ORMLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Radar.class);
            TableUtils.createTable(connectionSource, VersaoBD.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
            int oldVersion, int newVersion) {
        try {           
            TableUtils.dropTable(connectionSource, Radar.class, true);
            TableUtils.dropTable(connectionSource, VersaoBD.class, true);
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
   
    public Dao<VersaoBD, Integer> getVersaoBDDao() throws SQLException {
        if (versaoDao == null) {
        	versaoDao = getDao(VersaoBD.class);
        }
        return versaoDao;
    }

}