package sc.senac.mms.appsus.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.sql.SQLException;
import java.util.List;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Historico;
import sc.senac.mms.appsus.view.MainActivity;
import sc.senac.mms.appsus.view.adapter.ClickListener;
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

        historicoAdapter.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Historico h = historicoAdapter.getItem(position);

                MaterialDialog dialog = new MaterialDialog.Builder(activity)
                    .title(h.getMedicamento().getDescricao())
                    .customView(R.layout.medicamento_dialog, true)
                    .positiveText(getString(R.string.fechar))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    }).build();

                @SuppressWarnings("ConstantConditions")
                TextView classeTextView = (TextView) dialog.getCustomView().findViewById(R.id.classeTerapeuticaLabel);
                classeTextView.setText(h.getMedicamento().getClasseTerapeutica().getNome());

                TextView formaTextView = (TextView) dialog.getCustomView().findViewById(R.id.formaApresentacaoLabel);
                formaTextView.setText(h.getMedicamento().getFormaApresentacao());

                dialog.show();
            }

            @Override
            public boolean onItemLongClick(int position, View v) {
                return true;
            }
        });

        return fragmentView;
    }

    public void atualizarListaHistoricos(List<Historico> historicos) {
        this.historicoAdapter.updateList(historicos);
    }

}
