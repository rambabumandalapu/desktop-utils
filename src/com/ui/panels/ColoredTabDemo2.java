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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;

import javafx.stage.Stage;

import javafx.stage.StageStyle;

 

public class ColoredTabDemo2 extends Application implements ClipboardOwner{

       private static Properties props = new Properties();

       private static WebView webView = new WebView();
       private static WebEngine webEngine = webView.getEngine();
	
       
      public static void loadVersionProperties() {

              InputStream inputStream = null;

              try {
                   
            	  inputStream = new FileInputStream(System.getProperty("user.home")+"\\data2.properties");

                     props.load(inputStream);

 

              } catch (IOException e) {

                     e.printStackTrace();

              } finally {

                     if (inputStream != null) {

                           try {

                        	   inputStream.close();

                           } catch (IOException e) {

                                  e.printStackTrace();

                           }

                     }

              }

       }

 
    @Override

    public void start(Stage primaryStage) {

       props.getProperty("categories");

       String[] URLS = props.getProperty("categories").split(",");
       System.out.println(URLS);
        loadURL(props.getProperty(URLS[0]));
       TabPane tabPane = new TabPane();

        for (int i = 0; i < URLS.length; i++) {

            tabPane.getTabs().add(createTab(URLS[i]));

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

        WebView username = new WebView();
        
        
        EventHandler<Event> event = 

        new EventHandler<Event>() {
            public void handle(Event e)

            {

                if (tab.isSelected()) 

                {
                   
                	loadURL(props.getProperty(tabname));

                }

            }

        };

      

        // set event handler to the tab

        tab.setOnSelectionChanged(event);

               

        tab.setContent(username);

 

        return tab ;

    }

    
    private void loadURL(String url) {
    	 webEngine.load(props.getProperty(url));
         webEngine.setJavaScriptEnabled(true);
         
	}
    public static void main(String[] args) {

        launch(args);

    }

 

       @Override

       public void lostOwnership(Clipboard clipboard, Transferable contents) {

              // TODO Auto-generated method stub

             

       }

}

 