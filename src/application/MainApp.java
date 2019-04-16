package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    
	// main method which only launches GUI by start method 
	public static void main(String[] args) {	
		launch(args);
	}

}
