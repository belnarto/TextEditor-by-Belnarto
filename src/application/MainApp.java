package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage; // background stage
	private BorderPane rootLayout; // using BorderPane is useful, because size of inner forms will be resized automatically when primaryStage will be resized
	
	// called at start of application. Creates and sets up GUI
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage; // ???
		this.primaryStage.setTitle("TextEditor by Belnarto");
		
		// need try-catch block because start methods throws exceptions
		try {
			
			Class<MainApp> cls = MainApp.class;
			URL url = cls.getResource("view/RootLayout.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			rootLayout = (BorderPane) loader.load(); // NB! не понимаю как эта штука работает			
			Scene scene = new Scene(rootLayout); // using rootLayout as parent for scene
			primaryStage.setScene(scene);
			
			// setting up stage size base on screen(monitor) size
			List<Screen> screens = Screen.getScreens();
			if (screens.size()>0) {
				double height = screens.get(0).getBounds().getHeight() * 0.75;
				double width = screens.get(0).getBounds().getWidth() * 0.75;
	            primaryStage.setHeight(height);
	            primaryStage.setWidth(width);
	            primaryStage.setMinHeight(800); 
	            primaryStage.setMinWidth(800); 
			} 
			else {
	            primaryStage.setMinHeight(800); 
	            primaryStage.setMinWidth(800); 
			}
			
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		addElements(); // ???
	}

	
	/**
     * Adds elements on the scene from XML 
     */
    public void addElements() {
        try {
        	
        	Class<MainApp> cls = MainApp.class;
    		URL url = cls.getResource("view/TextAppLayout.fxml");
    		FXMLLoader loader = new FXMLLoader(url);
    		
            HBox textProPage = (HBox) loader.load(); // NB! не понимаю как эта штука работает	
            rootLayout.setCenter(textProPage); // NB! не понимаю как эта штука работает	
            
            // Connect controller and main app
            TextProController controller = loader.getController(); // NB! не понимаю как эта штука работает	
            controller.setMainApp(this); // NB! не понимаю как эта штука работает	
            
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

    }

    // SHOW NEW STAGE METHODS
    
    /**
     * Shows dialog for user input error
     * 
     * @param inErr - message to display
     */
    public void showInputErrorDialog(String inErr) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Input Error");
		alert.setContentText(inErr);

		alert.showAndWait();
    }

    /**
     * Displays dialog that allows user to select local text file to display in TextArea
     * 
     * @param ta - reference to TextArea to display loaded text file
     * 
     */
    public void showLoadFileDialog(AutoSpellingTextArea ta) {
    	try {
    		// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/LoadFileLayout.fxml"));
			VBox page = (VBox) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Load File");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set reference to stage in controller
			LoadFileDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			// give controller reference to text area to load file into
			controller.setTextArea(ta);

			// Show the dialog and wait until the user closes it
		    dialogStage.showAndWait();

    	} catch (IOException e) {
    		// Exception gets thrown if the fxml file could not be loaded
    		e.printStackTrace();
    	}
    }    

    public void showEditDistanceDialog(String selectedText) {
    	try {
    		// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/EditDistanceLayout.fxml"));
			VBox page = (VBox) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Calculate Edit Distance");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set reference to stage in controller
			EditDistanceDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(this);
			controller.setField(selectedText);

			// Show the dialog and wait until the user closes it
		    dialogStage.showAndWait();

    	} catch (IOException e) {
    		// Exception gets thrown if the fxml file could not be loaded
    		e.printStackTrace();
    	}
    }

    public void showLoadStage(Stage loadStage, String text) {
    	loadStage.initModality(Modality.APPLICATION_MODAL);
    	loadStage.initOwner(primaryStage);
        VBox loadVBox = new VBox(20);
        loadVBox.setAlignment(Pos.CENTER);
        Text tNode = new Text(text);
        tNode.setFont(new Font(16));
        loadVBox.getChildren().add(new HBox());
        loadVBox.getChildren().add(tNode);
        loadVBox.getChildren().add(new HBox());
        Scene loadScene = new Scene(loadVBox, 300, 200);
        loadStage.setScene(loadScene);
        loadStage.show();
    }
    
    public void showEDResult(List<String> path) {
        // intialize alert/dialog to display edit distance result
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Result");
    	alert.setHeaderText("Word Path : ");
    	alert.initModality(Modality.NONE);
    	alert.setResizable(true);
    	
    	// create layout for content
    	VBox box = new VBox();
    	HBox midBox = new HBox();
    	box.setPadding(new Insets(35,0,35,0));
    	box.setSpacing(35);
    	midBox.setSpacing(15);
    	
    	Label pathLabel = new Label();
    	Label numStepsLabel = new Label("Number of steps : ");
    	Label numSteps = new Label();
    	Font font = new Font(14);
    	pathLabel.setFont(font);
    	numStepsLabel.setFont(font);    	
    	numSteps.setFont(Font.font(font.getFamily(), FontWeight.BOLD, 14));
    	
    	midBox.getChildren().add(numStepsLabel);
    	midBox.getChildren().add(numSteps);
    	midBox.setAlignment(Pos.CENTER);
    	
    	box.getChildren().add(pathLabel);
    	box.getChildren().add(midBox);
    	box.setAlignment(Pos.CENTER);
    	alert.getDialogPane().setPrefWidth(300);
    	
    	// check for path
    	if(path != null) {
    		numSteps.setText(Integer.toString(path.size()-1));
	    	pathLabel.setText(String.join(" -> ", path));
	    	
	    	Text text = new Text(pathLabel.getText());
	    	text.setFont(font);
	    	if(text.getLayoutBounds().getWidth() > 200) {
		    	alert.getDialogPane().setPrefWidth(text.getLayoutBounds().getWidth()+100);
	    	}
	    	
    	}
    	// no path found
    	else {
    		pathLabel.setText("No Path Found.");
    		numSteps.setText("N/A");
    	}
    	
    	// set content and styling
    	alert.getDialogPane().setContent(box);
    	alert.getDialogPane().getStylesheets().add(
    			   getClass().getResource("application.css").toExternalForm());
    	alert.getDialogPane().getStyleClass().add("myDialog");
    	alert.showAndWait();
    }

    public void showMarkovDialog(textgen.MarkovTextGenerator mtg) {
    	try {
    		// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MarkovLayout.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Markov Text Generator");
			//dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set reference to stage in controller
			//BUG -- when first displayed results don't show up until resize window
			MarkovController controller = loader.getController();
			//controller.setDialogStage(dialogStage);
			controller.setMainApp(this);
			controller.setMTG(mtg);

			// Show the dialog and wait until the user closes it
		    dialogStage.showAndWait();
    	} catch (IOException e) {
    		// Exception gets thrown if the fxml file could not be loaded
    		e.printStackTrace();
    	}
    }
    
	// main method which only launches GUI by start method 
	public static void main(String[] args) {	
		launch(args);
	}

}
