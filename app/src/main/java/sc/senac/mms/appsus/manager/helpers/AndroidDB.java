package sc.senac.mms.appsus.manager.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.crash.FirebaseCrash;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.manager.DataManager;
import sc.senac.mms.appsus.manager.interfaces.DataManagerHelper;

public class AndroidDB extends OrmLiteSqliteOpenHelper implements DataManagerHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DEFAULT_DATABASE = "appsus.db";

    private DataManager dataManager;

    public AndroidDB(Context context, DataManager dataManager) {
        super(context, DEFAULT_DATABASE, null, DATABASE_VERSION);
        this.dataManager = dataManager;
    }

    public AndroidDB(Context context, String database, DataManager dataManager) {
        super(context, database, null, DATABASE_VERSION);
        this.dataManager = dataManager;
    }

    public AndroidDB(Context context, String database, int databaseVersion) {
        super(context, database, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Application.getInstance().getHistoricoManager().OnCreate();
        } catch (SQLException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Application.getInstance().getHistoricoManager().OnCreate();
        } catch (SQLException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }
}
