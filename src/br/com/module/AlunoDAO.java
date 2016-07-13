package br.com.module;

import br.com.controle.Alunos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 01/06/16.
 */
public class AlunoDAO extends DAO {


    public void cadastrarAluno(Alunos p) throws Exception {

        abrirConexao();
         String query = "insert into alunos(mat,nome,email,idade) values(null,?,?,?)";
         pstnt = (PreparedStatement) conexao.prepareStatement(query);
         pstnt.setString(1, p.getNome());
         pstnt.setString(2, p.getEmail());
         pstnt.setInt(3, p.getIdade());
         pstnt.execute();

        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText("ATENÇÃO");
        al.setContentText("Aluno(a) inserido com sucesso!!!");
        al.show();
        fecharConexao();
    }

    public void alterarAluno(Alunos p) throws Exception {

        abrirConexao();
        String query = "update alunos set nome=?, email=?, idade=? where mat=?";
        pstnt = (PreparedStatement) conexao.prepareStatement(query);
        pstnt.setString(1, p.getNome());
        pstnt.setString(2, p.getEmail());
        pstnt.setInt(3, p.getIdade());
        pstnt.setInt(4, p.getMat());
        pstnt.execute();

        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText("ATENÇÃO");
        al.setContentText("Aluno(a) alterado com sucesso!!!");
        al.show();
        fecharConexao();
    }

    public void excluirAluno(Alunos p) throws Exception {

        abrirConexao();
        String query = "delete from alunos where mat=?";
        pstnt = (PreparedStatement) conexao.prepareStatement(query);
        pstnt.setInt(1, p.getMat());
        pstnt.execute();

        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText("ATENÇÃO");
        al.setContentText("Aluno(a) deletado com sucesso!!!");
        al.show();
        fecharConexao();
    }

    public List<Alunos> pesquisarAluno(String pesquisa) throws SQLException {

        ArrayList<Alunos> list = new ArrayList<>();
        try {
            abrirConexao();
            String query = "select * from alunos where nome like ?";
            pstnt = (PreparedStatement) conexao.prepareStatement(query);
            pstnt.setString(1, "%"+pesquisa+"%");
            rs = pstnt.executeQuery();
            rs = pstnt.executeQuery();

            while (rs.next()){
                Alunos a = new Alunos();

                a.setMat(rs.getInt("mat"));
                a.setNome(rs.getString("nome"));
                a.setEmail(rs.getString("email"));
                a.setIdade(rs.getInt("idade"));
                list.add(a);
            }
            fecharConexao();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return list;
    }

    public Integer quantidadeAlunos() throws Exception {
        //exibe a quantidade de produtos no banco de dados
        abrirConexao();
        String qt = "select count(*) as qtd from alunos";
        pstnt = (PreparedStatement) conexao.prepareStatement(qt);
        rs = pstnt.executeQuery();
        rs.first();
        Integer qtd = Integer.valueOf(rs.getInt("qtd"));
        fecharConexao();

        return qtd;
    }
}
