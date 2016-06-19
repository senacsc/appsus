package sc.senac.mms.appsus;

import android.util.Log;

import sc.senac.mms.appsus.manager.ClasseTerapeuticaManager;
import sc.senac.mms.appsus.manager.DataManager;
import sc.senac.mms.appsus.manager.HistoricoManager;
import sc.senac.mms.appsus.manager.MedicamentoManager;

public class Application extends android.app.Application {

    private DataManager dataManager;

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        initializeManagers();
    }

    public static Application getInstance() {
        return mInstance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void initializeManagers() {

        Log.i(getClass().getSimpleName(), "Initializing database managers..");

        // Database Managers
        this.dataManager = new DataManager(getApplicationContext());
        this.dataManager.register(HistoricoManager.class);
        this.dataManager.register(MedicamentoManager.class);
        this.dataManager.register(ClasseTerapeuticaManager.class);
    }

    public HistoricoManager getHistoricoManager() {
        return (HistoricoManager) this.dataManager.get(HistoricoManager.class);
    }

    public MedicamentoManager getMedicamentoManager() {
        return (MedicamentoManager) this.dataManager.get(MedicamentoManager.class);
    }

    public ClasseTerapeuticaManager getClasseTerapeuticaManager() {
        return (ClasseTerapeuticaManager) this.dataManager.get(ClasseTerapeuticaManager.class);
    }

}
