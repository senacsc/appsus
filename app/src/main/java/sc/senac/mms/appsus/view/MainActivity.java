package sc.senac.mms.appsus.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.ClasseTerapeutica;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.view.adapter.MedicamentoAdapter;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener, Drawer.OnDrawerItemClickListener {

    private Application application;
    private List<Medicamento> medicamentoListModel;
    private List<Medicamento> filteredMedicamentoList;
    private List<ClasseTerapeutica> classesTerapeuticas;
    private RecyclerView recyclerViewMedicamentos;
    private MedicamentoAdapter medicamentoAdapter;
    private Bundle savedInstance;
    private Drawer menuLateral;
    private ActionBar toolbar;
    private Menu mainMenu;

    // Menu identifiers
    public static long MENU_ITEM_MEDICAMENTOS = 1L;
    public static long MENU_ITEM_HISTORICO = 2L;
    public static long MENU_ITEM_SOBRE = 3L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Altera o menu do aplicativo para um customizado
        // que permite o uso integrado com o menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.toolbar = getSupportActionBar();
        this.savedInstance = savedInstanceState;
        this.classesTerapeuticas = new ArrayList<>();

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
        recyclerViewMedicamentos.scrollToPosition(0);

        // Inicializa o menu lateral
        menuLateral = new DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .withRootView(R.id.drawer_layout)
            .withDisplayBelowStatusBar(true)
            .withTranslucentStatusBar(false)
            .withActionBarDrawerToggleAnimated(true)
            .addDrawerItems(
                new PrimaryDrawerItem().withName("Medicamentos").withIdentifier(MENU_ITEM_MEDICAMENTOS).withIcon(R.drawable.ic_medicamento),
                new PrimaryDrawerItem().withName("Histórico").withIdentifier(MENU_ITEM_HISTORICO).withIcon(R.drawable.ic_history_black_24dp),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName("Sobre").withIdentifier(MENU_ITEM_SOBRE)
            )
            // Restaura o estado da barra lateral caso o usuário mude a orientação da tela
            .withSavedInstance(savedInstanceState)
            .withOnDrawerItemClickListener(this)
            .build();

        // Carrega a lista de medicamentos cadastrados, essa é a única interação direta
        // com o banco de dados nessa activity, todas as próximas interações serão a partir
        // da lista de objetos salvos na memória
        loadMedicamentoList();

        VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller);
        SectionTitleIndicator sectionTitleIndicator = (SectionTitleIndicator) findViewById(R.id.fast_scroller_section_title_indicator);

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        fastScroller.setRecyclerView(recyclerViewMedicamentos);
        fastScroller.setSectionIndicator(sectionTitleIndicator);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        recyclerViewMedicamentos.addOnScrollListener(fastScroller.getOnScrollListener());

        medicamentoAdapter.setOnItemClickListener(new MedicamentoAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Medicamento m = medicamentoAdapter.getItem(position);

                MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                    .title(m.getDescricao())
                    .customView(R.layout.medicamento_dialog, true)
                    .positiveText("FECHAR")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    }).build();

                TextView classeTextView = (TextView) dialog.getCustomView().findViewById(R.id.classeTerapeuticaLabel);
                classeTextView.setText(m.getClasseTerapeutica().getNome());

                TextView formaTextView = (TextView) dialog.getCustomView().findViewById(R.id.formaApresentacaoLabel);
                formaTextView.setText(m.getFormaApresentacao());

                dialog.show();
            }

            @Override
            public boolean onItemLongClick(int position, View v) {
                Log.d("LONG_CLICK", "onItemClick: Olá");
                return true;
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState = menuLateral.saveInstanceState(outState);

        final MenuItem item = mainMenu.findItem(R.id.search_action);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        outState.putString("query", searchView.getQuery().toString());

        super.onSaveInstanceState(outState);
    }

    /**
     * Sobreescreve o método onBackPressed para que quando o usuário
     * pressionar o botão de voltar (ou sair) o menu lateral se feche
     * ao invés de fechar o aplicativo.
     * <p/>
     * Caso o menu estiver fechado a função original será executada.
     */
    @Override
    public void onBackPressed() {
        if (menuLateral != null && menuLateral.isDrawerOpen()) {
            menuLateral.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Carrega todos os medicamentos do banco de dados (medicamentos.db)
     * e adiciona-os na interface (tela) dentro de uma recyclerView
     */
    public void loadMedicamentoList() {

        // Busca todos os medicamentos cadastrados
        try {
            medicamentoListModel = this.application.getMedicamentoManager().buscarMedicamentos();
        } catch (SQLException ex) {
            medicamentoListModel = new ArrayList<>();
            Log.e(this.getClass().getSimpleName(), "Failed to load medicamentos list data.", ex);
        }

        // Faz uma cópia da lista de medicamentos e salva em uma variável
        // para utilização no filtro de pesquisa
        this.filteredMedicamentoList = medicamentoListModel;

        // Registra o adapter da lista de medicamentos
        this.medicamentoAdapter = new MedicamentoAdapter(medicamentoListModel);
        this.recyclerViewMedicamentos.setAdapter(medicamentoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Configura o menu do aplicativo de acordo
        // com os itens no arquivo "menu/main_menu.xml"
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        final MenuItem item = menu.findItem(R.id.search_action);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        // Configura o componente de pesquisa para permitir a busca dentro do aplicativo
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryRefinementEnabled(true);
        searchView.setQueryHint("Pesquisar...");
        searchView.setOnQueryTextListener(this);

        // Remove o limite de largura da barra de pesquisa
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // Recebe eventos de expansão e recolhimento do compontente de pesquisa
        MenuItemCompat.setOnActionExpandListener(item, this);

        // Salva uma referência ao menu do aplicativo afim de ter
        // acesso ao componente de pesquisa
        this.mainMenu = menu;

        // Mantem a pesquisa do usuário caso ele mude a orientação do celular (horizontal - vertical)
        if (savedInstance != null) {
            String query = savedInstance.getString("query");
            if (query != null && query.length() > 0) {
                item.expandActionView();
                searchView.setQuery(savedInstance.getString("query"), true);
                searchView.clearFocus();
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Esse evento será chamado quando o usuário
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            final String query = intent.getStringExtra(SearchManager.QUERY);
            final MenuItem item = this.mainMenu.findItem(R.id.search_action);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

            searchView.setQuery(query, false);
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

        List<Medicamento> medicamentos = filtrarMedicamentosPorDescricao(filteredMedicamentoList, query);
        atualizarListaMedicamentos(medicamentos);

        return true;
    }

    /**
     * Filtra os medicamentos relacionados ao texto da pesquisa
     *
     * @param listModel Lista completa de medicamentos
     * @param query     Texto da pesquisa
     * @return Lista com os medicamentos fitrados
     */
    private List<Medicamento> filtrarMedicamentosPorDescricao(List<Medicamento> listModel, String query) {

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

    private List<Medicamento> filtrarMedicamentosPorClasse(List<Medicamento> medicamentos, List<ClasseTerapeutica> classes) {
        final List<Medicamento> medicamentosFiltrados = new ArrayList<>();
        for (Medicamento m : medicamentos) {
            if (classes.contains(m.getClasseTerapeutica())) {
                medicamentosFiltrados.add(m);
            }
        }
        return medicamentosFiltrados;
    }

    private void atualizarListaMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentoAdapter.updateList(medicamentos);
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
        menuLateral.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        toolbar.setDisplayHomeAsUpEnabled(false);
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
        atualizarListaMedicamentos(filteredMedicamentoList);
        toolbar.setDisplayHomeAsUpEnabled(false);
        menuLateral.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        return true;
    }

    private void abrirModalSelecionarClasse() {

        final ArrayList<ClasseTerapeutica> listClasses = new ArrayList<>();

        try {
            listClasses.addAll(application.getClasseTerapeuticaManager().buscarClasses());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        new MaterialDialog.Builder(this)
            .title("Filtrar por Classe Farmacológica")
            .items(listClasses)
            .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                @Override
                public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                    ArrayList<ClasseTerapeutica> classes = new ArrayList<>();
                    for (Integer aWhich : which) {
                        classes.add(listClasses.get(aWhich));
                    }
                    classesTerapeuticas = classes;
                    return true;
                }
            })
            .neutralText("Limpar")
            .negativeText("Cancelar")
            .positiveText("Filtrar")
            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    classesTerapeuticas = new ArrayList<>();
                }
            })
            .onAny(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    List<Medicamento> medicamentos = medicamentoListModel;

                    if (classesTerapeuticas.size() > 0) {
                        medicamentos = filtrarMedicamentosPorClasse(medicamentos, classesTerapeuticas);
                        filteredMedicamentoList = medicamentos;
                    } else {
                        filteredMedicamentoList = medicamentoListModel;
                    }

                    final MenuItem item = mainMenu.findItem(R.id.search_action);
                    final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

                    medicamentos = filtrarMedicamentosPorDescricao(medicamentos, searchView.getQuery().toString());
                    atualizarListaMedicamentos(medicamentos);
                }
            })
            .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_action:
                abrirModalSelecionarClasse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

        if (drawerItem.getIdentifier() == MENU_ITEM_HISTORICO) {
            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
        } else if (drawerItem.getIdentifier() == MENU_ITEM_MEDICAMENTOS) {
            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
        } else if (drawerItem.getIdentifier() == MENU_ITEM_SOBRE) {
            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
