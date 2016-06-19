package sc.senac.mms.appsus.manager;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.manager.annotations.DatabaseSource;
import sc.senac.mms.appsus.manager.helpers.ExternalDB;
import sc.senac.mms.appsus.manager.interfaces.DataManagerHelper;
import sc.senac.mms.appsus.manager.interfaces.DataManagerInterface;

@DatabaseSource(ref = ExternalDB.class)
public class MedicamentoManager implements DataManagerInterface<Medicamento, Long> {

    private DataManagerHelper helper;
    private Dao<Medicamento, Long> dao;

    public MedicamentoManager(DataManagerHelper helper) {
        this.helper = helper;
        try {
            this.dao = getDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dao<Medicamento, Long> getDAO() throws SQLException {
        if (dao == null) {
            dao = this.helper.getDao(Medicamento.class);
        }
        return dao;
    }

    @Override
    public Boolean OnCreate() throws SQLException {
        return TableUtils.createTableIfNotExists(this.helper.getConnectionSource(), Medicamento.class) > 0;
    }

    @Override
    public Boolean OnUpgrade(Integer oldVersion, Integer newVersion) throws SQLException {
        Log.i(getClass().getSimpleName(), "upgrading table 'medicamento'");
        this.OnDestroy();
        this.OnCreate();
        return true;
    }

    @Override
    public Boolean OnDestroy() throws SQLException {
        return TableUtils.dropTable(this.helper.getConnectionSource(), Medicamento.class, true) > 0;
    }

    /**
     * Busca todos os medicamentos ordenados pela descrição
     *
     * @return lista de medicamentos
     * @throws SQLException
     */
    public List<Medicamento> buscarMedicamentos() throws SQLException {
        return this.getDAO()
            .queryBuilder()
            .orderBy("nomeMedicamento", true)
            .query();
    }
}
