package sc.senac.mms.appsus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.support.ConnectionSource;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import sc.senac.mms.appsus.manager.interfaces.DataManagerHelper;
import sc.senac.mms.appsus.manager.interfaces.DataManagerInterface;
import sc.senac.mms.appsus.manager.annotations.DatabaseSource;
import sc.senac.mms.appsus.manager.helpers.AndroidDB;
import sc.senac.mms.appsus.manager.helpers.ExternalDB;

public class DataManager {

    private HashMap<String, DataManagerInterface> managers;
    private HashMap<String, DataManagerHelper> db;
    private Context context;

    public DataManager(Context context) {

        this.context = context;
        this.managers = new HashMap<>();
        this.db = new HashMap<>();

        this.setupGlobalHelpers();
    }

    public void setupGlobalHelpers() {
        this.db.put(ExternalDB.class.getName(), new ExternalDB(this.context));
        this.db.put(AndroidDB.class.getName(), new AndroidDB(this.context));
    }

    public void register(Class<? extends DataManagerInterface> dataManager) {
        try {

            if (!dataManager.isAnnotationPresent(DatabaseSource.class)) {
                throw new InvalidClassException("Invalid data manager");
            }

            // Get the data helper annotation from the class
            DatabaseSource helperAnnotation = dataManager.getAnnotation(DatabaseSource.class);
            DataManagerHelper helper = this.db.get(helperAnnotation.ref().getName());

            managers.put(dataManager.getSimpleName(), dataManager.getConstructor(DataManagerHelper.class).newInstance(helper));

            Log.d(getClass().getSimpleName(), dataManager.getSimpleName() + " registered and binded to '" + helperAnnotation.ref().getName() + "' class.");
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e(getClass().getSimpleName(), "Failed to register " + dataManager.getSimpleName() + " manager.", e);
        } catch (InvalidClassException e) {
            Log.e(getClass().getSimpleName(), "Failed to register " + dataManager.getSimpleName() + " manager, missing DatabaseSource annotation.", e);
        } catch (InvocationTargetException e) {
            Log.e(getClass().getSimpleName(), "Failed to register " + dataManager.getSimpleName() + " manager, initialization error.", e.getTargetException());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public DataManagerInterface get(Class<? extends DataManagerInterface> manager) {
        return this.managers.get(manager.getSimpleName());
    }

    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        for (Map.Entry<String, DataManagerInterface> dataManager : this.managers.entrySet()) {
            try {
                dataManager.getValue().OnCreate(connectionSource);
            } catch (java.sql.SQLException e) {
                Toast.makeText(context, "Failed to create " + dataManager.getKey() + " database.", Toast.LENGTH_SHORT).show();
            }
        }
    }

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
