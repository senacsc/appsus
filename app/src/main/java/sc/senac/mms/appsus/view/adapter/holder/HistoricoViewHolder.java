package sc.senac.mms.appsus.view.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Historico;
import sc.senac.mms.appsus.view.adapter.ClickListener;

public class HistoricoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView descricaoMedicamentoView;
    public TextView classeMedicamentoView;
    public TextView dtVisualizacaoView;
    private ClickListener clickListener;

    public HistoricoViewHolder(View itemView, ClickListener clickListener) {
        super(itemView);

        this.clickListener = clickListener;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        this.descricaoMedicamentoView = (TextView) itemView.findViewById(R.id.descricao_medicamento);
        this.classeMedicamentoView = (TextView) itemView.findViewById(R.id.classe_medicamento);
        this.dtVisualizacaoView = (TextView) itemView.findViewById(R.id.data_visualizacao);
    }

    public void update(Historico model) {
        this.descricaoMedicamentoView.setText(model.getMedicamento().getDescricao());
        this.classeMedicamentoView.setText(model.getMedicamento().getClasseTerapeutica().getNome());

        Context context = Application.getInstance().getApplicationContext();
        this.dtVisualizacaoView.setText(DateFormat.getLongDateFormat(context).format(model.getDtVisualizacao()));
        this.dtVisualizacaoView.setTextColor(context.getResources().getColor(R.color.md_blue_700));
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null)
            clickListener.onItemClick(getAdapterPosition(), v);
    }

    @Override
    public boolean onLongClick(View v) {
        return clickListener == null || clickListener.onItemLongClick(getAdapterPosition(), v);
    }
}
