package sc.senac.mms.appsus.manager;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import sc.senac.mms.appsus.DataManager;
import sc.senac.mms.appsus.entity.Historico;
import sc.senac.mms.appsus.interfaces.DataManagerInterface;

public class HistoricoManager implements DataManagerInterface<Historico, Long> {

    private DataManager dm;
    private Dao<Historico, Long> dao;

    public HistoricoManager(DataManager dataManager) {
        this.dm = dataManager;
        try {
            this.dao = getDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dao<Historico, Long> getDAO() throws SQLException {
        if (dao == null) {
            dao = this.dm.getDao(Historico.class);
        }
        return dao;
    }

    @Override
    public Boolean OnCreate(ConnectionSource connectionSource) throws SQLException {
        return TableUtils.createTableIfNotExists(connectionSource, Historico.class) > 0;
    }

    @Override
    public Boolean OnUpgrade(ConnectionSource connectionSource, Integer oldVersion, Integer newVersion) throws SQLException {
        Log.i(getClass().getSimpleName(), "upgrading table 'historico'");
        this.OnDestroy(connectionSource);
        this.OnCreate(connectionSource);
        return true;
    }

    @Override
    public Boolean OnDestroy(ConnectionSource connectionSource) throws SQLException {
        return TableUtils.dropTable(connectionSource, Historico.class, true) > 0;
    }
}
