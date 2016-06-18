package sc.senac.mms.appsus.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.entity.Medicamento;

public class MedicamentoAdapterOld extends ArrayAdapter<Medicamento> {

    public MedicamentoAdapterOld(Context context) {
        super(context, android.R.layout.simple_list_item_1, new ArrayList<Medicamento>());
    }

    public MedicamentoAdapterOld(Context context, List<Medicamento> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    @Override
    public long getItemId(int position) {
        return super.getItem(position).getIdMedicamento();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
