package sc.senac.mms.appsus.manager;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import sc.senac.mms.appsus.entity.ClasseTerapeutica;
import sc.senac.mms.appsus.interfaces.DataManagerHelper;
import sc.senac.mms.appsus.interfaces.DataManagerInterface;
import sc.senac.mms.appsus.manager.annotations.DatabaseSource;
import sc.senac.mms.appsus.manager.helpers.PortableDB;

@DatabaseSource(ref = PortableDB.class)
public class ClasseTerapeuticaManager implements DataManagerInterface<ClasseTerapeutica, Long> {

    private DataManagerHelper helper;
    private Dao<ClasseTerapeutica, Long> dao;

    public ClasseTerapeuticaManager(DataManagerHelper helper) {
        this.helper = helper;
        try {
            this.dao = getDAO();
        } catch (SQLException e) {
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
    public Boolean OnCreate(ConnectionSource connectionSource) throws SQLException {
        return TableUtils.createTableIfNotExists(connectionSource, ClasseTerapeutica.class) > 0;
    }

    @Override
    public Boolean OnUpgrade(ConnectionSource connectionSource, Integer oldVersion, Integer newVersion) throws SQLException {
        Log.i(getClass().getSimpleName(), "upgrading table 'classeTerapeutica'");
        this.OnDestroy(connectionSource);
        this.OnCreate(connectionSource);
        return true;
    }

    @Override
    public Boolean OnDestroy(ConnectionSource connectionSource) throws SQLException {
        return TableUtils.dropTable(connectionSource, ClasseTerapeutica.class, true) > 0;
    }
}