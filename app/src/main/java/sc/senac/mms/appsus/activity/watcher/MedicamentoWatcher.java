package sc.senac.mms.appsus.activity.watcher;

import android.text.Editable;
import android.text.TextWatcher;

import sc.senac.mms.appsus.adapter.MedicamentoAdapter;

public class MedicamentoWatcher implements TextWatcher {

    private MedicamentoAdapter adapter;

    public MedicamentoWatcher(MedicamentoAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence searchText, int start, int before, int count) {
        this.adapter.getFilter().filter(searchText);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
