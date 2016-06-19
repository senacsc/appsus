package sc.senac.mms.appsus.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.List;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Historico;
import sc.senac.mms.appsus.view.MainActivity;
import sc.senac.mms.appsus.view.adapter.HistoricoAdapter;

public class HistoricoFragment extends Fragment {

    private Application application;
    private MainActivity activity;
    private RecyclerView recyclerViewHistorico;
    private HistoricoAdapter historicoAdapter;
    private List<Historico> historicosListModel;
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();
        application = (Application) this.activity.getApplication();
        fragmentView = inflater.inflate(R.layout.fragment_historico, container, false);

        recyclerViewHistorico = (RecyclerView) fragmentView.findViewById(R.id.recyclerViewHistorico);
        recyclerViewHistorico.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewHistorico.scrollToPosition(0);

        this.activity.setTitle("Histórico");

        try {
            historicosListModel = application.getHistoricoManager().buscarHistoricos();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        historicoAdapter = new HistoricoAdapter(historicosListModel);
        recyclerViewHistorico.setAdapter(historicoAdapter);

        return fragmentView;
    }

    public void atualizarListaHistoricos(List<Historico> historicos) {
        this.historicoAdapter.updateList(historicos);
    }

}
