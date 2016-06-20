package sc.senac.mms.appsus.manager.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.manager.DataManager;
import sc.senac.mms.appsus.manager.helpers.internal.PortableSQLiteHelper;
import sc.senac.mms.appsus.manager.interfaces.DataManagerHelper;

public class ExternalDB extends PortableSQLiteHelper implements DataManagerHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DEFAULT_DATABASE = "medicamentos.db";

    private DataManager dataManager;

    public ExternalDB(Context context, DataManager dataManager) {
        super(context, DEFAULT_DATABASE, null, DATABASE_VERSION);
        this.dataManager = dataManager;
    }

    public ExternalDB(Context context, String database) {
        super(context, database, null, DATABASE_VERSION);
    }

    public ExternalDB(Context context, String database, int databaseVersion) {
        super(context, database, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Application.getInstance().getMedicamentoManager().OnUpgrade(oldVersion, newVersion);
            Application.getInstance().getClasseTerapeuticaManager().OnUpgrade(oldVersion, newVersion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}