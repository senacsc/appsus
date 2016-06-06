package sc.senac.mms.appsus.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class PortableDB extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "medicamentos.db";
    public static final int DATABASE_VERSION = 1;

    public PortableDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public PortableDB(Context context, String database) {
        super(context, database, null, DATABASE_VERSION);
    }

    public PortableDB(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, storageDirectory, factory, version);
    }
}
