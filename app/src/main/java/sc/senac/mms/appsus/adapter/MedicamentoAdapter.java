package sc.senac.mms.appsus.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import sc.senac.mms.appsus.entity.Medicamento;

public class MedicamentoAdapter extends ArrayAdapter<Medicamento> {

    public MedicamentoAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public MedicamentoAdapter(Context context, Medicamento[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    public MedicamentoAdapter(Context context, List<Medicamento> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

}
