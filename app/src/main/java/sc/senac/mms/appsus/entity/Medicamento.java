package sc.senac.mms.appsus.entity;

import android.content.ContentValues;
import android.widget.Filterable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@DatabaseTable(tableName = "medicamento")
public class Medicamento implements Serializable {

    @DatabaseField(generatedId = true, columnName = "idMedicamento")
    private Long idMedicamento;

    @DatabaseField(columnName = "nomeMedicamento")
    private String descricao;

    @DatabaseField
    private String formaApresentacao;

    @DatabaseField(canBeNull = false, columnName = "classeTerapeutica", foreign = true, foreignAutoRefresh = true)
    private ClasseTerapeutica classeTerapeutica;

    // region ## GET's and SET's ##
    public Long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Number idMedicamento) {
        this.idMedicamento = idMedicamento.longValue();
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

    // region ## Constructors ##
    public Medicamento() {
    }

    public Medicamento(Number idMedicamento, String descricao) {
        this.idMedicamento = idMedicamento.longValue();
        this.descricao = descricao;
    }

    public Medicamento(Number idMedicamento, String descricao, String formaApresentacao) {
        this.idMedicamento = idMedicamento.longValue();
        this.descricao = descricao;
        this.formaApresentacao = formaApresentacao;
    }

    public Medicamento(Number idMedicamento, String descricao, String formaApresentacao, ClasseTerapeutica classeTerapeutica) {
        this.idMedicamento = idMedicamento.longValue();
        this.descricao = descricao;
        this.formaApresentacao = formaApresentacao;
        this.classeTerapeutica = classeTerapeutica;
    }
    // endregion

    // region ## Equality Comparators and toString ##
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicamento that = (Medicamento) o;
        return Objects.equals(idMedicamento, that.idMedicamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMedicamento);
    }

    @Override
    public String toString() {
//        final StringBuilder sb = new StringBuilder("Medicamento{");
//        sb.append("idMedicamento=").append(idMedicamento);
//        sb.append(", descricao='").append(descricao).append('\'');
//        sb.append(", formaApresentacao='").append(formaApresentacao).append('\'');
//        sb.append(", classeTerapeutica=").append(classeTerapeutica);
//        sb.append('}');
        return descricao;
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

        ClasseTerapeutica ctA = new ClasseTerapeutica(1, "ANALGESICOS");
        ClasseTerapeutica ctB = new ClasseTerapeutica(2, "ANESTESICOS E COADJUVANTES");

        Medicamento m1 = new Medicamento();
        m1.setIdMedicamento(1L);
        m1.setDescricao("FRASCO");
        m1.setFormaApresentacao("PARACETAMOL, GOTAS - 200MG/ML (FR. 10 A 15ML)");
        m1.setClasseTerapeutica(ctA);

        Medicamento m2 = new Medicamento();
        m2.setIdMedicamento(2L);
        m2.setDescricao("FRASCO AMPOLA");
        m2.setFormaApresentacao("BUPIVACAINA,CLORIDRATO VC(EMB.EST)0,25%,2,5MG/MLSOLU.I,F/A20");
        m2.setClasseTerapeutica(ctB);

        ArrayList<Medicamento> listMedicamentos = new ArrayList<>();
        listMedicamentos.add(m1);
        listMedicamentos.add(m2);

        return listMedicamentos;
    }
}
