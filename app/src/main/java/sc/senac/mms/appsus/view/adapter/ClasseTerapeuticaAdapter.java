package sc.senac.mms.appsus.view.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import sc.senac.mms.appsus.entity.ClasseTerapeutica;

public class ClasseTerapeuticaAdapter extends ArrayAdapter<ClasseTerapeutica> {

    public ClasseTerapeuticaAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public ClasseTerapeuticaAdapter(Context context, ClasseTerapeutica[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    public ClasseTerapeuticaAdapter(Context context, List<ClasseTerapeutica> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

}
