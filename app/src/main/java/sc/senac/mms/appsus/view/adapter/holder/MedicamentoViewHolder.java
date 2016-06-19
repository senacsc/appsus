package sc.senac.mms.appsus.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.view.adapter.MedicamentoAdapter;

public class MedicamentoViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener {

    public TextView descricaoMedicamentoView;
    public TextView classeMedicamentoView;
    private MedicamentoAdapter.ClickListener clickListener;

    public MedicamentoViewHolder(View itemView, MedicamentoAdapter.ClickListener clickListener) {
        super(itemView);

        this.clickListener = clickListener;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        this.descricaoMedicamentoView = (TextView) itemView.findViewById(R.id.descricao_medicamento);
        this.classeMedicamentoView = (TextView) itemView.findViewById(R.id.classe_medicamento);
    }

    public void update(Medicamento model) {
        this.descricaoMedicamentoView.setText(model.getDescricao());
        this.classeMedicamentoView.setText(model.getClasseTerapeutica().getNome());
    }

    @Override
    public void onClick(View v) {
        clickListener.onItemClick(getAdapterPosition(), v);
    }

    @Override
    public boolean onLongClick(View v) {
        return clickListener.onItemLongClick(getAdapterPosition(), v);
    }
}
