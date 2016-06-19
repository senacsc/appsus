package sc.senac.mms.appsus.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.view.adapter.holder.MedicamentoViewHolder;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoViewHolder> implements SectionIndexer {

    private List<Medicamento> mMedicamentoModel;
    private List<Object> mSections;

    public MedicamentoAdapter(List<Medicamento> listModel) {
        this.mMedicamentoModel = listModel;
        updateSectionList();
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

    public List<Medicamento> getItemList() {
        return this.mMedicamentoModel;
    }

    @Override
    public int getItemCount() {
        return mMedicamentoModel.size();
    }

    private void updateSectionList() {
        this.mSections = Arrays.asList(getSections());
    }

    public void updateList(List<Medicamento> listModel) {
        this.mMedicamentoModel = new ArrayList<>(listModel);
        notifyDataSetChanged();
        updateSectionList();
    }

    @Override
    public Object[] getSections() {
        List<String> strAlphabets = new ArrayList<>();
        for (int i = 0; i < mMedicamentoModel.size(); i++) {
            String name = mMedicamentoModel.get(i).getDescricao();
            if (name == null || name.trim().isEmpty())
                continue;
            String word = name.substring(0, 1);
            if (!strAlphabets.contains(word)) {
                strAlphabets.add(word);
            }
        }
        return strAlphabets.toArray();
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position >= mMedicamentoModel.size()) {
            position = mMedicamentoModel.size() - 1;
        }
        Medicamento m = mMedicamentoModel.get(position);
        return this.mSections.indexOf(Character.toString(m.getDescricao().charAt(0)));
    }
}
