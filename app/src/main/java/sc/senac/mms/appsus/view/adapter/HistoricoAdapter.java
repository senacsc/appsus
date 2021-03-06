package sc.senac.mms.appsus.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Historico;
import sc.senac.mms.appsus.view.adapter.holder.HistoricoViewHolder;
import sc.senac.mms.appsus.view.adapter.holder.MedicamentoViewHolder;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoViewHolder> implements INameableAdapter {

    private ClickListener clickListener;
    private List<Historico> mHistoricoModel;

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public Character getCharacterForElement(int element) {
        return this.getItem(element).getMedicamento().getDescricao().charAt(0);
    }

    public HistoricoAdapter(List<Historico> listModel) {
        this.mHistoricoModel = listModel;
    }

    @Override
    public void onBindViewHolder(HistoricoViewHolder viewHolder, int position) {
        Historico model = mHistoricoModel.get(position);
        viewHolder.update(model);
    }

    @Override
    public HistoricoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.historico_item, viewGroup, false);
        return new HistoricoViewHolder(v, this.clickListener);
    }

    public Historico getItem(int position) {
        return mHistoricoModel.get(position);
    }

    public List<Historico> getItemList() {
        return this.mHistoricoModel;
    }

    @Override
    public int getItemCount() {
        return mHistoricoModel.size();
    }

    public void updateList(List<Historico> listModel) {
        this.mHistoricoModel = new ArrayList<>(listModel);
        notifyDataSetChanged();
    }
}