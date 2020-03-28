package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class Controller {
    @FXML
    GridPane gridPane1;
    @FXML
    TitledPane titledPane;
    @FXML
    Button batras;
    int locacion;
    String ID;
    String [] categorias = {"Strings","Logica","Matematico","Otro"};
    String [] problemas = {"Problema1", "Problema2"};

    @FXML
    public void initialize() {
        inicio();
    }

    public  void inicio (){
        batras.setVisible(false);
        locacion = 1;
        for (int i=0;i<=categorias.length-1;i++){
            Button button= new Button(categorias[i]);
            button.setId(categorias[i]);
            button.setOnAction(event -> problemas(button.getId()));
            button.setPrefSize(150,10);
            gridPane1.addRow(0,button);
            titledPane.autosize();
            gridPane1.autosize();
        }
    }

    public void problemas(String id) {
        ID = id;
        batras.setVisible(true);
        gridPane1.getChildren().clear();
        locacion = 2;
        for (int i=0;i<=problemas.length-1;i++){
            Button button= new Button(problemas[i]);
            button.setId(problemas[i]);
            button.setOnAction(event -> Problema(button.getId()));
            button.setPrefSize(150,10);
            gridPane1.addRow(0,button);
            titledPane.autosize();
            gridPane1.autosize();
        }
    }


    public void Problema(String id) {
        gridPane1.getChildren().clear();
        locacion = 3;
    }

    @FXML
    public void atras(){
        if(locacion ==1){
        }
        if(locacion ==2){
            gridPane1.getChildren().clear();
            inicio();
        }
        if(locacion ==3){
            gridPane1.getChildren().clear();
            problemas(ID);
        }
    }
}
