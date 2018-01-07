package cs1302.pushcounterfx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.Random;

import javax.swing.JProgressBar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

public class App extends Application {
	private String search;
	private GridPane gallery;
	private Scene scene;
	private String[] artUrlArray;
	private String[] picsInUse = new String[20];
	private Timeline timeline;
	private Task task;

	public void start(Stage stage) throws MalformedURLException {
		VBox frame = new VBox();
		scene = new Scene(frame);
		scene.getStylesheets().add("/style/Basic.css");

		MenuBar menuBar = optionsMenu();
		menuBar.getStyleClass().add("style");

		HBox searchBar = searchBar();
		searchBar.getStyleClass().add("style");

		gallery = new GridPane();
		gallery.setPrefSize(500, 400);

		frame.getChildren().addAll(menuBar, searchBar, gallery);

		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	} // start

	/**
	 * Create the Menu bar which holds the File, Theme, and Help menus
	 * 
	 * @return menuBar returns the MenuBar object
	 */
	public MenuBar optionsMenu() {
		MenuBar menuBar = new MenuBar();

		// Create File menu
		Menu menuFile = new Menu("File");
		// Create items in File menu
		MenuItem add = new MenuItem("Exit");
		add.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});// Exit program on click
		menuFile.getItems().add(add);

		// Create theme menu
		Menu menuTheme = new Menu("Theme");

		// Create items in theme menu
		MenuItem retro = new MenuItem("Retro");
		retro.setOnAction(event -> {
			scene.getStylesheets().clear();
			setUserAgentStylesheet(null);
			scene.getStylesheets().add(getClass().getResource("/style/retro.css").toExternalForm());
		});// Sets style to retro

		MenuItem steamPunk = new MenuItem("SteamPunk");
		steamPunk.setOnAction(event -> {
			scene.getStylesheets().clear();
			setUserAgentStylesheet(null);
			scene.getStylesheets().add(getClass().getResource("/style/SteamPunk.css").toExternalForm());
		});// Sets style to steamPunk
		MenuItem basic = new MenuItem("Basic");
		basic.setOnAction(event -> {
			scene.getStylesheets().clear();
			setUserAgentStylesheet(null);
			scene.getStylesheets().add(getClass().getResource("/style/Basic.css").toExternalForm());
		});// Set style to basic

		menuTheme.getItems().addAll(retro, steamPunk, basic);

		// Create help menu
		Menu menuHelp = new Menu("Help");
		// Create items in help menu
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

			aboutMe.getChildren().addAll(name, email, version, pic);
			Scene scene = new Scene(aboutMe);
			Stage stage = new Stage();
			stage.setTitle("About Me");
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();
		});// Creates About Me Pane

		menuHelp.getItems().add(about);
		menuBar.getMenus().addAll(menuFile, menuTheme, menuHelp);

		return menuBar;
	}// Options Menu

	/**
	 * 
	 * @return
	 */
	public HBox searchBar() {
		Random r = new Random();

		HBox searchBar = new HBox();

		Button play = new Button();
		play.setPrefWidth(100);
		play.setText("Play");
		TextField searchField = new TextField("Search");
		Button search = new Button("Search");

		EventHandler handle = event -> newPic(r.nextInt(20));

		play.setOnAction(event -> {
			if (play.getText().equals("Pause")) {
				play.setText("Play");
				stopTimeline();
			} // if

			else if (play.getText().equals("Play")) {
				play.setText("Pause");
				resumeTimeline();
			} // if

		});

		search.setOnAction(event -> {
			setSearch(searchField.getText());
			searchField.clear();
			play.setText("Pause");
			gallery();
			startPictureCycle(handle);

		});//
		searchBar.getChildren().addAll(play, searchField, search);
		return searchBar;
	}// searchBar

	public GridPane gallery() {
		gallery.getChildren().clear();
		artUrlArray = null;
		try {
			
			Content content = new Content(getSearch());
			artUrlArray = content.getArtUrlArray();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} // try

		int count = 0;
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				if (artUrlArray != null) {

					Image img = new Image(artUrlArray[count]);
					ImageView imgView = new ImageView(img);
					imgView.setFitWidth(100);
					imgView.setFitHeight(100);
					gallery.getChildren().add(imgView);
					gallery.setRowIndex(imgView, x);
					gallery.setColumnIndex(imgView, y);
					picsInUse[count] = artUrlArray[count];
					count++;
				} // if
			} // inner for
		} // Outer for

		return gallery;
	}// gallery

	/**
	 * 
	 * @return
	 */
	public HBox progressBar() {
		HBox progress = new HBox();
		
		JProgressBar progressBar = new JProgressBar(0);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		return progress;
	}// progressBar
	/**
	 * Gets a new picture
	 * @param x the location of the picture to be replaced
	 */
	public void newPic(int x) {
		ImageView imgView = null;
		Random r = new Random();
		int randomNumber;
		boolean isInUse = true;

		while (isInUse) {
			randomNumber = r.nextInt(artUrlArray.length);
			for (int i = 0; i < 20; i++) {
				if (artUrlArray[randomNumber].equals(picsInUse[i])) {
					break;
				} else {
					Image img = new Image(artUrlArray[randomNumber]);
					imgView = new ImageView(img);
					isInUse = false;
					picsInUse[x] = artUrlArray[randomNumber];
				} // else
			} // for
		} // while
		int row = (int) (x / 4.1);
		int col = (int) (x / 5.1);
		gallery.getChildren().add(imgView);
		gallery.setRowIndex(imgView, row);
		gallery.setColumnIndex(imgView, col);
	}// newPic
	
	/**
	 * Creates a new timeline and starts the cycle animation
	 * @param handler the action that occurs every two seconds
	 */
	public void startPictureCycle(EventHandler handler) {

		Thread t = new Thread(() -> {
			KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), handler);
			timeline = new Timeline();

			Platform.runLater(() -> {
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.getKeyFrames().add(keyFrame);
				timeline.play();

			});

		});// Thread
		t.start();

	}// startPictureCycle
	/**
	 * stops the animation
	 */
	public void stopTimeline() {
		timeline.stop();
	}
	
	/**
	 * resumes the animation
	 */
	public void resumeTimeline() {
		timeline.play();
	}
	/**
	 * Gets the search variable
	 * 
	 * @return returns the search variable
	 */
	public String getSearch() {
		return search;
	}// getSearch

	/**
	 * Sets the search variable to the string from the text field and replaces any
	 * space with an underscore
	 * 
	 * @param x
	 *            String from the text field
	 */
	public void setSearch(String x) {
		search = x;
		while (x.indexOf(" ") != -1) {
			search = x.substring(0, x.indexOf(" ")) + "_" + x.substring(x.indexOf(" "));
		} // while

	}// setSearch

	public static void main(String... args) {
		try {
			Application.launch(args);
		} catch (UnsupportedOperationException e) {
			System.out.println(e);
			System.err.println("If this is a DISPLAY problem, then your X server connection");
			System.err.println("has likely timed out. This can generally be fixed by logging");
			System.err.println("out and logging back in.");
			System.exit(1);
		} // try
	}// main
}// App
