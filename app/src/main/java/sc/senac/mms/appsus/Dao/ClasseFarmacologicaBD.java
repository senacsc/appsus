package sc.senac.mms.appsus.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import sc.senac.mms.appsus.Model.ClasseFarmacologica;

/**
 * Created by Milton on 18/05/2016.
 */
public class ClasseFarmacologicaBD {

    //Definir nome do banco
    private static final String NOME_BANCO = "sus.bd";
    private static final int BANCO_ACESSO = 0;
    //Definir o nome da tabela
    private static final String NOME_TABELA = "classefarmacologica";

    /* Definir o modo de acesso ao banco
    0 -> Modo privado
    1 -> Outros apps podem ler
    2 -> Outros apps podem ler e escrever
     */
    private static final String SQL_TABLE = "" +
            "CREATE TABLE IF NOT EXISTS " + NOME_TABELA + "(" +
            "idclassefarmacologica integer not null primary key autoincrement," +
            "nome varchar(100) not null)";

    //SQL para selecionar todas classes
    private static final String SELECT_ALL = "" +
            "SELECT idclassefarmacologica, nome FROM " + NOME_TABELA;

    //Código SQL para criação da tabela
    private ClasseFarmacologica classeFarmacologica;
    private SQLiteDatabase banco;

    public ClasseFarmacologicaBD(Context ctx) {
        this.banco = ctx.openOrCreateDatabase(NOME_BANCO, BANCO_ACESSO, null);
        this.banco.execSQL(SQL_TABLE);
    }

    public ClasseFarmacologica getClasseFarmacologica() {
        return classeFarmacologica;
    }

    public void setClasseFarmacologica(ClasseFarmacologica classeFarmacologica) {
        this.classeFarmacologica = classeFarmacologica;
    }

    //Método para realizar cadastro no banco
    public boolean cadastrar(ClasseFarmacologica classeFarmacologica) {

        long res = this.banco.insert(NOME_TABELA, null, classeFarmacologica.getContentValues());

        if (res != -1)
            return true;
        else
            return false;

    }

    //Método para edição de um emprestimo
    public boolean editar() {

        String[] args = new String[]{String.valueOf(this.classeFarmacologica.getId())};

        int res = this.banco.update(NOME_TABELA, this.classeFarmacologica.getContentValues(), "idclassefarmacologica=?", args);

        if (res != -1)
            return true;
        else
            return false;
    }

    //Método para exclusao
    public boolean excluir() {

        String[] args = new String[]{String.valueOf(this.classeFarmacologica.getId())};

        int res = this.banco.delete(NOME_TABELA, "idclassefarmacologica=?", args);

        if (res != -1)
            return true;
        else
            return false;
    }


    // Método para listar emprestimo
    public ArrayList<ClasseFarmacologica> listarClasseFarmacologica() {

        ArrayList<ClasseFarmacologica> listClasseFarmacologica = new ArrayList<>();
        Cursor cursor = this.banco.rawQuery(SELECT_ALL, null);

        while (cursor.moveToNext()) {

            ClasseFarmacologica classeFarmacologica = new ClasseFarmacologica();

            classeFarmacologica.setId(cursor.getInt(cursor.getColumnIndex("idclassefarmacologica")));
            classeFarmacologica.setNome(cursor.getString(cursor.getColumnIndex("nome")));

            listClasseFarmacologica.add(classeFarmacologica);
        }

        return listClasseFarmacologica;
    }

}
