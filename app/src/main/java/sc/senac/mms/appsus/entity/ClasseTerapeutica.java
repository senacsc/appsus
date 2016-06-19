package sc.senac.mms.appsus.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Objects;

@DatabaseTable(tableName = "classe_terapeutica")
public class ClasseTerapeutica implements Serializable {

    @DatabaseField(id = true)
    private Long idClasse;

    @DatabaseField(columnName = "nomeClasse", canBeNull = false)
    private String nome;

    @DatabaseField(columnName = "descricaoClasse")
    private String descricao;

    // region ## Constructors ##
    public ClasseTerapeutica() {
    }

    public ClasseTerapeutica(Number idClasse, String nome) {
        this.idClasse = idClasse.longValue();
        this.nome = nome;
    }
    // endregion

    // region ## Get's and Set's ##
    public Long getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(Number idClasse) {
        this.idClasse = idClasse.longValue();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    // endregion

    // region ## Equality Comparators and toString ##
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClasseTerapeutica that = (ClasseTerapeutica) o;
        return Objects.equals(idClasse, that.idClasse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClasse);
    }

    @Override
    public String toString() {
        return this.getNome();
    }
    // endregion

}
