package sc.senac.mms.appsus.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Medicamento;

public class MedicamentoViewHolder extends RecyclerView.ViewHolder {

    public TextView descricaoMedicamentoView;
    public TextView classeMedicamentoView;

    public MedicamentoViewHolder(View itemView) {
        super(itemView);

        this.descricaoMedicamentoView = (TextView) itemView.findViewById(R.id.descricao_medicamento);
        this.classeMedicamentoView = (TextView) itemView.findViewById(R.id.classe_medicamento);
    }

    public void update(Medicamento model) {
        this.descricaoMedicamentoView.setText(model.getDescricao());
        this.classeMedicamentoView.setText(model.getClasseTerapeutica().getNome());
    }
}
