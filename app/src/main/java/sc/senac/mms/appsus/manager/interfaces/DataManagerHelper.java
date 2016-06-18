package sc.senac.mms.appsus.manager.interfaces;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public interface DataManagerHelper {
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException;
}