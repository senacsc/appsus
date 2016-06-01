package sc.senac.mms.appsus.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import sc.senac.mms.appsus.dao.ClasseFarmacologicaBD;
import sc.senac.mms.appsus.dao.MedicamentoBD;
import sc.senac.mms.appsus.dao.MySqlDao;
import sc.senac.mms.appsus.model.ClasseFarmacologica;
import sc.senac.mms.appsus.model.Medicamento;
import sc.senac.mms.appsus.R;

public class MainActivity extends AppCompatActivity {

    ClasseFarmacologica classeFarmacologica;
    ClasseFarmacologicaBD classeFarmacologicaBD;
    Medicamento medicamento;
    MedicamentoBD medicamentoBD;
    ListView listViewMedicamentos;
    EditText editTextPalavraDigitada;
    ArrayAdapter<Medicamento> arrayAdapterMedicamento;
    MySqlDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewMedicamentos = (ListView) findViewById(R.id.listViewMedicamentos);
        editTextPalavraDigitada = (EditText) findViewById(R.id.editTextPalavraDigitada);
        this.cadastraClasse();
        this.cadastraMedicamento();
        this.buscarMedicamentos();

        ArrayList<Medicamento> list;
        list = this.buscarMedicamentos();

        arrayAdapterMedicamento = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listViewMedicamentos.setAdapter(arrayAdapterMedicamento);
        getAutoCompleteListView(editTextPalavraDigitada);

        try {
            dao = new MySqlDao();
            dao.getListaMedicamentoMySql();
            System.out.println("Entro ");
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filtro:

                classeFarmacologica = new ClasseFarmacologica();
                classeFarmacologicaBD = new ClasseFarmacologicaBD(MainActivity.this);

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);

                builderSingle.setTitle("Selecione uma classe farmacológica");

                final ArrayAdapter<ClasseFarmacologica> arrayAdapter = new ArrayAdapter<>(
                        MainActivity.this,
                        android.R.layout.select_dialog_singlechoice, classeFarmacologicaBD.listarClasseFarmacologica());


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
                                final ClasseFarmacologica classeFarm = arrayAdapter.getItem(which);
                                AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                        MainActivity.this);

                                //mostra na listview a lista de medicamentos referente aquela classe farmacologica
                                builderInner.setMessage(classeFarm.getNome());
                                builderInner.setTitle("Your Selected Item is");
                                builderInner.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                arrayAdapterMedicamento = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, getMedicamentoPorClasseFarmacologica(classeFarm));

                                                listViewMedicamentos.setAdapter(arrayAdapterMedicamento);
                                                System.out.println(classeFarm);
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

    public void cadastraClasse() {

        classeFarmacologica = new ClasseFarmacologica();
        classeFarmacologicaBD = new ClasseFarmacologicaBD(MainActivity.this);
        int contador = 0;

        //enquanto tiver classe na lista ele cadastra no banco
        for (ClasseFarmacologica classe : classeFarmacologica.getListaClasseFarmacologica()) {
            classeFarmacologicaBD.cadastrar(classe);
            contador++;
        }

        System.out.println("Foram inseridas " + contador + " classes farmacológicas no banco");
    }

    public void cadastraMedicamento() {

        medicamento = new Medicamento();
        medicamentoBD = new MedicamentoBD(MainActivity.this);
        int contador = 0;

        //enquanto tiver medicamento na lista ele cadastra no banco
        for (Medicamento med : medicamento.getListaMedicamentos()) {
            medicamentoBD.cadastrar(med);
            contador++;
        }

        System.out.println("Foram inseridos " + contador + " medicamentos no banco");


    }

    public ArrayList<Medicamento> buscarMedicamentos() {

        medicamentoBD = new MedicamentoBD(getApplicationContext());

        ArrayAdapter<Medicamento> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,
                medicamentoBD.listarMedicamentos());

        listViewMedicamentos.setAdapter(adapter);

        return medicamentoBD.listarMedicamentos();

    }

    public ArrayList<Medicamento> getMedicamentoPorClasseFarmacologica(ClasseFarmacologica classeFarmacologica) {
        medicamentoBD = new MedicamentoBD(MainActivity.this);
        return medicamentoBD.listarMedicamentosPorClasseFarmacologica(classeFarmacologica);
    }

    public void getAutoCompleteListView(EditText autoComplete) {

        autoComplete.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                arrayAdapterMedicamento.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
}
