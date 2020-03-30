package sample;

import com.mongodb.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {
    MongoClient mongoClient;
    DB db;
    @FXML
    GridPane gridPane1;
    @FXML
    TitledPane titledPane;
    @FXML
    Button batras;
    int locacion;
    String ID;
    String [] [] categorias;
    String [] problemas;
    String [] descripcion;

    @FXML
    public void initialize() {
        conexion();
        DBCategorias();

        inicio();
    }

    public  void inicio (){
        batras.setVisible(false);
        locacion = 1;
        for (int i=0;i<=categorias.length-1;i++){
            Button button= new Button(categorias[i][0]);
            button.setId(categorias[i][1]);
            button.setOnAction(event -> problemas(button.getId()));
            button.setPrefSize(150,10);
            gridPane1.addRow(0,button);
            titledPane.autosize();
            gridPane1.autosize();
        }
    }

    public void problemas(String id) {
        ID = id;
        DBProblemas(id);
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

        ID = id;
        batras.setVisible(true);

        TextArea environment = new TextArea();
        TextArea environment1 = new TextArea();
        Label titulo = new Label();
        Label descripcion = new Label();
        Button prueba1 = new Button();
        Button prueba2 = new Button();
        Button prueba3 = new Button();
        Button prueba4 = new Button();
        Button prueba5 = new Button();
        Button verificar = new Button();

        gridPane1.getChildren().clear();

        environment.setMinHeight(50);
        environment.setMinWidth(50);
        environment1.setMinHeight(50);
        environment1.setMinWidth(50);
        titulo.setText(String.valueOf(id));
        descripcion.setText("Descripcion");

        prueba1.setText("prueba 1");prueba1.setMinWidth(100);
        prueba2.setText("prueba 2");prueba2.setMinWidth(100);
        prueba3.setText("prueba 3");prueba3.setMinWidth(100);
        prueba4.setText("prueba 4");prueba4.setMinWidth(100);
        prueba5.setText("prueba 5");prueba5.setMinWidth(100);
        verificar.setText("Verificar");

        HBox parte1 = new HBox();
        HBox parte2 = new HBox();
        HBox parte3 = new HBox();
        HBox parte4 = new HBox();

        VBox parteB = new VBox();



        parte1.getChildren().add(titulo);
        parte2.getChildren().add(descripcion);
        parte3.getChildren().add(environment);
        parte4.getChildren().add(environment1);
        parteB.getChildren().addAll(prueba1, prueba2, prueba3, prueba4, prueba5);

        gridPane1.add(parte1, 0, 0);
        gridPane1.add(parte2, 0, 1);
        gridPane1.add(parte3, 0, 2);
        gridPane1.add(parteB, 1, 2);
        gridPane1.add(parte4, 2, 2);
        gridPane1.add(verificar, 0, 3);

        gridPane1.setVgap(15);
        gridPane1.setHgap(15);
        locacion = 3;


        //Aqui va su parte nestor y migue
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

    public void DBCategorias(){
        DBCollection coll = db.getCollection("categoria");
        DBCursor cursor = coll.find();
        int size = cursor.size();
        categorias = new String[size][size];
        int x=0;
        try {
            while(cursor.hasNext()) {
                categorias[x][0]=(cursor.next().get("categoria"))+"";
                x++;
            }
        } finally {
            cursor.close();
        }

        coll = db.getCollection("categoria");
        cursor = coll.find();
        x=0;
        try {
            while(cursor.hasNext()) {
                categorias[x][1]=(cursor.next().get("_id"))+"";
                x++;
            }
        } finally {
            cursor.close();
        }

    }

    public void DBProblemas(String id){//id categoria
        DBCollection coll = db.getCollection("problema");
        BasicDBObject filtro = new BasicDBObject();
        filtro.put("categoria", id);
        DBCursor cursor = coll.find(filtro);
        int size = cursor.size();
        problemas = new String[size];
        int x=0;
        try {
            while(cursor.hasNext()) {
                problemas[x]=(cursor.next().get("problema"))+"";
                x++;
            }
        } finally {
            cursor.close();
        }
    }

    public void conexion(){
        mongoClient = new MongoClient( "localhost" , 27017 );
        db= mongoClient.getDB("Focus");
    }
}
