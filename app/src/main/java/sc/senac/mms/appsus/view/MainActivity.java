package sc.senac.mms.appsus.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.ClasseTerapeutica;
import sc.senac.mms.appsus.entity.Medicamento;
import sc.senac.mms.appsus.view.fragments.HistoricoFragment;
import sc.senac.mms.appsus.view.fragments.MedicamentosFragment;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener, Drawer.OnDrawerItemClickListener {

    private Application application;

    public List<Medicamento> medicamentoListModel;
    public List<Medicamento> filteredMedicamentoList;
    public List<ClasseTerapeutica> classesTerapeuticas;

    private Bundle savedInstance;
    private Drawer menuLateral;
    private ActionBar toolbar;
    public Menu mainMenu;

    // Menu identifiers
    public static final int MENU_ITEM_MEDICAMENTOS = 1;
    public static final int MENU_ITEM_HISTORICO = 2;
    public static final int MENU_ITEM_SOBRE = 3;

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

        // Inicializa o menu lateral
        menuLateral = new DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .withRootView(R.id.drawer_layout)
            .withDisplayBelowStatusBar(true)
            .withTranslucentStatusBar(false)
            .withActionBarDrawerToggleAnimated(true)
            .addDrawerItems(
                new PrimaryDrawerItem()
                    .withName(R.string.lista_medicamentos)
                    .withIdentifier(MENU_ITEM_MEDICAMENTOS)
                    .withIcon(R.drawable.ic_medicamento),
                new PrimaryDrawerItem()
                    .withName(R.string.historico)
                    .withIdentifier(MENU_ITEM_HISTORICO)
                    .withIcon(R.drawable.ic_history_black_24dp),
                new DividerDrawerItem(),
                new PrimaryDrawerItem()
                    .withName(R.string.sobre)
                    .withIdentifier(MENU_ITEM_SOBRE)
                    .withSelectable(false)
            )
            // Restaura o estado da barra lateral caso o usuário mude a orientação da tela
            .withSavedInstance(savedInstanceState)
            .withOnDrawerItemClickListener(this)
            .build();

        // Carrega a lista de medicamentos cadastrados, essa é a única interação direta
        // com o banco de dados nessa activity, todas as próximas interações serão a partir
        // da lista de objetos salvos na memória
        loadMedicamentoList();

        // Alterar fragment para a lista de medicamentos
        MedicamentosFragment fragment = new MedicamentosFragment();
        alterarFragment(fragment);
    }

    public void alterarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Configura o menu do aplicativo de acordo
        // com os itens no arquivo "menu/main_menu.xml"
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Salva uma referência ao menu do aplicativo afim de ter
        // acesso ao componente de pesquisa
        this.mainMenu = menu;

        final MenuItem item = mainMenu.findItem(R.id.search_action);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        // Configura o componente de pesquisa para permitir a busca dentro do aplicativo
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryRefinementEnabled(true);
        searchView.setQueryHint(getString(R.string.pesquisar));
        searchView.setOnQueryTextListener(this);

        // Remove o limite de largura da barra de pesquisa
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // Recebe eventos de expansão e recolhimento do compontente de pesquisa
        MenuItemCompat.setOnActionExpandListener(item, this);

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
     * Esse evento será chamado quando o usuário clicar em pesquisar
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            final String query = intent.getStringExtra(SearchManager.QUERY);
            final MenuItem item = mainMenu.findItem(R.id.search_action);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

            searchView.setQuery(query, false);
        }
    }

    /**
     * Evento chamado quando há alteração no texto da pesquisa
     *
     * @param query Texto da busca
     * @return retorna-se verdadeiro para customizar o filtro da lista
     */
    @Override
    public boolean onQueryTextChange(String query) {

        List<Medicamento> medicamentos = filtrarMedicamentosPorDescricao(filteredMedicamentoList, query);

        Fragment fragment = this.getFragmentAtual();

        if (fragment instanceof MedicamentosFragment) {
            ((MedicamentosFragment) fragment).atualizarListaMedicamentos(medicamentos);
        }

        return true;
    }

    public Fragment getFragmentAtual() {
        return this.getSupportFragmentManager().findFragmentById(R.id.frame_container);
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

        Fragment fragment = this.getFragmentAtual();

        if (fragment instanceof MedicamentosFragment) {
            ((MedicamentosFragment) fragment).atualizarListaMedicamentos(filteredMedicamentoList);
        }

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

        List<Integer> classesSelecionadas = new ArrayList<>();

        for (int i = 0; i < listClasses.size(); i++) {
            if (classesTerapeuticas.contains(listClasses.get(i))) {
                classesSelecionadas.add(i);
            }
        }

        Integer[] selecionados = new Integer[classesSelecionadas.size()];
        for (int i = 0; i < classesSelecionadas.size(); i++) {
            selecionados[i] = classesSelecionadas.get(i);
        }

        new MaterialDialog.Builder(this)
            .title(R.string.filtro_dialog_title)
            .items(listClasses)
            .itemsCallbackMultiChoice(selecionados, new MaterialDialog.ListCallbackMultiChoice() {
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
            .neutralText(R.string.limpar)
            .negativeText(R.string.cancelar)
            .positiveText(R.string.filtrar)
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

                    Fragment fragment = getFragmentAtual();

                    if (fragment instanceof MedicamentosFragment) {
                        ((MedicamentosFragment) fragment).atualizarListaMedicamentos(medicamentos);
                    }
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

    /**
     * Mostrar modal com informações do aplicativo
     */
    @SuppressWarnings("StringBufferReplaceableByString")
    public void mostrarInformacoesAplicativo() {

        new MaterialDialog.Builder(this)
            .title("Sobre")
            .content(Html.fromHtml(
                new StringBuilder()
                    .append("Aplicativo desenvolvido como trabalho de conclusão de semestre do curso de Análise e ")
                    .append("Desenvolvimento de Sistemas da faculdade SENAC de Florianópolis - SC.<br/><br/>")
                    .append("<b>Integrantes:</b> <br/><br/>")
                    .append("Matheus Vitória Garcez<br/>")
                    .append("Milton Andrade Junior")
                    .toString()
                )
            )
            .positiveText(getString(R.string.fechar).toUpperCase())
            .show();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

        drawerItem.withSetSelected(false);

        Fragment fragment;

        switch ((int) drawerItem.getIdentifier()) {
            case MENU_ITEM_HISTORICO:
                fragment = new HistoricoFragment();
                alterarFragment(fragment);
                break;
            case MENU_ITEM_MEDICAMENTOS:
                fragment = new MedicamentosFragment();
                alterarFragment(fragment);
                break;
            case MENU_ITEM_SOBRE:
                mostrarInformacoesAplicativo();
                break;
        }

        return false;
    }
}
