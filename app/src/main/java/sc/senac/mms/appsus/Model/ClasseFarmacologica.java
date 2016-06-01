package sc.senac.mms.appsus.model;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Milton on 18/05/2016.
 */
public class ClasseFarmacologica implements Serializable {

    private int id;
    private String nome;

    public ClasseFarmacologica() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<ClasseFarmacologica> getListaClasseFarmacologica() {

        ArrayList<ClasseFarmacologica> listClasse = new ArrayList<>();

        ClasseFarmacologica classeFarmacologica1 = new ClasseFarmacologica();
        classeFarmacologica1.setId(1);
        classeFarmacologica1.setNome("ANALGESICOS");

        ClasseFarmacologica classeFarmacologica2 = new ClasseFarmacologica();
        classeFarmacologica2.setId(2);
        classeFarmacologica2.setNome("ANESTESICOS E COADJUVANTES");

        listClasse.add(classeFarmacologica1);
        listClasse.add(classeFarmacologica2);

        return listClasse;
    }


    public ContentValues getContentValues() {


        ContentValues valoresDaTabela = new ContentValues();
        valoresDaTabela.put("nome", this.getNome());

        return valoresDaTabela;

    }

    @Override
    public String toString() {
        return nome;
    }
}
