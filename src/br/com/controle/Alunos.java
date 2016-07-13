package br.com.controle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alunos {

    private IntegerProperty mat = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private IntegerProperty idade = new SimpleIntegerProperty();

    public int getMat() {
        return mat.get();
    }

    public IntegerProperty matProperty() {
        return mat;
    }

    public void setMat(int mat) {
        this.mat.set(mat);
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public int getIdade() {
        return idade.get();
    }

    public IntegerProperty idadeProperty() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade.set(idade);
    }
}
