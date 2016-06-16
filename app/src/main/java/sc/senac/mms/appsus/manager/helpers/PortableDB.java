package sc.senac.mms.appsus.manager.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;

import sc.senac.mms.appsus.PortableSQLiteHelper;
import sc.senac.mms.appsus.interfaces.DataManagerHelper;

public class PortableDB extends PortableSQLiteHelper implements DataManagerHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DEFAULT_DATABASE = "medicamentos.db";

    public PortableDB(Context context) {
        super(context, DEFAULT_DATABASE, null, DATABASE_VERSION);
    }

    public PortableDB(Context context, String database) {
        super(context, database, null, DATABASE_VERSION);
    }

    public PortableDB(Context context, String database, int databaseVersion) {
        super(context, database, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}