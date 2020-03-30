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

import java.util.ArrayList;
import java.util.List;

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

        descripciones();
        TextArea environment = new TextArea();
        TextArea environment1 = new TextArea();
        Label titulo = new Label();
        Label descripcion = new Label();
        Button prueba1 = new Button();
        Button prueba2 = new Button();
        Button prueba3 = new Button();
        Button prueba4 = new Button();
        Button prueba5 = new Button();
        Button prueba6 = new Button();
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
        prueba6.setText("prueba 6");prueba6.setMinWidth(100);
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
        parteB.getChildren().addAll(prueba1, prueba2, prueba3, prueba4, prueba5, prueba6);

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
    //------------------------------------------------------------------------------------------------------------------
    public void descripciones()
    {
        desc.add("");
        desc.add("You and your date are trying to get a table at a restaurant. The parameter \"you\" is the stylishness of your clothes, in the range 0..10, and \"date\" is the stylishness of your date's clothes. The result getting the table is encoded as an int value with 0=no, 1=maybe, 2=yes. If either of you is very stylish, 8 or more, then the result is 2 (yes). With the exception that if either of you has style of 2 or less, then the result is 0 (no). Otherwise the result is 1 (maybe).");
        desc.add("Given three ints, a b c, return true if one of b or c is \"close\" (differing from a by at most 1), while the other is \"far\", differing from both other values by 2 or more. Note: Math.abs(num) computes the absolute value of a number.");
        desc.add("Given an array of strings, return a Map<String, Boolean> where each different string is a key and its value is true if that string appears 2 or more times in the array.");
        desc.add("Given a string, return a string where every appearance of the lowercase word \"is\" has been replaced with \"is not\". The word \"is\" should not be immediately preceeded or followed by a letter -- so for example the \"is\" in \"this\" does not count. (Note: Character.isLetter(char) tests if a char is a letter.)");
        desc.add("Given two arrays of ints sorted in increasing order, outer and inner, return true if all of the numbers in inner appear in outer. The best solution makes only a single \"linear\" pass of both arrays, taking advantage of the fact that both arrays are already in sorted order.");
    }
    public void codigosPruebas()
    {
        //---------------Array dateFashion--------------------------------
        ArrayList<dateFashion> dateFashion = new ArrayList<>();
        dateFashion.add(new dateFashion("dateFashion(5, 10)","2", "public int dateFashion(int you, int date)\n" +
            " {\n" +
            "int val=0;\n" +
            "if(you >= 8 || date >= 8)\n" +
            "    val = 2;\n" +
            "  return val;\n" +
            "}\n"));
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
        dateFashion.add(new dateFashion("dateFashion(2, 7)","0", "public int dateFashion(int you, int date)\n" +
                "{\n" +
                "  int val=0;\n" +
                "  if(you <= 2 || date <= 2)\n" +
                "    val = 0;\n" +
                "    \n" +
                "  return val; \n" +
                "}\n"));
        //--------------closeFar-----------------------------------------
        ArrayList<closeFar> closeFar = new ArrayList<>();
        closeFar.add(new closeFar("closeFar(1, 2, 10)", "true", "public boolean closeFar(int a, int b, int c)\n" +
                "{\n" +
                " boolean result=false;\n" +
                "  if(Math.abs(a-b) <= 1)\n" +
                "              result = true;\n" +
                "            return result;\n" +
                "}\n"));
        closeFar.add(new closeFar("closeFar(8, 6, 9)", "true", "public boolean closeFar(int a, int b, int c)\n" +
                "{\n" +
                "    boolean result=false;\n" +
                "\n" +
                "if(Math.abs(a - b) >= 2)\n" +
                "      result= true;\n" +
                "    \n" +
                "    return result;\n" +
                "}\n"));
        closeFar.add(new closeFar("closeFar(-1, 10, 0)", "true", "public boolean closeFar(int a, int b, int c)\n" +
                "{\n" +
                "  boolean result=false;\n" +
                " \n" +
                "    if(Math.abs(a - c) <= 1 )\n" +
                "      result = true;\n" +
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


        //--------------wordMultiple-------------------------------------
        ArrayList<wordMultiple> wordMultiple = new ArrayList<>();
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

        //--------------notRepla-----------------------------------------
            ArrayList<notRepla> notRepla = new ArrayList<>();
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

        //--------------linearIn-----------------------------------------
            ArrayList<linearIn> linearIn = new ArrayList<>();
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
