package sc.senac.mms.appsus.manager.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public interface DataManagerHelper {
    <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException;
    ConnectionSource getConnectionSource();
}