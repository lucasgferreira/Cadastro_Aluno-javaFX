package br.com.controle;

import br.com.module.AlunoDAO;
import br.com.module.DAO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by lucas on 01/06/16.
 */
public class Tela extends DAO{

    @FXML
    public TextField TFnome;
    @FXML
    public TextField TFemail;
    @FXML
    public TextField TFidade;
    @FXML
    public TableView<Alunos> TValuno;

    public Button BTnovo;
    public Button BTsalvar;
    public Button BTalterar;
    public Button BTexcluir;
    public TextField TFpesquisar;
    @FXML
    private Label LBqunatidade;
    private String message = null;


    @FXML
    public void initialize() {
        MaskForm mf = new MaskForm();//classe para aplicar mascara em campos de texto
        mf.maxField(TFnome, 45);
        mf.maxField(TFemail, 45);
        mf.maxField(TFidade, 9);
        mf.positionCaret(TFidade);
        mf.mascaraNumeroInteiro(TFidade);

        AlunoDAO cp = new AlunoDAO();
        caregarTabela();
        try {

            LBqunatidade.setText(String.valueOf(cp.quantidadeAlunos()));

        }catch (Exception e){
            System.out.print(e.getMessage());
        }

        TValuno.getSelectionModel().selectedItemProperty().addListener((event, oldValue, newValue) -> {
            unbindData(oldValue);
            bindData(newValue);
        });

        //se algum item na tabela for selecionado desativa o botão salvar
        BTsalvar.disableProperty().bind(TValuno.getSelectionModel().selectedItemProperty().isNotNull());
        //se algum item na tabela não estiver selecionado desativa os botões alterar e excluir
        BTalterar.disableProperty().bind(TValuno.getSelectionModel().selectedItemProperty().isNull());
        BTexcluir.disableProperty().bind(TValuno.getSelectionModel().selectedItemProperty().isNull());

        TFpesquisar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                TValuno.setItems(FXCollections.observableArrayList(cp.pesquisarAluno("")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void caregarTabela(){
        AlunoDAO cp = new AlunoDAO();
        try {
            TValuno.setItems(FXCollections.observableArrayList(cp.pesquisarAluno("")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bindData(Alunos a){
        if (a != null){
            TFnome.textProperty().bindBidirectional(a.nomeProperty());
            TFemail.textProperty().bindBidirectional(a.emailProperty());
            TFidade.textProperty().bindBidirectional(a.idadeProperty(), new NumberStringConverter());
        }
    }

    private void unbindData(Alunos a){
        if (a != null){
            TFnome.textProperty().unbindBidirectional(a.nomeProperty());
            TFemail.textProperty().unbindBidirectional(a.emailProperty());
            TFidade.textProperty().unbindBidirectional(a.idadeProperty());
            clearForm();
        }
    }

    public void onLimparPesquisa(){

        TFpesquisar.clear();
    }

    public void onRecaregar() {

        initialize();
    }

    public void onSalvar() throws Exception {
        Alunos a = new Alunos();
        a.setNome(TFnome.getText().toString());
        a.setEmail(TFemail.getText());
        a.setIdade(Integer.valueOf(TFidade.getText()));
        validarFormulario(a);
    }

    public void onAlterar() throws Exception {
        Alunos a = (Alunos) TValuno.getSelectionModel().getSelectedItem();
        Alert at = new Alert(Alert.AlertType.CONFIRMATION);
        at.setHeaderText("DESEJA ALTERAR ALUNO!!!");
        at.setContentText(a.getNome());

        Optional<ButtonType> result = at.showAndWait();
        if (result.get() == ButtonType.OK) {

            a.setNome(TFnome.getText().toString());
            a.setEmail(TFemail.getText());
            a.setIdade(Integer.valueOf(TFidade.getText()));
            AlunoDAO ad = new AlunoDAO();
            ad.alterarAluno(a);
            caregarTabela();
        }
    }

    public void onExcluir() throws Exception {
        if (TValuno.getSelectionModel().getSelectedItem() != null){

            Alunos al = (Alunos) TValuno.getSelectionModel().getSelectedItem();

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("DESEJA EXCLUIR ALUNO!!!");
            a.setContentText(al.getNome());

            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                AlunoDAO ad = new AlunoDAO();
                ad.excluirAluno(al);
                caregarTabela();
            }
        }
    }

    public void onNovo() {
        TValuno.getSelectionModel().clearSelection();
        clearForm();
    }
    /*
    * limpa o formulario
    */
    private void clearForm(){
        TFnome.clear();
        TFemail.clear();
        TFidade.clear();
        TFnome.requestFocus();
    }

    private void validarFormulario(Alunos a) throws Exception {
        StringBuilder valida = new StringBuilder();
        valida.delete(0, valida.length());

        if (a.getNome().isEmpty()){
            valida.append("NOME\n");
        }
        if (a.getEmail().isEmpty()){
            valida.append("E-MAIL\n");
        }
        if (a.getIdade() == 0){
            valida.append("IDADE\n");
        }

        if (!valida.toString().isEmpty()) {

            Alert e = new Alert(Alert.AlertType.INFORMATION);
            e.setHeaderText("Preencha os campos coretamente!!!");
            e.setContentText(valida.toString());
            e.show();
        }
        else {
            AlunoDAO dao = new AlunoDAO();
            dao.cadastrarAluno(a);
            caregarTabela();
        }

    }
}
