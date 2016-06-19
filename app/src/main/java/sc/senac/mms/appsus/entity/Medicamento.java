package sc.senac.mms.appsus.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
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
        return descricao;
    }
    // endregion
}
