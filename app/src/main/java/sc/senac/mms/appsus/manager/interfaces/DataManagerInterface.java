package sc.senac.mms.appsus.manager.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

/**
 *
 * @param <T> Class type
 * @param <R> PrimaryKey type
 */
public interface DataManagerInterface<T, R> {

    Dao<T, R> getDAO() throws java.sql.SQLException;

    Boolean OnCreate(ConnectionSource connectionSource) throws java.sql.SQLException;
    Boolean OnUpgrade(ConnectionSource connectionSource, Integer oldVersion, Integer newVersion) throws java.sql.SQLException;
    Boolean OnDestroy(ConnectionSource connectionSource) throws java.sql.SQLException;
}

