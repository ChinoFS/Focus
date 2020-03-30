package sample;

import com.mongodb.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import javax.swing.text.Style;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    ArrayList<String> desc = new ArrayList<>();
    String descr;

    TextField campo1 = new TextField();
    TextField campo2 = new TextField();
    TextField campo3 = new TextField();
    TextField campo4 = new TextField();
    TextField campo5 = new TextField();
    Label titulo = new Label();
    Label descripcion = new Label();
    Label parametros = new Label();
    Button prueba1 = new Button();
    Button BtnJson = new Button();
    ArrayList<dateFashion> dateFashion = new ArrayList<>();
    ArrayList<closeFar> closeFar = new ArrayList<>();
    ArrayList<wordMultiple> wordMultiple = new ArrayList<>();
    ArrayList<notRepla> notRepla = new ArrayList<>();
    ArrayList<linearIn> linearIn = new ArrayList<>();
    HBox parte1 = new HBox();
    HBox parte2 = new HBox();
    HBox parte3 = new HBox();
    HBox parte4 = new HBox();

    VBox parteB = new VBox();


    @FXML
    public void initialize() {
        conexion();
        DBCategorias();
        codigosPruebas();
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
        gridPane1.getChildren().clear();

        campo1.setMinHeight(50);
        campo1.setMinWidth(50);
        campo2.setMinHeight(50);
        campo2.setMinWidth(50);
        titulo.setText(String.valueOf(id));
        descripcion.setText(""+ObtenerDes(id));
        parametros.setText("Ingrese los parametros correspondientes");
        prueba1.setText("Verificar");prueba1.setMinWidth(100);

        BtnJson.setText("Json");

        metodos(titulo.getText());


        BtnJson.setOnAction(event -> {
            ObtenerJson(id);
        });



        parte1.getChildren().add(titulo);
        parte2.getChildren().add(descripcion);
        parte4.getChildren().add(parametros);

        parteB.getChildren().add(prueba1);

        gridPane1.add(parte1, 0, 0);
        gridPane1.add(parte2, 0, 1);
        gridPane1.add(parte4, 0, 2);

        gridPane1.add(parteB, 2, 3);
        gridPane1.add(BtnJson, 0, 4);

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
    private Object ObtenerDes(String id) {
        DBCollection coll = db.getCollection("problema");
        BasicDBObject filtro = new BasicDBObject();
        filtro.put("problema", id);
        DBCursor cursor = coll.find(filtro);
        try {
            while(cursor.hasNext()) {
                return cursor.next().get("descripcion");
            }
        } finally {
            cursor.close();
        }
        return null;
    }
    private DBObject ObtenerJson(String id) {
        DBCollection coll = db.getCollection("problema");
        BasicDBObject filtro = new BasicDBObject();
        filtro.put("problema", id);
        DBCursor cursor = coll.find(filtro);
        try {
            while(cursor.hasNext()) {
                return cursor.next();
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    public void conexion(){
        mongoClient = new MongoClient( "localhost" , 27017 );
        db= mongoClient.getDB("Focus");
    }
    //------------------------------------------------------------------------------------------------------------------

    public void metodos(String titulo)
    {
        if(titulo.equals("dateFashion")){
            parte3.getChildren().addAll(campo1, campo2);
            gridPane1.add(parte3, 0, 3);
            prueba1.setOnAction(event -> {

                dateFashion(Integer.valueOf(campo1.getText()), Integer.valueOf(campo2.getText()));

            });


        }else if(titulo.equals("closeFar")){
            parte3.getChildren().addAll(campo1, campo2, campo3);
            gridPane1.add(parte3, 0, 3);
            prueba1.setOnAction(event -> {
                closeFar(Integer.valueOf(campo1.getText()), Integer.valueOf(campo2.getText()), Integer.valueOf(campo3.getText()));
            });
        }else if(titulo.equals("wordMultiple")){
            parte3.getChildren().addAll(campo1, campo2, campo3, campo4, campo5);
            gridPane1.add(parte3, 0, 3);
            //wordMultiple(Integer.valueOf(campo1),) NO HABILITADO
        }else if(titulo.equals("notReplace")){
            parte3.getChildren().addAll(campo1);
            gridPane1.add(parte3, 0, 3);
            prueba1.setOnAction(event -> {
                notReplace(campo1.getText());
            });

        }else if(titulo.equals("linearIn")){
            //linearIn() NO HABILITADO
        }
    }
    //--------Codigos CodingBat-----------------------------------------------------------------------------------------
    public int dateFashion(int you, int date) {
        int result;

        if (you <= 2 || date <= 2)
        {
            result = 0;
        }
        else
        {
            if (you >= 8 || date >= 8)
            {
                result = 2;
            }
            else {
                result = 1;

            }
        }
        Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
        dialogo.setTitle("Prueba Unitaria");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Resultado \n" + result);
        dialogo.initStyle(StageStyle.UTILITY);
        dialogo.showAndWait();

        return result;
    }

    public boolean closeFar(int a, int b, int c)
    {
        boolean bandera = true;
        if(Math.abs(a-b) <= 1 && Math.abs(a-c) >= 2 && Math.abs(b-c) >= 2) {
             bandera = true;
        }
        else if(Math.abs(a - c) <= 1 && Math.abs(a - b) >= 2 && Math.abs(b - c) >= 2)
        bandera = true;
        else {
            bandera = false;
        }
        Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
        dialogo.setTitle("Prueba Unitaria");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Resultado \n" + bandera);
        dialogo.initStyle(StageStyle.UTILITY);
        dialogo.showAndWait();
        return bandera;
    }
    public Map<String, Boolean> wordMultiple(String[] strings)
    {
        Map<String, Boolean> map = new HashMap<>();

        for(String s:strings)
        {
            if(map.containsKey(s))
            {
                map.put(s, true);
            }
            else
            {
                map.put(s,false);
            }
        }
        return map;
    }
    public String notReplace(String str)
    {
        String result = "";
        for(int i = 0; i < str.length(); i++)
        {
            if(i-1>=0 && Character.isLetter(str.charAt(i-1)) ||
                    i+2<str.length() && Character.isLetter(str.charAt(i+2)))
            {
                result += str.charAt(i);
            }
            else if(i+1 < str.length() && str.substring(i,i+2).equals("is"))
            {
                result += "is not";
                i++;
            }
            else result += str.charAt(i);
        }

        Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
        dialogo.setTitle("Prueba Unitaria");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Resultado \n" + result);
        dialogo.initStyle(StageStyle.UTILITY);
        dialogo.showAndWait();

        return result;
    }
    public boolean linearIn(int[] outer, int[] inner)
    {
        int numFound = 0;

        if(inner.length == 0)
            return true;

        int cont = 0;
        for(int i = 0; i < outer.length; i++)
        {
            if(outer[i] == inner[cont])
            {
                numFound++;
                cont++;
            }
            else if(outer[i] > inner[cont])
                return false;

            if(numFound == inner.length)
                return true;
        }
        return false;
    }
    //------------------------------------------------------------------------------------------------------------------
    public void codigosPruebas()
    {
        //---------------Array dateFashion--------------------------------


        dateFashion.add(new dateFashion("dateFashion(5, 10)","2", "public int dateFashion(int you, int date)\n" +
            " {\n" +
            "int val=0;\n" +
            "if(you >= 8 || date >= 8)\n" +
            "    val = 2;\n" +
            "  return val;\n" +
            "}\n"));
        dateFashion.add(new dateFashion("dateFashion(9, 9)", "2", ""));
        dateFashion.add(new dateFashion("dateFashion(10, 5)","2", "public int dateFashion(int you, int date)\n" +
                " {\n" +
                "int val=0;\n" +
                "if(you >= 8 || date >= 8)\n" +
                "    val = 2;\n" +
                "  return val;\n" +
                "}\n"));
        dateFashion.add(new dateFashion("dateFashion(3, 3)", "1","public int dateFashion(int you, int date)\n" +
            "{\n" +
            "  return 1;\n" +
            "}\n"));
        dateFashion.add(new dateFashion("dateFashion(3, 7)", "1","public int dateFashion(int you, int date)\n" +
                "{\n" +
                "  return 1;\n" +
                "}\n"));
        dateFashion.add(new dateFashion("dateFashion(10, 2)","0", "public int dateFashion(int you, int date)\n" +
                "{\n" +
                "  int val=0;\n" +
                "  if(you <= 2 || date <= 2)\n" +
                "    val = 0;\n" +
                "    \n" +
                "  return val; \n" +
                "}\n"));
        dateFashion.add(new dateFashion("dateFashion(2, 9)", "0", ""));
        dateFashion.add(new dateFashion("dateFashion(2, 2)", "0", ""));
        dateFashion.add(new dateFashion("dateFashion(2, 7)","0", "public int dateFashion(int you, int date)\n" +
                "{\n" +
                "  int val=0;\n" +
                "  if(you <= 2 || date <= 2)\n" +
                "    val = 0;\n" +
                "    \n" +
                "  return val; \n" +
                "}\n"));
        dateFashion.add(new dateFashion("dateFashion(6, 2)", "0", ""));
        //--------------closeFar----------------------------------------------------------------------------------------

        closeFar.add(new closeFar("closeFar(1, 2, 10)", "true", "public boolean closeFar(int a, int b, int c)\n" +
            "{\n" +
            " boolean result=false;\n" +
            "  if(Math.abs(a-b) <= 1)\n" +
            "              result = true;\n" +
            "            return result;\n" +
            "}\n"));
        closeFar.add(new closeFar("closeFar(4, 1, 3)", "true", ""));
        closeFar.add(new closeFar("closeFar(-1, 10, 0)", "true", "public boolean closeFar(int a, int b, int c)\n" +
                "{\n" +
                "  boolean result=false;\n" +
                " \n" +
                "    if(Math.abs(a - c) <= 1 )\n" +
                "      result = true;\n" +
                "    return result;\n" +
                "}\n"));
        closeFar.add(new closeFar("closeFar(0, -1, 10)", "true", ""));
        closeFar.add(new closeFar("closeFar(8, 6, 9)", "true", "public boolean closeFar(int a, int b, int c)\n" +
                "{\n" +
                "    boolean result=false;\n" +
                "\n" +
                "if(Math.abs(a - b) >= 2)\n" +
                "      result= true;\n" +
                "    \n" +
                "    return result;\n" +
                "}\n"));

        closeFar.add(new closeFar("closeFar(1, 2, 3)", "false", "public boolean closeFar(int a, int b, int c)\n" +
                "{\n" +
                "  boolean result = true;\n" +
                "\n" +
                "    result = false;\n" +
                "    \n" +
                "     return result;\n" +
                "} \n"));
        closeFar.add(new closeFar("closeFar(4, 5, 3)", "false", "public boolean closeFar(int a, int b, int c)\n" +
                "{\n" +
                "  boolean result = true;\n" +
                "\n" +
                "    result = false;\n" +
                "    \n" +
                "     return result;\n" +
                "} \n"));
        closeFar.add(new closeFar("closeFar(4, 3, 5)", "false", "public boolean closeFar(int a, int b, int c)\n" +
                "{\n" +
                "  boolean result = true;\n" +
                "\n" +
                "    result = false;\n" +
                "    \n" +
                "     return result;\n" +
                "} \n"));
        closeFar.add(new closeFar("closeFar(8, 9, 10)", "false", ""));
        closeFar.add(new closeFar("closeFar(10, 8, 9)", "false", ""));

        //--------------wordMultiple------------------------------------------------------------------------------------

        wordMultiple.add(new wordMultiple("wordMultiple([\"a\", \"b\", \"a\", \"c\", \"b\"])", "{\"a\": true, \"b\": true, \"c\": false}", " public Map<String, Boolean> wordMultiple(String[] strings)\n" +
                "{\n" +
                "     Map<String, Boolean> map = new HashMap<String,Boolean>();\n" +
                " \n" +
                "  for(String s:strings)\n" +
                "  {\n" +
                "    if(map.containsKey(s))\n" +
                "    {\n" +
                "      map.put(s, true);\n" +
                "    }\n" +
                "    else\n" +
                "    {\n" +
                "      map.put(s,false);\n" +
                "    }\n" +
                "  }\n" +
                "  return map;\n" +
                "}\n"));
        wordMultiple.add(new wordMultiple("wordMultiple([\"c\", \"b\", \"a\"])", "{\"a\": false, \"b\": false, \"c\": false}", "public Map<String, Boolean> wordMultiple(String[] strings)\n" +
                "{\n" +
                " Map<String, Boolean> map = new HashMap<String,Boolean>();\n" +
                " \n" +
                "  for(String s:strings)\n" +
                "  {\n" +
                "    if(!map.containsKey(s))\n" +
                "    \n" +
                "    {\n" +
                "      map.put(s,false);\n" +
                "    }\n" +
                "  }\n" +
                "  return map;\n"));
        wordMultiple.add(new wordMultiple("wordMultiple([\"c\", \"c\", \"c\", \"c\"])", "{\"c\": true}", "public Map<String, Boolean> wordMultiple(String[] strings)\n" +
                "{\n" +
                " Map<String, Boolean> map = new HashMap<String,Boolean>();\n" +
                " \n" +
                "  for(String s:strings)\n" +
                "  {\n" +
                "    if(!map.containsKey(s))\n" +
                "    {\n" +
                "      map.put(s, true);\n" +
                "    }\n" +
                "  }\n" +
                "  return map\n"));
        wordMultiple.add(new wordMultiple("wordMultiple([])", "{}", "public Map<String, Boolean> wordMultiple(String[] strings)\n" +
                "{\n" +
                " Map<String, Boolean> map = new HashMap<String,Boolean>();\n" +
                " \n" +
                "  return map;\n" +
                "}\n"));

        //--------------notRepla----------------------------------------------------------------------------------------

            notRepla.add(new notRepla("notReplace(\"is test\")", "\"is not test\"", "public String notReplace(String str)\n" +
                    "{\n" +
                    "  String result = \"\";\n" +
                    "  for(int i = 0; i < str.length(); i++)\n" +
                    "  {\n" +
                    "     if(i+1 < str.length() && str.substring(i,i+2).equals(\"is\"))\n" +
                    "    {\n" +
                    "      result += \"is not\";\n" +
                    "      i++;\n" +
                    "    }\n" +
                    "    else result += str.charAt(i);\n" +
                    "  }\n" +
                    " \n" +
                    "  return result;\n" +
                    "}\n"));
            notRepla.add(new notRepla("notReplace(\"is-is\")", "\"is not-is not\" ", ""));
            notRepla.add(new notRepla("notReplace(\"This is right\")", "\"This is not right\"", "public String notReplace(String str)\n" +
                    "{\n" +
                    "  String result = \"\";\n" +
                    "  for(int i = 0; i < str.length(); i++)\n" +
                    "  {\n" +
                    "    if(i-1>=0 && Character.isLetter(str.charAt(i-1)) )\n" +
                    "    {\n" +
                    "      result += str.charAt(i);\n" +
                    "    }\n" +
                    "    else if( str.substring(i,i+2).equals(\"is\"))\n" +
                    "    {\n" +
                    "      result += \"is not\";\n" +
                    "      i++;\n" +
                    "    }\n" +
                    "    else result += str.charAt(i);\n" +
                    "  }\n" +
                    "  return result;\n" +
                    "}\n"));
            notRepla.add(new notRepla("notReplace(\"\")", "\"\"", " public String notReplace(String str)\n" +
                    "{\n" +
                    "  String result = \"\";\n" +
                    "  for(int i = 0; i < str.length(); i++)\n" +
                    "  {\n" +
                    "    if( Character.isLetter(str.charAt(i-1)) )\n" +
                    "    {\n" +
                    "      result += str.charAt(i);\n" +
                    "    }\n" +
                    "  }\n" +
                    "  return result;\n" +
                    "}\n"));
            notRepla.add(new notRepla("notReplace(\"is\")", "\"is not\"", " public String notReplace(String str)\n" +
                    "{\n" +
                    "  String result = \"\";\n" +
                    "  for(int i = 0; i < str.length(); i++)\n" +
                    "  {\n" +
                    "    if( str.substring(i,i+2).equals(\"is\"))\n" +
                    "    {\n" +
                    "      result += \"is not\";\n" +
                    "      i++;\n" +
                    "    }\n" +
                    "    else result += str.charAt(i);\n" +
                    "  }\n" +
                    " \n" +
                    "  re\n"));
            notRepla.add(new notRepla("notReplace(\"isis\")", "\"isis\" ", ""));
            notRepla.add(new notRepla("notReplace(\"AAAis is\")", "\"AAAis is not\"", "public String notReplace(String str)\n" +
                    "{\n" +
                    "  String result = \"\";\n" +
                    "  for(int i = 0; i < str.length(); i++)\n" +
                    "  {\n" +
                    "    if(i-1>=0 && Character.isLetter(str.charAt(i-1)) )\n" +
                    "    {\n" +
                    "      result += str.charAt(i);\n" +
                    "    }\n" +
                    "    else if( str.substring(i,i+2).equals(\"is\"))\n" +
                    "    {\n" +
                    "      result += \"is not\";\n" +
                    "      i++;\n" +
                    "    }\n" +
                    "    else result += str.charAt(i);\n" +
                    "  }\n" +
                    "  return result;\n" +
                    "}\n"));

        //--------------linearIn----------------------------------------------------------------------------------------

            linearIn.add(new linearIn("linearIn([1, 2, 4, 6], [2, 4])", "true", "public boolean linearIn(int[] outer, int[] inner)\n" +
                    "{\n" +
                    "  int numFound = 0;\n" +
                    "    \n" +
                    "      int cont = 0;\n" +
                    "    for(int i = 0; i < outer.length; i++)\n" +
                    "      {\n" +
                    "        if(outer[i] == inner[cont])\n" +
                    "        {\n" +
                    "        numFound++;\n" +
                    "          cont++;\n" +
                    "        }\n" +
                    "      \n" +
                    "      if(numFound == inner.length)\n" +
                    "         return true;\n" +
                    "      }\n" +
                    "      return false;\n" +
                    "}\n"));
            linearIn.add(new linearIn("linearIn([2, 2, 4, 4, 6, 6], [2, 4])", "true ", ""));
            linearIn.add(new linearIn("linearIn([2, 2, 2, 2, 2], [2, 2])", "true ", ""));
            linearIn.add(new linearIn("linearIn([1, 2, 3], [2])", "true", "public boolean linearIn(int[] outer, int[] inner)\n" +
                    "{\n" +
                    "  int numFound = 0;\n" +
                    "    \n" +
                    "      int cont = 0;\n" +
                    "    for(int i = 0; i < outer.length; i++)\n" +
                    "      {\n" +
                    "        if(outer[i] == inner[cont])\n" +
                    "        {\n" +
                    "        numFound++;\n" +
                    "          cont++;\n" +
                    "        }\n" +
                    "      \n" +
                    "      if(numFound == inner.length)\n" +
                    "         return true;\n" +
                    "      }\n" +
                    "      return false;\n" +
                    "}\n"));
            linearIn.add(new linearIn("linearIn([1, 2, 3], [])", "true", "public boolean linearIn(int[] outer, int[] inner)\n" +
                    "{\n" +
                    "  boolean result=false;\n" +
                    "    \n" +
                    "    if(inner.length == 0)\n" +
                    "      result =true;\n" +
                    "\n" +
                    "    return result;\n" +
                    "} \n"));
            linearIn.add(new linearIn("linearIn([1, 2, 4, 6], [2, 3, 4])", "false", "public boolean linearIn(int[] outer, int[] inner)\n" +
                    "{\n" +
                    "\n" +
                    "      int cont = 0;\n" +
                    "    boolean result=true;\n" +
                    "    \n" +
                    "    for(int i = 0; i < outer.length; i++)\n" +
                    "      {\n" +
                    "        if(outer[i] == inner[cont])\n" +
                    "        {\n" +
                    "          cont++;\n" +
                    "        }\n" +
                    "        else if(outer[i] > inner[cont])\n" +
                    "        result=false;\n" +
                    "      }\n" +
                    "      return result;\n"));
            linearIn.add(new linearIn("linearIn([2, 2, 2, 2, 2], [2, 4])", "false", ""));
            linearIn.add(new linearIn("linearIn([1, 2, 3], [-1])", "false", "public boolean linearIn(int[] outer, int[] inner)\n" +
                    "{\n" +
                    "\n" +
                    "      int cont = 0;\n" +
                    "    boolean result=true;\n" +
                    "    \n" +
                    "    for(int i = 0; i < outer.length; i++)\n" +
                    "      {\n" +
                    "        if(outer[i] == inner[cont])\n" +
                    "        {\n" +
                    "          cont++;\n" +
                    "        }\n" +
                    "        else if(outer[i] > inner[cont])\n" +
                    "        result=false;\n" +
                    "      }\n" +
                    "      return result;\n"));

    }

}
