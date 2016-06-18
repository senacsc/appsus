package sc.senac.mms.appsus.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.List;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Historico;

public class HistoricoActivity extends AppCompatActivity {

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
