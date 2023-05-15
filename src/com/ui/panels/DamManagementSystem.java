package com.ui.panels;


import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DamManagementSystem extends Application {

	static String userHome = System.getProperty("user.home");
	static File file = new File(userHome, "my.lock");
	GridPane sp = null;
	GridPane loading = null;
	GridPane tabbedPane = null;
	
	//added newly
	WebView webView = null;   //added newly
	public static void main(String[] args) {
			Application.launch(args);
		}

	public void start(Stage primaryStage) {

		// Create the GridPane
		sp = new GridPane();  //added newly
		loading = new GridPane();   //added newly
		tabbedPane = new GridPane();
		loading.setAlignment(Pos.CENTER);
		sp.setAlignment(Pos.CENTER);
		tabbedPane.setAlignment(Pos.CENTER);
		javafx.geometry.Rectangle2D r = Screen.getPrimary().getBounds();
		sp.setMinSize(r.getWidth(), r.getHeight()-50);
		tabbedPane.setMinSize(r.getWidth(), 50);
         
		TabPane tabPane = new TabPane();
         
        Tab tab1 = new Tab("Planes", new Label("Show all planes available"));
        Tab tab2 = new Tab("Cars"  , new Label("Show all cars available"));
        Tab tab3 = new Tab("Boats" , new Label("Show all boats available"));

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabbedPane.add(tabPane, 0, 0);
       
		// Creating WebView Object
		webView = new WebView();   // modified newly

		WebEngine webEngine = webView.getEngine();
	
		// Setting priorities for GridPane

		GridPane.setHgrow(webView, Priority.ALWAYS);
		GridPane.setVgrow(webView, Priority.ALWAYS);
		GridPane.setHgrow(loading, Priority.ALWAYS);
		GridPane.setVgrow(loading, Priority.ALWAYS);
		
		GridPane.setHgrow(loading, Priority.ALWAYS);
		GridPane.setVgrow(loading, Priority.ALWAYS);
		
		
		//sp.add(webView, 0, 1);   // removed newly
		Scene scene = new Scene(sp, r.getWidth(), r.getHeight());

		// loading url dynamically through properties file
		 webEngine.load("http://192.168.0.1");
		webEngine.setJavaScriptEnabled(true);
		sp.add(createProgressReport(webView.getEngine()),  0,2);
		// prevent application being shut down when all the JavaFX windows are closed

		Platform.setImplicitExit(false);
		primaryStage.setScene(scene);
		Platform.setImplicitExit(false);

		// handling event for window close using keyboard short cut keys

		primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.F9) {
				try {
					file.deleteOnExit();
					Platform.exit();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		
		primaryStage.show();

	}

	// creating Progress indicator
	
	private GridPane createProgressReport(WebEngine engine) { // modified newly
	//private HBox createProgressReport(WebEngine engine) {   //removed newly
		final LongProperty startTime = new SimpleLongProperty();
		final LongProperty endTime = new SimpleLongProperty();
		final LongProperty elapsedTime = new SimpleLongProperty();

		ProgressIndicator progressIndicator = new ProgressIndicator();
		progressIndicator.progressProperty().bind(engine.getLoadWorker().progressProperty());
		progressIndicator.applyCss();

		final Label loadTimeLabel = new Label();

		// setting style for Label data

		loadTimeLabel.setStyle("-fx-font-family: Gafata; -fx-font-size: 30;");
		loadTimeLabel.textProperty()
				.bind(Bindings.when(elapsedTime.greaterThan(0))
						.then(Bindings.concat("Loaded page in ", elapsedTime.divide(100_000_000), "ms")).otherwise(

								" Loading, Please Wait..."));

		elapsedTime.bind(Bindings.subtract(endTime, startTime));

		// using the Worker instance available from the getLoadWorker

		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState,
					Worker.State state) {
				switch (state) {
				case RUNNING:
					startTime.set(System.nanoTime());
					break;

				case SUCCEEDED:
					endTime.set(System.nanoTime());
					sp.getChildren().remove(1);
					sp.add(webView, 0, 2);
					break;
				default:
					break;
				}
			}
		});
		 loading.add(tabbedPane, 0, 0);
		 loading.add(progressIndicator, 0, 1);
		 loading.add(webView, 0, 2);

		//return progressReport; // removed newly
         return loading; // added newly
	}

}
