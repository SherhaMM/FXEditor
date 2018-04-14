package ap161.mihailov;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;


import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root=FXMLLoader.load(Main.class.getResource("list.fxml"));
        Scene scene= new Scene(root);
        stage.setTitle("FX Editor");
        stage.setScene(scene);
        stage.show();
       ImageControl.getInstance().setMainStage(stage);
    }

    public static void main(String[] args)  {
	launch(args);
    }
}
