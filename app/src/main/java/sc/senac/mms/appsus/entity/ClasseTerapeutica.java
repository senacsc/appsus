package sc.senac.mms.appsus.entity;

import android.content.ContentValues;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

@DatabaseTable(tableName = "classe_terapeutica")
public class ClasseTerapeutica implements Serializable {

    @DatabaseField(generatedId = true)
    private Long idClasse;

    @DatabaseField
    private String nome;

    @DatabaseField
    private String descricao;

    public ClasseTerapeutica() {
    }

    public ClasseTerapeutica(Long idClasse, String nome) {
        this.idClasse = idClasse;
        this.nome = nome;
    }

    public Long getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(Long idClasse) {
        this.idClasse = idClasse;
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

    public ArrayList<ClasseTerapeutica> getClassesTerapeuticas() {
        ArrayList<ClasseTerapeutica> classes = new ArrayList<>();
        classes.add(new ClasseTerapeutica(1L, "ANALGESICOS"));
        classes.add(new ClasseTerapeutica(2L, "ANESTESICOS E COADJUVANTES"));
        return classes;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("nome", this.getNome());
        return values;
    }

}
