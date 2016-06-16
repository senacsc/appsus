package sc.senac.mms.appsus.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.Objects;

@DatabaseTable
public class Historico {

    @DatabaseField(generatedId = true)
    private Integer idHistorico;

    @DatabaseField
    private Date dtVisualizacao;

    @DatabaseField(foreign = true)
    private Medicamento medicamento;

    public Historico() {
    }

    public Historico(Integer idHistorico, Date dtVisualizacao, Medicamento medicamento) {
        this.idHistorico = idHistorico;
        this.dtVisualizacao = dtVisualizacao;
        this.medicamento = medicamento;
    }

    public Integer getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Date getDtVisualizacao() {
        return dtVisualizacao;
    }

    public void setDtVisualizacao(Date dtVisualizacao) {
        this.dtVisualizacao = dtVisualizacao;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Historico historico = (Historico) o;
        return Objects.equals(dtVisualizacao, historico.dtVisualizacao) &&
            Objects.equals(medicamento, historico.medicamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dtVisualizacao, medicamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Historico{");
        sb.append("idHistorico=").append(idHistorico);
        sb.append(", dtVisualizacao=").append(dtVisualizacao);
        sb.append(", medicamento=").append(medicamento);
        sb.append('}');
        return sb.toString();
    }
}
