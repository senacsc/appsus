package sc.senac.mms.appsus.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.activity.watcher.MedicamentoWatcher;
import sc.senac.mms.appsus.adapter.MedicamentoAdapter;
import sc.senac.mms.appsus.entity.ClasseTerapeutica;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.entity.dao.ClasseTerapeuticaDAO;
import sc.senac.mms.appsus.entity.dao.MedicamentoBD;

public class MainActivity extends Activity {

    public ListView listViewMedicamentos;
    public EditText editTextPalavraDigitada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMedicamentos = (ListView) findViewById(R.id.listViewMedicamentos);
        editTextPalavraDigitada = (EditText) findViewById(R.id.editTextPalavraDigitada);

        this.buscarMedicamentos();

        ArrayList<Medicamento> list;
        list = this.buscarMedicamentos();

        MedicamentoAdapter adapter = new MedicamentoAdapter(this, list);
        listViewMedicamentos.setAdapter(adapter);

        getAutoCompleteListView(editTextPalavraDigitada);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filtro:

                ClasseTerapeuticaDAO daoClasse = new ClasseTerapeuticaDAO(MainActivity.this);

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);

                builderSingle.setTitle("Selecione uma classe farmacológica");

                final ArrayAdapter<ClasseTerapeutica> arrayAdapter = new ArrayAdapter<>(
                    MainActivity.this,
                    android.R.layout.select_dialog_singlechoice, daoClasse.listarClasseFarmacologica());


                builderSingle.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                builderSingle.setAdapter(
                    arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final ClasseTerapeutica classeTerapeutica = arrayAdapter.getItem(which);
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);

                            // mostra na listview a lista de medicamentos referente aquela classe farmacologica
                            builderInner.setMessage(classeTerapeutica.getNome());
                            builderInner.setTitle("Your Selected Item is");

                            builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    MedicamentoAdapter adapter = new MedicamentoAdapter(MainActivity.this, getMedicamentoPorClasseFarmacologica(classeTerapeutica));
                                    listViewMedicamentos.setAdapter(adapter);

                                    getAutoCompleteListView(editTextPalavraDigitada);
                                    dialog.dismiss();
                                }
                            });

                            builderInner.show();
                        }
                    });

                builderSingle.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<Medicamento> buscarMedicamentos() {

        MedicamentoBD daoMedicamento = new MedicamentoBD(getApplicationContext());

        ArrayList<Medicamento> medicamentos = new ArrayList<>();//daoMedicamento.listarMedicamentos();
        Integer itemLayout = android.R.layout.simple_list_item_1;

        ArrayAdapter<Medicamento> adapter = new ArrayAdapter<>(this, itemLayout, medicamentos);
        listViewMedicamentos.setAdapter(adapter);

        return medicamentos; //daoMedicamento.listarMedicamentos();
    }

    public List<Medicamento> getMedicamentoPorClasseFarmacologica(ClasseTerapeutica classeTerapeutica) {
        //MedicamentoBD medicamentoBD = new MedicamentoBD(MainActivity.this);
        return new ArrayList<>();//medicamentoBD.listarMedicamentosPorClasseFarmacologica(classeTerapeutica);
    }

    public void getAutoCompleteListView(EditText autoComplete) {
        MedicamentoAdapter adapter = (MedicamentoAdapter) this.listViewMedicamentos.getAdapter();
        autoComplete.addTextChangedListener(new MedicamentoWatcher(adapter));
    }
}
