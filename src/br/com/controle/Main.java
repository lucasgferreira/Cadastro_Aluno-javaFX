package br.com.controle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../visao/Tela.fxml"));
        primaryStage.setTitle("Cadastro Alunos");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("br/com/visao/icons/graduate.png"));
        primaryStage.setMinHeight(480);
        primaryStage.setMinWidth(640);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
