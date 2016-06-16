package sc.senac.mms.appsus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class PortableDB extends PortableSQLiteHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DEFAULT_DATABASE = "medicamentos.db";

    public PortableDB(Context context) {
        super(context, DEFAULT_DATABASE, null, DATABASE_VERSION);
    }

    public PortableDB(Context context, String database) {
        super(context, database, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
