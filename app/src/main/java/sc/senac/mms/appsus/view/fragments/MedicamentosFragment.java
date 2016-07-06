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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.sql.SQLException;
import java.util.List;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.view.MainActivity;
import sc.senac.mms.appsus.view.adapter.ClickListener;
import sc.senac.mms.appsus.view.adapter.MedicamentoAdapter;

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

        this.activity.setTitle("Medicamentos");

        /**
         * Inicia uma view de medicamentos otimizada que mostra somente os itens
         * que cabem na tela do usuário ao invés da lista inteira de medicamentos.
         *
         * Mais Informações em: <https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html>
         */
        recyclerViewMedicamentos = (RecyclerView) fragmentView.findViewById(R.id.recyclerViewMedicamentos);
        recyclerViewMedicamentos.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewMedicamentos.scrollToPosition(0);

        DragScrollBar materialScrollBar = new DragScrollBar(activity, recyclerViewMedicamentos, true);
        materialScrollBar.setHandleColour(getResources().getColor(R.color.md_teal_400));
        materialScrollBar.addIndicator(new AlphabetIndicator(activity), true);

        // Registra o adapter da lista de medicamentos
        this.medicamentoAdapter = new MedicamentoAdapter(activity.medicamentoListModel);
        this.recyclerViewMedicamentos.setAdapter(medicamentoAdapter);

        medicamentoAdapter.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Medicamento m = medicamentoAdapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "0");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "show_medicamento");
                bundle.putString(FirebaseAnalytics.Param.VALUE, m.getIdMedicamento().toString());
                activity.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);

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
                    FirebaseCrash.report(e);
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
