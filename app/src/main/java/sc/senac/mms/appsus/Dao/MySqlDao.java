package sc.senac.mms.appsus.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sc.senac.mms.appsus.Model.ClasseFarmacologica;
import sc.senac.mms.appsus.Model.Medicamento;

/**
 * Created by Milton on 23/05/2016.
 */
public class MySqlDao {

    Connection conexao;
    ResultSet rs;

    public MySqlDao() throws SQLException {
        this.conexao = CriaConexao.getConnection();
    }


    public ArrayList<Medicamento> getListaMedicamentoMySql() throws SQLException {

        String sql = "SELECT forma_de_apresentação, descrição2, descrição1 FROM medicamentos";
        PreparedStatement stm = this.conexao.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        int c = 0;
        ArrayList<Medicamento> listaMedicamento = new ArrayList<Medicamento>();

        while (rs.next()) {

            Medicamento medicamento = new Medicamento();
            ClasseFarmacologica classeFarmacologica = new ClasseFarmacologica();

            medicamento.setFormaApresentacao(rs.getString("forma_de_apresentação"));
            medicamento.setDescricao(rs.getString("descrição1"));
            classeFarmacologica.setNome(rs.getString("Descrição2"));
            medicamento.setClasseFarmacologica(classeFarmacologica);
            listaMedicamento.add(medicamento);
            c++;
        }
        System.out.println("Foi adicionado " + c);
        rs.close();
        stm.close();

        return listaMedicamento;

    }


}
