package sc.senac.mms.appsus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import sc.senac.mms.appsus.interfaces.DataManagerInterface;

public class DataManager extends OrmLiteSqliteOpenHelper {

    private static final int CURRENT_VERSION = 1;

    private HashMap<String, DataManagerInterface> managers;
    private Context context;

    public DataManager(Context context, String database) {
        super(context, database, null, CURRENT_VERSION);
        this.context = context;
        this.managers = new HashMap<>();
    }

    public void registerManager(Class<? extends DataManagerInterface> dataManager) {
        try {
            managers.put(dataManager.getSimpleName(), dataManager.getConstructor(DataManager.class).newInstance(this));
            Log.d(getClass().getSimpleName(), "Data manager " + dataManager.getSimpleName() + " registered.");
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            Log.e(getClass().getSimpleName(), "Failed to register " + dataManager.getSimpleName() + " manager.", e);
        }
    }

    public DataManagerInterface getManager(Class<? extends DataManagerInterface> manager) {
        return this.managers.get(manager.getSimpleName());
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        for (Map.Entry<String, DataManagerInterface> dataManager : this.managers.entrySet()) {
            try {
                dataManager.getValue().OnCreate(connectionSource);
            } catch (java.sql.SQLException e) {
                Toast.makeText(context, "Failed to create " + dataManager.getKey() + " database.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        for (Map.Entry<String, DataManagerInterface> dataManager : this.managers.entrySet()) {
            try {
                dataManager.getValue().OnUpgrade(connectionSource, oldVersion, newVersion);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to upgrade " + dataManager.getKey() + " database.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onDestroy(ConnectionSource connectionSource) {
        for (Map.Entry<String, DataManagerInterface> dataManager : this.managers.entrySet()) {
            try {
                dataManager.getValue().OnDestroy(connectionSource);
            } catch (java.sql.SQLException e) {
                Toast.makeText(context, "Failed to delete " + dataManager.getKey() + " database.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
