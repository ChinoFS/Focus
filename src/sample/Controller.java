package sample;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;


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
    String []problemas;

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

    public void Problema(String nombre) {
        gridPane1.getChildren().clear();
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
