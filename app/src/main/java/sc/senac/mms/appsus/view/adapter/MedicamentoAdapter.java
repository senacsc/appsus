package sc.senac.mms.appsus.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viethoa.RecyclerViewFastScroller;

import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.view.adapter.holder.MedicamentoViewHolder;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoViewHolder> implements RecyclerViewFastScroller.BubbleTextGetter{

    private List<Medicamento> mMedicamentoModel;

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
        return new MedicamentoViewHolder(v);
    }

    public Medicamento getItem(int position) {
        return mMedicamentoModel.get(position);
    }

    public List<Medicamento> getItemList(){
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

    @Override
    public String getTextToShowInBubble(int pos) {

        if (pos < 0 || pos >= mMedicamentoModel.size())
            return null;

        String name = mMedicamentoModel.get(pos).getDescricao();
        if (name == null || name.length() < 1)
            return null;

        return mMedicamentoModel.get(pos).getDescricao().substring(0, 1);
    }
}
