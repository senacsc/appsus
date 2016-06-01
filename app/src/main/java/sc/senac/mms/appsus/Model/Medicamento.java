package sc.senac.mms.appsus.model;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Milton on 18/05/2016.
 */
public class Medicamento implements Serializable {

    private int id;
    private String descricao;
    private String formaApresentacao;
    private ClasseFarmacologica classeFarmacologica;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFormaApresentacao() {
        return formaApresentacao;
    }

    public void setFormaApresentacao(String formaApresentacao) {
        this.formaApresentacao = formaApresentacao;
    }

    public ClasseFarmacologica getClasseFarmacologica() {
        return classeFarmacologica;
    }

    public void setClasseFarmacologica(ClasseFarmacologica classeFarmacologica) {
        this.classeFarmacologica = classeFarmacologica;
    }

    public ContentValues getContentValues() {


        ContentValues valoresDaTabela = new ContentValues();
        valoresDaTabela.put("descricao", this.getDescricao());
        valoresDaTabela.put("forma_apresentacao", this.getFormaApresentacao());
        valoresDaTabela.put("classefarmacologica_idclassefarmacologica", this.getClasseFarmacologica().getId());

        return valoresDaTabela;

    }

    public ArrayList<Medicamento> getListaMedicamentos() {

        ArrayList<Medicamento> listMedicamento = new ArrayList<>();

        ClasseFarmacologica classeFarmacologica1 = new ClasseFarmacologica();
        classeFarmacologica1.setId(1);
        classeFarmacologica1.setNome("ANALGESICOS");

        ClasseFarmacologica classeFarmacologica2 = new ClasseFarmacologica();
        classeFarmacologica2.setId(2);
        classeFarmacologica2.setNome("ANESTESICOS E COADJUVANTES");

        Medicamento medicamento1 = new Medicamento();
        medicamento1.setId(1);
        medicamento1.setDescricao("FRASCO");
        medicamento1.setFormaApresentacao("PARACETAMOL, GOTAS - 200MG/ML (FR. 10 A 15ML)");
        medicamento1.setClasseFarmacologica(classeFarmacologica1);

        Medicamento medicamento2 = new Medicamento();
        medicamento2.setId(2);
        medicamento2.setDescricao("FRASCO AMPOLA");
        medicamento2.setFormaApresentacao("BUPIVACAINA,CLORIDRATO VC(EMB.EST)0,25%,2,5MG/MLSOLU.I,F/A20");
        medicamento2.setClasseFarmacologica(classeFarmacologica2);

        listMedicamento.add(medicamento1);
        listMedicamento.add(medicamento2);

        return listMedicamento;
    }


    @Override
    public String toString() {
        return formaApresentacao + " - " + classeFarmacologica.getNome();
    }
}
