package sc.senac.mms.appsus.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import sc.senac.mms.appsus.Model.ClasseFarmacologica;
import sc.senac.mms.appsus.Model.Medicamento;

/**
 * Created by Aluno on 08/04/2016.
 */
public class MedicamentoBD {


    //Definir nome do banco
    private static final String NOME_BANCO = "sus.bd";
    private static final int BANCO_ACESSO = 0;
    //Definir o nome da tabela
    private static final String NOME_TABELA = "medicamento";

    /* Definir o modo de acesso ao banco
    0 -> Modo privado
    1 -> Outros apps podem ler
    2 -> Outros apps podem ler e escrever
     */
    private static final String SQL_TABLE = "" +
            "CREATE TABLE IF NOT EXISTS " + NOME_TABELA + "(" +
            "idmedicamento integer not null primary key autoincrement," +
            "descricao varchar(100) not null," +
            "forma_apresentacao varchar(80) not null," +
            "classefarmacologica_idclassefarmacologica int not null, " +
            "foreign key (classefarmacologica_idclassefarmacologica) references classefarmacologica (idclassefarmacologica))";

    //SQL para selecionar todas os medicamentos
    private static final String SELECT_ALL = "" +
            "SELECT  medicamento.idmedicamento, " +
            "medicamento.descricao," +
            " medicamento.forma_apresentacao, " +
            "medicamento.classefarmacologica_idclassefarmacologica," +
            "classefarmacologica.idclassefarmacologica," +
            "classefarmacologica.nome FROM " + NOME_TABELA +
            " inner join classefarmacologica on " +
            "medicamento.classefarmacologica_idclassefarmacologica = classefarmacologica.idclassefarmacologica  ";

    private static final String SELECT_BY_CLASSE_FARMACOLOGICA = "" +
            "SELECT  medicamento.idmedicamento, " +
            "medicamento.descricao," +
            " medicamento.forma_apresentacao, " +
            "medicamento.classefarmacologica_idclassefarmacologica," +
            "classefarmacologica.idclassefarmacologica," +
            "classefarmacologica.nome FROM " + NOME_TABELA +
            " inner join classefarmacologica on " +
            "medicamento.classefarmacologica_idclassefarmacologica = classefarmacologica.idclassefarmacologica  " +
            "WHERE classefarmacologica.nome = ?";

    //Código SQL para criação da tabela
    private Medicamento medicamento;
    private SQLiteDatabase banco;

    public MedicamentoBD(Context ctx) {
        this.banco = ctx.openOrCreateDatabase(NOME_BANCO, BANCO_ACESSO, null);
        this.banco.execSQL(SQL_TABLE);
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    //Método para realizar cadastro no banco
    public boolean cadastrar(Medicamento medicamento) {

        long res = this.banco.insert(NOME_TABELA, null, medicamento.getContentValues());

        if (res != -1)
            return true;
        else
            return false;

    }

    //Método para edição de um emprestimo
    public boolean editar() {

        String[] args = new String[]{String.valueOf(this.medicamento.getId())};

        int res = this.banco.update(NOME_TABELA, this.medicamento.getContentValues(), "idemprestimo=?", args);

        if (res != -1)
            return true;
        else
            return false;
    }

    //Método para exclusao
    public boolean excluir() {

        String[] args = new String[]{String.valueOf(this.medicamento.getId())};

        int res = this.banco.delete(NOME_TABELA, "idemprestimo=?", args);

        if (res != -1)
            return true;
        else
            return false;
    }


    // Método para listar emprestimo
    public ArrayList<Medicamento> listarMedicamentos() {

        ArrayList<Medicamento> listMedicamento = new ArrayList<>();

        Cursor cursor = this.banco.rawQuery(SELECT_ALL, null);

        while (cursor.moveToNext()) {

            Medicamento medicamento = new Medicamento();
            ClasseFarmacologica classeFarmacologica = new ClasseFarmacologica();

            classeFarmacologica.setId(cursor.getInt(cursor.getColumnIndex("idclassefarmacologica")));
            classeFarmacologica.setNome(cursor.getString(cursor.getColumnIndex("classefarmacologica.nome")));
            medicamento.setId(cursor.getInt(cursor.getColumnIndex("idmedicamento")));
            medicamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            medicamento.setFormaApresentacao(cursor.getString(cursor.getColumnIndex("forma_apresentacao")));
            medicamento.setClasseFarmacologica(classeFarmacologica);

            //adiciona medicamento na lista
            listMedicamento.add(medicamento);
        }

        return listMedicamento;
    }

    public ArrayList<Medicamento> listarMedicamentosPorClasseFarmacologica(ClasseFarmacologica classeFarmacologicaFiltro) {

        ArrayList<Medicamento> listMedicamento = new ArrayList<>();
        String[] args = new String[]{classeFarmacologicaFiltro.getNome().toString()};
        Cursor cursor = this.banco.rawQuery(SELECT_BY_CLASSE_FARMACOLOGICA, args);

        while (cursor.moveToNext()) {

            Medicamento medicamento = new Medicamento();
            ClasseFarmacologica classeFarmacologica = new ClasseFarmacologica();

            classeFarmacologica.setId(cursor.getInt(cursor.getColumnIndex("idclassefarmacologica")));
            classeFarmacologica.setNome(cursor.getString(cursor.getColumnIndex("classefarmacologica.nome")));
            medicamento.setId(cursor.getInt(cursor.getColumnIndex("idmedicamento")));
            medicamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            medicamento.setFormaApresentacao(cursor.getString(cursor.getColumnIndex("forma_apresentacao")));
            medicamento.setClasseFarmacologica(classeFarmacologica);

            //adiciona medicamento na lista
            listMedicamento.add(medicamento);
        }

        return listMedicamento;
    }


}


