package com.ui.panels;

 

import java.awt.Toolkit;

import java.awt.datatransfer.Clipboard;

import java.awt.datatransfer.ClipboardOwner;

import java.awt.datatransfer.StringSelection;

import java.awt.datatransfer.Transferable;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.util.Properties;

 

import javafx.application.Application;

import javafx.application.Platform;

import javafx.collections.ObservableList;

import javafx.event.Event;

import javafx.event.EventHandler;

import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.control.Tab;

import javafx.scene.control.TabPane;

import javafx.scene.control.Tooltip;

import javafx.scene.input.KeyCode;

import javafx.scene.input.KeyEvent;

import javafx.scene.layout.HBox;

import javafx.scene.text.Font;

import javafx.stage.Screen;

import javafx.stage.Stage;

import javafx.stage.StageStyle;

 

public class ColoredTabDemo extends Application implements ClipboardOwner{

       private static Properties versionProperties = null;

 

       public static void loadVersionProperties() {

              InputStream inStreamVersionProperties = null;

              try {

             

                     inStreamVersionProperties = new FileInputStream(System.getProperty("user.home")+"\\data.properties");

                     versionProperties = new Properties();

                     versionProperties.load(inStreamVersionProperties);

 

              } catch (IOException e) {

                     e.printStackTrace();

              } finally {

                     if (inStreamVersionProperties != null) {

                           try {

                                  inStreamVersionProperties.close();

                           } catch (IOException e) {

                                  e.printStackTrace();

                           }

                     }

              }

       }

 

       public static Properties getVersionProperties() {

              loadVersionProperties();

              return versionProperties;

       }     

public static Properties props = getVersionProperties();

    @Override

    public void start(Stage primaryStage) {

       props.getProperty("categories");

       String[] categories = props.getProperty("categories").split(",");

       String[] arcos_data = props.getProperty(categories[0]+"_DATA").split(";");

      

       setClipboardContents(arcos_data[0]);

        TabPane tabPane = new TabPane();

        for (int i = 0; i < categories.length; i++) {

            tabPane.getTabs().add(createTab(categories[i]));

        }

       

        Scene scene = new Scene(tabPane, 331, 52);

        scene.getStylesheets().add("colored-tab-demo.css");

        primaryStage.setScene(scene);

        primaryStage.setTitle("SBI");

        primaryStage.setResizable(true);

        primaryStage.setAlwaysOnTop(true);

        primaryStage.setX(Screen.getPrimary().getBounds().getMaxX()-370);

        primaryStage.setY(0);

        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event->{

             if(event.getCode()==KeyCode.F10) Platform.exit();

        });

        //primaryStage.setFocused(true);

        primaryStage.show();

    }

 

    private Tab createTab(String tabname) {

        Tab tab = new Tab(tabname);

        Button username = new Button("USERNAME");

        Button password = new Button("PASSWORD");

       

       

        HBox hbox = new HBox();   

        

        //Setting the space between the nodes of a HBox pane

        hbox.setSpacing(20);   

       

        

        String[] data = props.getProperty(tabname+"_DATA").split(";");

      

        // action event

        EventHandler<Event> event = 

        new EventHandler<Event>() {

      

            public void handle(Event e)

            {

                if (tab.isSelected()) 

                {

                    setClipboardContents(data[0]);

                    try {System.out.println("data   "+data[0]);

                    if(data[0].contains("192.168"))

                                  Runtime.getRuntime().exec("C:\\Program Files\\Internet Explorer\\iexplore.exe " + data[0]);

                    else   Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe " + data[0]);            
                    
                    } catch (IOException e1) {

                                         // TODO Auto-generated catch block

                                         e1.printStackTrace();

                                  }

                    System.out.println(data[0]);

                }

            }

        };

      

        // set event handler to the tab

        tab.setOnSelectionChanged(event);

        final Tooltip tooltip_tab = new Tooltip(data[0]);

        tooltip_tab.setFont(new Font("Arial", 16));

        tab.setTooltip(tooltip_tab);

       

        final Tooltip tooltip_username = new Tooltip(data[1]);

        tooltip_tab.setFont(new Font("Arial", 16));

        username.setTooltip(tooltip_username);

       

        final Tooltip tooltip_password = new Tooltip(data[2]);

        tooltip_tab.setFont(new Font("Arial", 16));

        password.setTooltip(tooltip_password);

       

        username.setOnAction(e -> {

            setClipboardContents(data[1]);

            System.out.println(data[1]);

            });    

             password.setOnAction(e -> {

            setClipboardContents(data[2]);

            System.out.println(data[2]);

            });    

        

        ObservableList list = hbox.getChildren(); 

        

        //Adding all the nodes to the observable list (HBox)

        list.addAll(username, password);    

        

        tab.setContent(hbox);

 

        return tab ;

    }

    public void setClipboardContents(String string){

        StringSelection stringSelection = new StringSelection(string);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        clipboard.setContents(stringSelection, this);

      }

    public static void main(String[] args) {

        launch(args);

    }

 

       @Override

       public void lostOwnership(Clipboard clipboard, Transferable contents) {

              // TODO Auto-generated method stub

             

       }

}

 