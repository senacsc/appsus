package sc.senac.mms.appsus.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.view.adapter.MedicamentoAdapter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    private Application application;
    private RecyclerView recyclerViewMedicamentos;
    private List<Medicamento> medicamentoListModel;
    private MedicamentoAdapter medicamentoAdapter;
    private Menu mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Salva uma refêrencia da aplicação para auxiliar no acesso
        // dos gerenciadores dos bancos de dados
        this.application = (Application) getApplication();

        /**
         * Inicia uma view de medicamentos otimizada que mostra somente os itens
         * que cabem na tela do usuário ao invés da lista inteira de medicamentos.
         *
         * Mais Informações em: <https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html>
         */
        recyclerViewMedicamentos = (RecyclerView) findViewById(R.id.recyclerViewMedicamentos);
        recyclerViewMedicamentos.setLayoutManager(new LinearLayoutManager(this));

        loadMedicamentoList();
    }

    /**
     * Carrega todos os medicamentos do banco de dados (medicamentos.db)
     * e adiciona-os na interface (tela) dentro de uma recyclerView
     */
    public void loadMedicamentoList() {

        try {
            // Busca e atualiza a lista de medicamentos
            medicamentoListModel = this.application.getMedicamentoManager().buscarMedicamentos();
        } catch (SQLException ex) {
            medicamentoListModel = new ArrayList<>();
            Log.e(this.getClass().getSimpleName(), "Failed to load medicamentos list data.", ex);
        }

        // Atualiza a interface com os medicamentos
        atualizarListaMedicamentos();
    }

    /**
     * Atualiza a interface com a nova lista de medicamentos
     */
    public void atualizarListaMedicamentos() {
        medicamentoAdapter = new MedicamentoAdapter(medicamentoListModel);
        recyclerViewMedicamentos.setAdapter(medicamentoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        final MenuItem item = menu.findItem(R.id.search_action);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryRefinementEnabled(true);
        searchView.setQueryHint("Pesquisar...");
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, this);

        this.mainMenu = menu;

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);

            final MenuItem item = this.mainMenu.findItem(R.id.search_action);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

            searchView.setQuery(query, false);

            Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * TODO: Document method "onQueryTextChange" of "MainActivity" class
     *
     * @param query Texto da busca
     * @return retorna-se verdadeiro para customizar o filtro da lista
     */
    @Override
    public boolean onQueryTextChange(String query) {
        final List<Medicamento> filteredList = this.filtrarMedicamentos(medicamentoListModel, query);
        medicamentoAdapter.updateList(filteredList);
        return true;
    }

    /**
     * Filtra os medicamentos relacionados ao texto da pesquisa
     *
     * @param listModel Lista completa de medicamentos
     * @param query     Texto da pesquisa
     * @return Lista com os medicamentos fitrados
     */
    private List<Medicamento> filtrarMedicamentos(List<Medicamento> listModel, String query) {

        // Cria uma nova lista para os medicamentos filtrados
        final List<Medicamento> medicamentosFiltrados = new ArrayList<>();

        // Remove espaços em branco antes e depois do texto da pesquisa
        // e converte as letras para minúsculo
        String searchQuery = query.trim().toLowerCase();

        // Filtra todos os medicamentos na qual a campo descrição
        // contenha as letras do texto da pesquisa
        for (Medicamento m : listModel) {

            // Remove espaços em branco e converte as letras para minúsculo
            // dos dados do medicamento
            String descricao = m.getDescricao().trim().toLowerCase();
            String formaApresentacao = m.getFormaApresentacao().trim().toLowerCase();

            // Verifica se a descrição ou forma de apresentação
            // contém a sequência de caracteres da pesquisa
            if (descricao.contains(searchQuery) || formaApresentacao.contains(searchQuery)) {
                medicamentosFiltrados.add(m);
            }
        }

        // Retona a nova lista contendo os medicamentos filtrados
        return medicamentosFiltrados;
    }

    // ignored
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Do something when expanded
     *
     * @param item Item Selecionado
     * @return expand the view
     */
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        // Return true to expand action view
        return true;
    }

    /**
     * Do something when collapsed
     *
     * @param item Item Selecionado
     * @return collapse the view
     */
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        medicamentoAdapter.updateList(medicamentoListModel);
        // Return true to collapse action view
        return true;
    }
}
