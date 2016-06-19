package sc.senac.mms.appsus.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.view.MainActivity;
import sc.senac.mms.appsus.view.adapter.MedicamentoAdapter;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class MedicamentosFragment extends Fragment {

    private View fragmentView;
    private Application application;
    private MainActivity activity;
    private RecyclerView recyclerViewMedicamentos;
    private MedicamentoAdapter medicamentoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.fragmentView = inflater.inflate(R.layout.fragment_medicamentos, container, false);

        this.activity = (MainActivity) getActivity();
        this.application = (Application) this.activity.getApplication();

        /**
         * Inicia uma view de medicamentos otimizada que mostra somente os itens
         * que cabem na tela do usuário ao invés da lista inteira de medicamentos.
         *
         * Mais Informações em: <https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html>
         */
        recyclerViewMedicamentos = (RecyclerView) fragmentView.findViewById(R.id.recyclerViewMedicamentos);
        recyclerViewMedicamentos.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewMedicamentos.scrollToPosition(0);

        VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) fragmentView.findViewById(R.id.fast_scroller);
        SectionTitleIndicator sectionTitleIndicator = (SectionTitleIndicator) fragmentView.findViewById(R.id.fast_scroller_section_title_indicator);

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        fastScroller.setRecyclerView(recyclerViewMedicamentos);
        fastScroller.setSectionIndicator(sectionTitleIndicator);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        recyclerViewMedicamentos.addOnScrollListener(fastScroller.getOnScrollListener());

        // Registra o adapter da lista de medicamentos
        this.medicamentoAdapter = new MedicamentoAdapter(activity.medicamentoListModel);
        this.recyclerViewMedicamentos.setAdapter(medicamentoAdapter);

        medicamentoAdapter.setOnItemClickListener(new MedicamentoAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Medicamento m = medicamentoAdapter.getItem(position);

                MaterialDialog dialog = new MaterialDialog.Builder(activity)
                    .title(m.getDescricao())
                    .customView(R.layout.medicamento_dialog, true)
                    .positiveText("FECHAR")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    }).build();

                @SuppressWarnings("ConstantConditions")
                TextView classeTextView = (TextView) dialog.getCustomView().findViewById(R.id.classeTerapeuticaLabel);
                classeTextView.setText(m.getClasseTerapeutica().getNome());

                TextView formaTextView = (TextView) dialog.getCustomView().findViewById(R.id.formaApresentacaoLabel);
                formaTextView.setText(m.getFormaApresentacao());

                try {
                    application.getHistoricoManager().novo(m);
                } catch (SQLException e) {
                    Log.e(MainActivity.class.getSimpleName(), "Erro ao adicionar um histórico para o medicamento " + m.getIdMedicamento(), e);
                }

                dialog.show();
            }

            @Override
            public boolean onItemLongClick(int position, View v) {
                return true;
            }
        });

        return this.fragmentView;
    }

    public void atualizarListaMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentoAdapter.updateList(medicamentos);
    }

}
