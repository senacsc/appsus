package sc.senac.mms.appsus;

import android.util.Log;

import sc.senac.mms.appsus.manager.HistoricoManager;

public class Application extends android.app.Application {

    public static final String DATABASE_NAME = "appsus.db";
    private DataManager dataManager;

    private static Application mInstance;

    public Application() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
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
        this.dataManager = new DataManager(getApplicationContext(), DATABASE_NAME);
        //this.dataManager.registerManager(MedicamentoManager.class);
        //this.dataManager.registerManager(ClasseTerapeuticaManager.class);
        this.dataManager.registerManager(HistoricoManager.class);
    }

    public HistoricoManager getHistoricoManager() {
        return (HistoricoManager) this.dataManager.getManager(HistoricoManager.class);
    }

//    public MedicamentoManager getMedicamentoManager() {
//        return (MedicamentoManager) this.dataManager.getManager(MedicamentoManager.class);
//    }
//
//    public ClasseTerapeuticaManager getClasseTerapeuticaManager() {
//        return (ClasseTerapeuticaManager) this.dataManager.getManager(ClasseTerapeuticaManager.class);
//    }

}
