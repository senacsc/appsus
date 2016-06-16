package sc.senac.mms.appsus.activity;

import android.app.Activity;
import android.os.Bundle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Historico;

public class HistoricoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        try {

            List<Historico> historicos = Application.getInstance().getHistoricoManager().getDAO().queryForAll();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
