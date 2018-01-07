package cs1302.pushcounterfx;

import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.geometry.Pos;


public class WebApp extends Application {

    public String picUrl;
    public Pane pane3;
    public Scene scene;
    public Stage mainStage;
    
    @Override
    public void start(Stage stage) throws MalformedURLException {
    	
    
	VBox pane = new VBox();
	
	MenuBar menu = optionsMenu();
	Pane pane2 = searchMenu();
	pane3 = new Pane();

	menu.prefWidthProperty().bind(stage.widthProperty());
	pane.getChildren().addAll(menu,pane2,pane3);
	
    scene = new Scene(pane);
    mainStage = stage;
    stage.setScene(scene);
	stage.sizeToScene();
    stage.show();
    } // start
    
    /**
     * Creates the options menu 
     * @return menuBar the menu that contains file themes and help
     */
    public MenuBar optionsMenu() {
    	MenuBar menuBar = new MenuBar();
    	
    	//File menu and items under it
    	Menu menuFile = new Menu("File");
    	//Menu items
    	MenuItem add = new MenuItem("Exit");
    	add.setOnAction(event -> {
    		Platform.exit();
    		System.exit(0);
    	});
    	menuFile.getItems().add(add);
    	
    	//Theme menu and items under it
    	Menu menuTheme = new Menu("Theme");
    	//Menu items
    	MenuItem retro = new MenuItem("Retro");
    	retro.setOnAction(event -> {
    		
    	});
    	MenuItem steamPunk = new MenuItem("SteamPunk");
    	retro.setOnAction(event -> {
    		
    	});
    	MenuItem basic = new MenuItem("Basic");
    	retro.setOnAction(event -> {
    		
    	});
    	
    	menuTheme.getItems().addAll(retro,steamPunk,basic);
    	//Help menu and items under it
    	Menu menuHelp = new Menu("Help");
    	//Menu items
    	MenuItem about = new MenuItem("About");
    	about.setOnAction(event -> {
    		VBox aboutMe = new VBox();
    		Label name = new Label("Name: Lucas Justice"); 
    		Label email = new Label("Email: ljj54024@uga.edu"); 
    		Label version = new Label("Version 6.9.69");
    		Image image = new Image("lucas.jpg");
    		ImageView pic = new ImageView();
    		pic.setFitHeight(250);
    		pic.setFitWidth(250);
    		pic.setImage(image);
    		
    		aboutMe.getChildren().addAll(name,email,version,pic);
    		Scene scene = new Scene(aboutMe);
    		Stage stage = new Stage();
    		stage.setTitle("About Me");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
    	});
    	menuHelp.getItems().add(about);
    	
    	
    	menuBar.getMenus().addAll(menuFile,menuTheme,menuHelp);
    	
    	return menuBar;
    }
    
    /**
     * 
     * @return
     */
    public Pane searchMenu() {
    	
    	HBox paneBar2 = new HBox();
    	
    	TextField text = new TextField("Search");
    	Button search = new Button("Search"); 
    	search.setOnAction(event -> {
    		String searchBy = text.getText();
    		try {
				gallery(searchBy);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	
    	paneBar2.getChildren().addAll(text, search);
    	
    	return paneBar2;
    }
    
    public Pane gallery(String searchBy) throws MalformedURLException {
    	HBox galleryPane = new HBox();
    	
		Content gallery = new Content(searchBy);
		String[] galleryArray = gallery.getArtUrlArray();
		
		
    	String[] artUrlArray;
    	for(int x = 0; x < 4; x++) {
    		for(int y = 0; y < 5;y++) {
    			
    		}
    	}
    	return galleryPane;
    }
    public static void main(String[] args) {
	try {
	    Application.launch(args);
	} catch (UnsupportedOperationException e) {
	    System.out.println(e);
	    System.err.println("If this is a DISPLAY problem, then your X server connection");
	    System.err.println("has likely timed out. This can generally be fixed by logging");
	    System.err.println("out and logging back in.");
	    System.exit(1);
	} // try
    } // main  

} // PushCounterApp
