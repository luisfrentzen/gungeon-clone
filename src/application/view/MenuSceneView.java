package application.view;

import application.MainApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuSceneView extends SceneView{

	private Label menuLabel;
	private ImageView titleImage;
	private Pane logoPane;
	
	private Pane buttonPane;
	
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		this.root = new StackPane();
		Image im = new Image(MenuSceneView.class.getResource("/titlescreen/backdrop/title_backdrop.png").toExternalForm()); 
		
		this.root.setBackground(new Background(new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
		
		Image logo = new Image(MenuSceneView.class.getResource("/titlescreen/title/title_logo.png").toExternalForm());
		this.titleImage = new ImageView();
		this.titleImage.setImage(logo);
		this.titleImage.setFitWidth(MainApplication.W * 0.5);
		this.titleImage.setY(MainApplication.H * 0.3);
		this.titleImage.setX((MainApplication.W / 2) - (MainApplication.W * 0.5 * 0.5));
		this.titleImage.setPreserveRatio(true);
		this.titleImage.setSmooth(true);
		this.titleImage.setCache(true);
		
		logoPane = new Pane();
		logoPane.getChildren().add(titleImage);
		
		this.root.setMinHeight(MainApplication.H);
		this.root.setMinWidth(MainApplication.W); 
		
		Button playButton = new Button("PLAY");
		Button optionButton = new Button("OPTIONS");
		Button exitButton = new Button("EXIT");
		
		Font f = Font.loadFont("file:resources/font/minecraftia/Minecraftia-Regular.ttf", MainApplication.H * 0.04);

		playButton.setId("play-btn");
		playButton.setFont(f);
		playButton.setLayoutY(MainApplication.H * 0.75);
		playButton.setLayoutX(MainApplication.W * 0.02);
		
		optionButton.setFont(f);
		optionButton.setLayoutY(MainApplication.H * 0.815);
		optionButton.setLayoutX(MainApplication.W * 0.02);
		
		exitButton.setFont(f);
		exitButton.setLayoutY(MainApplication.H * 0.88);
		exitButton.setLayoutX(MainApplication.W * 0.02);
		
		this.buttonPane = new Pane();
		this.buttonPane.getChildren().addAll(playButton, optionButton, exitButton);

		this.menuLabel = new Label("Main Menu");
	}

	@Override
	protected Pane addComponents() {
		// TODO Auto-generated method stub
		this.root.getChildren().add(menuLabel);
		this.root.getChildren().add(logoPane);
		this.root.getChildren().add(this.buttonPane);
		
		return this.root;
	}

	@Override
	protected Scene initScene() {
		// TODO Auto-generated method stub
		this.scene = new Scene(root);
		this.scene.getStylesheets().add(getClass().getResource("menu-view.css").toExternalForm());
		
		return this.scene;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFrame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderFrame() {
		// TODO Auto-generated method stub
		
	}

}
