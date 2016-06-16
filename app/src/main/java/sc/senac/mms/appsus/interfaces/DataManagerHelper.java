package sc.senac.mms.appsus.interfaces;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import sc.senac.mms.appsus.entity.Historico;

public interface DataManagerHelper {
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException;
}