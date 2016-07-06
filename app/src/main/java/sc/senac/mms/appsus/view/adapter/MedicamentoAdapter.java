package sc.senac.mms.appsus.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.view.adapter.holder.MedicamentoViewHolder;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoViewHolder> implements INameableAdapter {

    private ClickListener clickListener;
    private List<Medicamento> mMedicamentoModel;

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public Character getCharacterForElement(int element) {
        if (element > -1) {
            return this.getItem(element).getDescricao().charAt(0);
        }
        else {
            return 'A';
        }
    }

    public MedicamentoAdapter(List<Medicamento> listModel) {
        this.mMedicamentoModel = listModel;
    }

    @Override
    public void onBindViewHolder(MedicamentoViewHolder viewHolder, int position) {
        Medicamento model = mMedicamentoModel.get(position);
        viewHolder.update(model);
    }

    @Override
    public MedicamentoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medicamento_item, viewGroup, false);
        return new MedicamentoViewHolder(v, this.clickListener);
    }

    public Medicamento getItem(int position) {
        return mMedicamentoModel.get(position);
    }

    public List<Medicamento> getItemList() {
        return this.mMedicamentoModel;
    }

    @Override
    public int getItemCount() {
        return mMedicamentoModel.size();
    }

    public void updateList(List<Medicamento> listModel) {
        this.mMedicamentoModel = new ArrayList<>(listModel);
        notifyDataSetChanged();
    }
}
