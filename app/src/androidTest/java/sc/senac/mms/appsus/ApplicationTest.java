package sc.senac.mms.appsus;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.Arrays;

import sc.senac.mms.appsus.sqlite.PortableDB;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    public void testOpenPortableDatabase() {
        this.createApplication();

        PortableDB portableDB = new PortableDB(getContext(), "medicamentos.db");
        SQLiteDatabase db = portableDB.getReadableDatabase();

        Cursor queryCursor = null;

        try {
            queryCursor = db.rawQuery("SELECT * FROM medicamento", new String[]{});
        } catch (SQLiteException ignored) {
        }

        assertNotNull("Banco de dados 'medicamentos.db' não contem uma tabela com o nome 'medicamento'", queryCursor);

        String[] columnNames = queryCursor.getColumnNames();

        assertTrue(columnNames.length == 1);
        assertEquals(columnNames[0], "idMedicamento");

        queryCursor.close();
    }
}