package sc.senac.mms.appsus.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Historico;
import sc.senac.mms.appsus.view.MainActivity;

public class HistoricoFragment extends Fragment {

    private Application application;
    private MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (MainActivity) getActivity();
        this.application = (Application) this.activity.getApplication();

        try {

            List<Historico> historicoList = application.getHistoricoManager().buscarHistoricos();



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.fragment_historico, container, false);
    }


}
