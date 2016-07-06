package sc.senac.mms.appsus.manager;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import sc.senac.mms.appsus.entity.ClasseTerapeutica;
import sc.senac.mms.appsus.manager.annotations.DatabaseSource;
import sc.senac.mms.appsus.manager.helpers.ExternalDB;
import sc.senac.mms.appsus.manager.interfaces.DataManagerHelper;
import sc.senac.mms.appsus.manager.interfaces.DataManagerInterface;

@DatabaseSource(ref = ExternalDB.class)
public class ClasseTerapeuticaManager implements DataManagerInterface<ClasseTerapeutica, Long> {

    private DataManagerHelper helper;
    private Dao<ClasseTerapeutica, Long> dao;

    public ClasseTerapeuticaManager(DataManagerHelper helper) {
        this.helper = helper;
        try {
            this.dao = getDAO();
        } catch (SQLException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }

    @Override
    public Dao<ClasseTerapeutica, Long> getDAO() throws SQLException {
        if (dao == null) {
            dao = this.helper.getDao(ClasseTerapeutica.class);
        }
        return dao;
    }

    @Override
    public Boolean OnCreate() throws SQLException {
        return TableUtils.createTableIfNotExists(this.helper.getConnectionSource(), ClasseTerapeutica.class) > 0;
    }

    @Override
    public Boolean OnUpgrade(Integer oldVersion, Integer newVersion) throws SQLException {
        Log.i(getClass().getSimpleName(), "upgrading table 'classeTerapeutica'");
        //this.OnDestroy();
        //this.OnCreate();
        return true;
    }

    @Override
    public Boolean OnDestroy() throws SQLException {
        return TableUtils.dropTable(this.helper.getConnectionSource(), ClasseTerapeutica.class, true) > 0;
    }

    public List<ClasseTerapeutica> buscarClasses() throws SQLException {
        return this.getDAO()
            .queryBuilder()
            .orderBy("nomeClasse", true)
            .query();
    }
}