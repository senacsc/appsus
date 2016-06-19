package sc.senac.mms.appsus.manager;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import sc.senac.mms.appsus.entity.Historico;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.manager.annotations.DatabaseSource;
import sc.senac.mms.appsus.manager.helpers.AndroidDB;
import sc.senac.mms.appsus.manager.interfaces.DataManagerHelper;
import sc.senac.mms.appsus.manager.interfaces.DataManagerInterface;

@DatabaseSource(ref = AndroidDB.class)
public class HistoricoManager implements DataManagerInterface<Historico, Long> {

    private DataManagerHelper helper;
    private Dao<Historico, Long> dao;

    public HistoricoManager(DataManagerHelper helper) {
        this.helper = helper;
        try {
            this.dao = getDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dao<Historico, Long> getDAO() throws SQLException {
        if (dao == null) {
            dao = this.helper.getDao(Historico.class);
        }
        return dao;
    }

    @Override
    public Boolean OnCreate() throws SQLException {
        return TableUtils.createTableIfNotExists(this.helper.getConnectionSource(), Historico.class) > 0;
    }

    @Override
    public Boolean OnUpgrade(Integer oldVersion, Integer newVersion) throws SQLException {
        Log.i(getClass().getSimpleName(), "upgrading table 'historico'");
        this.OnDestroy();
        this.OnCreate();
        return true;
    }

    @Override
    public Boolean OnDestroy() throws SQLException {
        return TableUtils.dropTable(this.helper.getConnectionSource(), Historico.class, true) > 0;
    }

    public List<Historico> buscarHistoricos() throws SQLException {
        return this.getDAO().queryBuilder()
            .orderBy("dtVisualizacao", false)
            .query();

    }

    public boolean novo(Medicamento m) throws SQLException {

        Historico h = new Historico();
        h.setDtVisualizacao(new Date());
        h.setMedicamento(m);

        return this.getDAO().create(h) > 0;
    }
}
