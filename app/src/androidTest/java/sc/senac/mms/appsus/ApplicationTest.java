package sc.senac.mms.appsus;

import android.test.ApplicationTestCase;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.entity.ClasseTerapeutica;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.manager.helpers.ExternalDB;
import sc.senac.mms.appsus.view.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<sc.senac.mms.appsus.Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    public void testPesquisaMedicamentosComORMLite() throws SQLException {

        this.createApplication();

        // Criar conexão com o banco de dados externo
        ExternalDB externalDB = new ExternalDB(getContext(), "medicamentos.db");

        // Buscar os medicamentos utilizando o framework ORMLite
        Dao<Medicamento, Long> dao = externalDB.getDao(Medicamento.class);
        List<Medicamento> med = dao.queryForAll();

        // Verificar se a consulta teve sucesso
        assertNotNull(med);

        // Verificar se foram retornados medicamentos
        assertTrue(med.size() > 0);
    }

    public void testGerenciadoresBancoDadosIniciados() {
        this.createApplication();

        this.getApplication().initializeManagers();

        assertNotNull(this.getApplication().getClasseTerapeuticaManager());
        assertNotNull(this.getApplication().getMedicamentoManager());
        assertNotNull(this.getApplication().getHistoricoManager());
    }

    public void testFiltrarMedicamentoPorDescricao() {

        MainActivity mainActivity = new MainActivity();

        List<Medicamento> medicamentos = getMedicamentos();
        List<Medicamento> medicamentosFiltrados = mainActivity.filtrarMedicamentosPorDescricao(medicamentos, "PARA");

        assertEquals(medicamentosFiltrados.size(), 1);
        assertEquals(medicamentosFiltrados.get(0).getIdMedicamento().longValue(), 27003L);
    }

    public void testFiltrarMedicamentoPorClasseTerapeutica() {

        MainActivity mainActivity = new MainActivity();

        List<ClasseTerapeutica> classes = new ArrayList<>();
        classes.add(new ClasseTerapeutica(6501, "ANALGESICO OPIOIDES E ANTAGONISTAS"));

        List<Medicamento> listaMedicamentos = getMedicamentos();
        List<Medicamento> medicamentosFiltrados = mainActivity.filtrarMedicamentosPorClasse(listaMedicamentos, classes);

        assertEquals(medicamentosFiltrados.size(), 2);
        assertEquals(medicamentosFiltrados.get(0).getClasseTerapeutica().getIdClasse().longValue(), 6501);
        assertEquals(medicamentosFiltrados.get(1).getClasseTerapeutica().getIdClasse().longValue(), 6501);

    }

    private List<Medicamento> getMedicamentos() {

        List<Medicamento> medicamentos = new ArrayList<>();

        ClasseTerapeutica analgesico_opioide = new ClasseTerapeutica(6501, "ANALGESICO OPIOIDES E ANTAGONISTAS");
        ClasseTerapeutica analgesico_local = new ClasseTerapeutica(6502, "ANESTESICOS LOCAIS");

        Medicamento morfina = new Medicamento();
        morfina.setIdMedicamento(19002);
        morfina.setFormaApresentacao("AMPOLA");
        morfina.setDescricao("MORFINA, SULFATO - 1MG/ML");
        morfina.setClasseTerapeutica(analgesico_opioide);

        Medicamento paracetamol = new Medicamento();
        paracetamol.setIdMedicamento(27003);
        paracetamol.setDescricao("PARACETAMOL - 500MG");
        paracetamol.setFormaApresentacao("COMPRIMIDO");
        paracetamol.setClasseTerapeutica(analgesico_opioide);

        Medicamento flumazenil = new Medicamento();
        flumazenil.setIdMedicamento(116002);
        flumazenil.setFormaApresentacao("AMPOLA");
        flumazenil.setDescricao("FLUMAZENIL 0,5MG, 0,1MG/ML,SOL.INJ. AMPOLA 5ML");
        flumazenil.setClasseTerapeutica(analgesico_local);

        medicamentos.add(paracetamol);
        medicamentos.add(flumazenil);
        medicamentos.add(morfina);

        return medicamentos;
    }

}