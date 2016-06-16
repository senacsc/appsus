package sc.senac.mms.appsus.entity;

import android.content.ContentValues;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

@DatabaseTable(tableName = "medicamento")
public class Medicamento implements Serializable {

    @DatabaseField(generatedId = true, columnName = "idMedicamento")
    private int idMedicamento;

    //@DatabaseField(canBeNull = false)
    private String descricao;

    //@DatabaseField
    private String formaApresentacao;

    //@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private ClasseTerapeutica classeTerapeutica;

    // region ## GET's and SET's ##
    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
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

    public ClasseTerapeutica getClasseTerapeutica() {
        return classeTerapeutica;
    }

    public void setClasseTerapeutica(ClasseTerapeutica classeTerapeutica) {
        this.classeTerapeutica = classeTerapeutica;
    }
    // endregion

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("descricao", this.getDescricao());
        values.put("forma_apresentacao", this.getFormaApresentacao());
        values.put("classefarmacologica_idclassefarmacologica", classeTerapeutica.getIdClasse());
        return values;
    }

    public ArrayList<Medicamento> getListaMedicamentos() {

        ClasseTerapeutica ctA = new ClasseTerapeutica(1L, "ANALGESICOS");
        ClasseTerapeutica ctB = new ClasseTerapeutica(2L, "ANESTESICOS E COADJUVANTES");

        Medicamento m1 = new Medicamento();
        m1.setIdMedicamento(1);
        m1.setDescricao("FRASCO");
        m1.setFormaApresentacao("PARACETAMOL, GOTAS - 200MG/ML (FR. 10 A 15ML)");
        m1.setClasseTerapeutica(ctA);

        Medicamento m2 = new Medicamento();
        m2.setIdMedicamento(2);
        m2.setDescricao("FRASCO AMPOLA");
        m2.setFormaApresentacao("BUPIVACAINA,CLORIDRATO VC(EMB.EST)0,25%,2,5MG/MLSOLU.I,F/A20");
        m2.setClasseTerapeutica(ctB);

        ArrayList<Medicamento> listMedicamentos = new ArrayList<>();
        listMedicamentos.add(m1);
        listMedicamentos.add(m2);

        return listMedicamentos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Medicamento{");
        sb.append("idMedicamento=").append(idMedicamento);
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", formaApresentacao='").append(formaApresentacao).append('\'');
        sb.append(", classeTerapeutica=").append(classeTerapeutica);
        sb.append('}');
        return sb.toString();
    }
}
