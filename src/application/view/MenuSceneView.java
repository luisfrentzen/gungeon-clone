package application.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Database;
import application.MainApplication;
import application.controller.SoundController;
import application.model.ScoreModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class MenuSceneView extends SceneView{

	private ImageView titleImage;
	private Pane logoPane;
	
	private Pane buttonPane;
	
	private StackPane optionPane;
	private boolean onOption;
	private Font font;
	private Font fontSmall;
	
	private StackPane optSoundPane;
	private boolean onOptSound;
	
	private StackPane optGameplayPane;
	private boolean onOptGameplay;
	
	private StackPane highscorePane;
	private boolean onHighscore;
	private ResultSet highscore;
	
	private EventHandler<Event> hover;
	private Database db;
	
	private TableView<ScoreModel> tbHighscore;
	private double lastMusicVol;
	private double lastSfxVol;
	private boolean lastShake;
	private boolean lastOrientation;
	
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		this.root = new StackPane();
		this.db = Database.getConnection();
		
		this.font = Font.loadFont("file:resources/font/minecraftia/Minecraftia-Regular.ttf", MainApplication.H * 0.04);
		this.fontSmall = Font.loadFont("file:resources/font/minecraftia/Minecraftia-Regular.ttf", MainApplication.H * 0.03);

		this.hover = new EventHandler<>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_SELECT);
			}
			
		};
		
		this.initTitlePage();
		this.initOptionPage();
		this.initHighscorePage();
		this.initSoundOptPage();
		this.initGameOptPage();
	}
	
	public void initHighscorePage() {
		this.highscorePane = new StackPane();
		this.highscorePane.getStyleClass().add("option-container");
		this.highscorePane.setVisible(false);
		this.onHighscore = false;
		
		Label title = new Label("HIGHSCORE");
		title.setPrefWidth(MainApplication.W * 0.25);
		title.setLayoutX(MainApplication.W * 0.375);
		title.setLayoutY(MainApplication.H * 0.055);
		title.setFont(this.fontSmall);
		
		Button cancBtn = new Button("BACK");
		cancBtn.setPrefWidth(MainApplication.W * 0.25);
		cancBtn.setLayoutX(MainApplication.W * 0.375);
		cancBtn.setLayoutY(MainApplication.H * 0.84);
		cancBtn.setFont(this.fontSmall);
		cancBtn.setOnMouseEntered(this.hover);
		cancBtn.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				sound.playSfx(SoundController.SFX_MENU_CANCEL);
				onHighscore = false;
				highscorePane.setVisible(false);
			}
			
		});
		
		tbHighscore = new TableView<ScoreModel>();
		tbHighscore.setPrefWidth(MainApplication.W * 0.5);
		tbHighscore.setPrefHeight(MainApplication.H * 0.6);
		tbHighscore.setLayoutX(MainApplication.W * 0.33);
		tbHighscore.setLayoutY(MainApplication.H * 0.22);
		
		tbHighscore.getStyleClass().add("noheader");

		TableColumn<ScoreModel, String> name = new TableColumn<ScoreModel, String>();
		name.setText("NAME");
		name.setCellValueFactory(new PropertyValueFactory<ScoreModel, String>("name"));
		name.setPrefWidth(MainApplication.W * 0.2);
		name.setCellFactory(new Callback<TableColumn<ScoreModel,String>, TableCell<ScoreModel,String>>() {

			@Override
			public TableCell<ScoreModel, String> call(TableColumn<ScoreModel, String> arg0) {
				// TODO Auto-generated method stub
				return new TableCell<ScoreModel, String>() 
                {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(isEmpty())
                        {
                            setText("");
                        }
                        else
                        {
                            setTextFill(Color.WHITE);
                            setFont(fontSmall);
                            setText(item);
                        }
                    }
                };
			}
		});
		
		TableColumn<ScoreModel, Integer> score = new TableColumn<ScoreModel, Integer>();
		score.setText("SCORE");
		score.setPrefWidth(MainApplication.W * 0.2);
		score.setCellValueFactory(new PropertyValueFactory<ScoreModel, Integer>("score"));
		score.setCellFactory(new Callback<TableColumn<ScoreModel,Integer>, TableCell<ScoreModel,Integer>>() {

			@Override
			public TableCell<ScoreModel, Integer> call(TableColumn<ScoreModel, Integer> arg0) {
				// TODO Auto-generated method stub
				return new TableCell<ScoreModel, Integer>() 
                {
                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);

                        if(isEmpty())
                        {
                            setText("");
                        }
                        else
                        {
                            setTextFill(Color.WHITE);
                            setFont(fontSmall);
                            setText(String.format("%010d", item));
                        }
                    }
                };
			}
		});
		
		tbHighscore.getColumns().addAll(name, score);
		
		Pane controlPane = new Pane();
		controlPane.getChildren().add(cancBtn);
		controlPane.getChildren().add(title);
		controlPane.getChildren().add(tbHighscore);
		
		
		ImageView optionLayout = createImageViewByH("/layout/layout_menu.png", MainApplication.H);
		this.highscorePane.getChildren().add(optionLayout);
		this.highscorePane.getChildren().add(controlPane);
	}
	
	
	public void initGameOptPage() {
		this.optGameplayPane = new StackPane();
		this.optGameplayPane.getStyleClass().add("option-container");
		this.optGameplayPane.setVisible(false);
		this.onOptGameplay = false;
		
		Label title = new Label("GAMEPLAY");
		title.setPrefWidth(MainApplication.W * 0.25);
		title.setLayoutX(MainApplication.W * 0.375);
		title.setLayoutY(MainApplication.H * 0.055);
		title.setFont(this.fontSmall);

		Label shakeLabel = new Label("ENABLE SCREEN SHAKE");
		shakeLabel.setFont(fontSmall);
		shakeLabel.setPrefWidth(MainApplication.W * 0.3);
		shakeLabel.setAlignment(Pos.BASELINE_LEFT);
		shakeLabel.setLayoutX(MainApplication.W * 0.42);
		shakeLabel.setLayoutY(MainApplication.H * 0.425);
	
		CheckBox shakeCb = new CheckBox("");
		shakeCb.setPadding(new Insets(MainApplication.H * 0.01));
		shakeCb.setLayoutX(MainApplication.W * 0.39);
		shakeCb.setLayoutY(MainApplication.H * 0.425);
		shakeCb.setSelected(MainApplication.DEFAULT_SHAKE);

		Button resetBtn = new Button("RESET DEFAULTS");
		resetBtn.setPrefWidth(MainApplication.W * 0.25);
		resetBtn.setLayoutX(MainApplication.W * 0.375);
		resetBtn.setLayoutY(MainApplication.H * 0.84);
		resetBtn.setFont(this.fontSmall);
		resetBtn.setOnMouseEntered(this.hover);
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				shakeCb.setSelected(MainApplication.DEFAULT_SHAKE);
				MainApplication.IS_SHAKE = MainApplication.DEFAULT_SHAKE;
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
			}
			
		});
		
		Button confBtn = new Button("CONFIRM");
		confBtn.setPrefWidth(MainApplication.W * 0.25);
		confBtn.setLayoutX(MainApplication.W * 0.575);
		confBtn.setLayoutY(MainApplication.H * 0.84);
		confBtn.setFont(this.fontSmall);
		confBtn.setOnMouseEntered(this.hover);
		confBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CANCEL);
				MainApplication.IS_SHAKE = shakeCb.isSelected();
				onOptGameplay = false;
				optGameplayPane.setVisible(false);
			}
			
		});
		
		Button cancBtn = new Button("CANCEL");
		cancBtn.setPrefWidth(MainApplication.W * 0.25);
		cancBtn.setLayoutX(MainApplication.W * 0.175);
		cancBtn.setLayoutY(MainApplication.H * 0.84);
		cancBtn.setFont(this.fontSmall);
		cancBtn.setOnMouseEntered(this.hover);
		cancBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MainApplication.IS_SHAKE = lastShake;
				shakeCb.setSelected(lastShake);
				sound.playSfx(SoundController.SFX_MENU_CANCEL);
				onOptGameplay = false;
				optGameplayPane.setVisible(false);
			}
			
		});
		
		Pane controlPane = new Pane();
		controlPane.getChildren().add(resetBtn);
		controlPane.getChildren().add(cancBtn);
		controlPane.getChildren().add(confBtn);
		controlPane.getChildren().add(title);
		controlPane.getChildren().add(shakeCb);
		controlPane.getChildren().add(shakeLabel);
		
		
		ImageView optionLayout = createImageViewByH("/layout/layout_menu.png", MainApplication.H);
		this.optGameplayPane.getChildren().add(optionLayout);
		this.optGameplayPane.getChildren().add(controlPane);
		
		this.optionPane.getChildren().add(this.optGameplayPane);
	}
	
	public void initSoundOptPage() {
		this.optSoundPane = new StackPane();
		this.optSoundPane.getStyleClass().add("option-container");
		this.optSoundPane.setVisible(false);
		this.onOptSound = false;
		
		Label title = new Label("SOUND");
		title.setPrefWidth(MainApplication.W * 0.25);
		title.setLayoutX(MainApplication.W * 0.375);
		title.setLayoutY(MainApplication.H * 0.055);
		title.setFont(this.fontSmall);
		
		Label sfxVolLbl = new Label("Sound Effect Volume");
		sfxVolLbl.setFont(fontSmall);
		sfxVolLbl.setPrefWidth(MainApplication.W * 0.4);
		sfxVolLbl.setLayoutX(MainApplication.W * 0.3);
		sfxVolLbl.setLayoutY(MainApplication.H * 0.25);
		
		Pane sfxProgress = new Pane();
		StackPane sfxBar = new StackPane();
		sfxBar.setLayoutX(MainApplication.W * 0.3);
		sfxBar.setLayoutY(MainApplication.H * 0.315);
		sfxProgress.setPrefHeight(MainApplication.H * 0.05);
		sfxProgress.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		sfxProgress.setBorder(new Border(new BorderStroke(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
	            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
	            CornerRadii.EMPTY, new BorderWidths(MainApplication.W * 0.003), Insets.EMPTY)));
		sfxProgress.setPrefWidth(MainApplication.W * 0.4);
		
		Slider sfxVol = new Slider();
		sfxVol.setValue(MainApplication.SFX_VOLUME * 100);

		sfxVol.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					// TODO Auto-generated method stub
					sfxProgress.setPrefWidth(MainApplication.W * 0.4 * ((double)arg1 / 100.0));
					sound.setSoundVolume((double)arg1 / 100.0);
					MainApplication.SFX_VOLUME = ((double)arg1 / 100.0);
				}
	        });
		
		sfxVol.skinProperty().addListener((obs,old,skin)->{
            if(skin!=null){
                StackPane thumb = (StackPane)sfxVol.lookup(".thumb");
                thumb.setPrefHeight(MainApplication.H * 0.05);
            }
        });
		
		sfxVol.setPrefWidth(MainApplication.W * 0.4);
		sfxVol.setLayoutX(MainApplication.W * 0.3);
		sfxVol.setLayoutY(MainApplication.H * 0.315);
		
		Label musicVolLbl = new Label("Music Volume");
		musicVolLbl.setFont(fontSmall);
		musicVolLbl.setPrefWidth(MainApplication.W * 0.4);
		musicVolLbl.setLayoutX(MainApplication.W * 0.3);
		musicVolLbl.setLayoutY(MainApplication.H * 0.4);
		
		Pane musicProgress = new Pane();
		StackPane musicBar = new StackPane();
		musicBar.setLayoutX(MainApplication.W * 0.3);
		musicBar.setLayoutY(MainApplication.H * 0.465);
		musicProgress.setPrefHeight(MainApplication.H * 0.05);
		musicProgress.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		musicProgress.setBorder(new Border(new BorderStroke(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
	            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
	            CornerRadii.EMPTY, new BorderWidths(MainApplication.W * 0.003), Insets.EMPTY)));
		sfxProgress.setPrefWidth(MainApplication.W * 0.4);
		
		Slider musicVol = new Slider();
		musicVol.setValue(MainApplication.MUSIC_VOLUME * 100);
		musicVol.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					// TODO Auto-generated method stub
					sound.setMusicVolume((double)arg1 / 100.0);
					MainApplication.MUSIC_VOLUME = ((double)arg1 / 100.0);
				}
	        });
		
		musicVol.skinProperty().addListener((obs,old,skin)->{
            if(skin!=null){
                StackPane thumb = (StackPane)musicVol.lookup(".thumb");
                thumb.setPrefHeight(MainApplication.H * 0.05);
            }
        });
		
		musicVol.setPrefWidth(MainApplication.W * 0.4);
		musicVol.setLayoutX(MainApplication.W * 0.3);
		musicVol.setLayoutY(MainApplication.H * 0.465);
		
		Button resetBtn = new Button("RESET DEFAULTS");
		resetBtn.setPrefWidth(MainApplication.W * 0.25);
		resetBtn.setLayoutX(MainApplication.W * 0.375);
		resetBtn.setLayoutY(MainApplication.H * 0.84);
		resetBtn.setFont(this.fontSmall);
		resetBtn.setOnMouseEntered(this.hover);
		resetBtn.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MainApplication.MUSIC_VOLUME = MainApplication.DEFAULT_MUSIC_VOL;
				MainApplication.SFX_VOLUME = MainApplication.DEFAULT_SFX_VOL;
				sfxVol.setValue(MainApplication.DEFAULT_SFX_VOL * 100);
				musicVol.setValue(MainApplication.DEFAULT_MUSIC_VOL * 100);
				sound.setMusicVolume(MainApplication.DEFAULT_MUSIC_VOL);
				sound.setSoundVolume(MainApplication.DEFAULT_SFX_VOL);
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
			}
			
		});
		
		Button confBtn = new Button("CONFIRM");
		confBtn.setPrefWidth(MainApplication.W * 0.25);
		confBtn.setLayoutX(MainApplication.W * 0.575);
		confBtn.setLayoutY(MainApplication.H * 0.84);
		confBtn.setFont(this.fontSmall);
		confBtn.setOnMouseEntered(this.hover);
		confBtn.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CANCEL);
				onOptSound = false;
				optSoundPane.setVisible(false);
			}
			
		});
		
		Button cancBtn = new Button("CANCEL");
		cancBtn.setPrefWidth(MainApplication.W * 0.25);
		cancBtn.setLayoutX(MainApplication.W * 0.175);
		cancBtn.setLayoutY(MainApplication.H * 0.84);
		cancBtn.setFont(this.fontSmall);
		cancBtn.setOnMouseEntered(this.hover);
		cancBtn.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MainApplication.MUSIC_VOLUME = lastMusicVol;
				MainApplication.SFX_VOLUME = lastSfxVol;
				sfxVol.setValue(lastSfxVol * 100);
				musicVol.setValue(lastMusicVol * 100);
				sound.setMusicVolume(lastMusicVol);
				sound.setSoundVolume(lastSfxVol);
				sound.playSfx(SoundController.SFX_MENU_CANCEL);
				optSoundPane.setVisible(false);
				onOptSound = false;
			}
			
		});
		
		sfxBar.getChildren().add(sfxProgress);
		sfxBar.getChildren().add(sfxVol);
		
		musicBar.getChildren().add(musicProgress);
		musicBar.getChildren().add(musicVol);
		
		Pane controlPane = new Pane();
		controlPane.getChildren().add(sfxVolLbl);
		controlPane.getChildren().add(musicVolLbl);
		controlPane.getChildren().add(resetBtn);
		controlPane.getChildren().add(cancBtn);
		controlPane.getChildren().add(confBtn);
		controlPane.getChildren().add(title);
		controlPane.getChildren().add(sfxBar);
		controlPane.getChildren().add(musicBar);
		
		ImageView optionLayout = createImageViewByH("/layout/layout_menu.png", MainApplication.H);
		this.optSoundPane.getChildren().add(optionLayout);
		this.optSoundPane.getChildren().add(controlPane);
		
		this.optionPane.getChildren().add(this.optSoundPane);
	}
	
	public void initOptionPage() {
		this.optionPane = new StackPane();
		this.optionPane.getStyleClass().add("option-container");
		this.optionPane.setVisible(false);
		
		ImageView optionLayout = createImageViewByW("/layout/layout_menu_small.png", MainApplication.W * 0.35);
		this.onOption = false;
		
		Label optionLbl = new Label("OPTIONS");
		optionLbl.setPrefWidth(MainApplication.W * 0.3);
		optionLbl.setFont(this.font);
		optionLbl.setLayoutX(MainApplication.W * 0.35);
		optionLbl.setLayoutY(MainApplication.H * 0.29);
		
		Button soundOptBtn = new Button("SOUND");
		Button gameplayOptBtn = new Button("GAMEPLAY");
		Button backOptBtn = new Button("BACK");
		
		soundOptBtn.setPrefWidth(MainApplication.W * 0.3);
		soundOptBtn.setFont(this.font);
		soundOptBtn.setLayoutX(MainApplication.W * 0.5 - MainApplication.W * 0.15);
		soundOptBtn.setLayoutY(MainApplication.H * 0.415);
		soundOptBtn.setOnMouseEntered(hover);
		soundOptBtn.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
				lastMusicVol = MainApplication.MUSIC_VOLUME;
				lastSfxVol = MainApplication.SFX_VOLUME;
				onOptSound = true;
				optSoundPane.setVisible(true);
			}
			
		});
		
		gameplayOptBtn.setPrefWidth(MainApplication.W * 0.3);
		gameplayOptBtn.setFont(this.font);
		gameplayOptBtn.setLayoutX(MainApplication.W * 0.5 - MainApplication.W * 0.15);
		gameplayOptBtn.setLayoutY(MainApplication.H * 0.49);
		gameplayOptBtn.setOnMouseEntered(hover);
		gameplayOptBtn.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
				onOptGameplay = true;
				optGameplayPane.setVisible(true);
				lastShake = MainApplication.IS_SHAKE;
			}
			
		});
		
		backOptBtn.setPrefWidth(MainApplication.W * 0.3);
		backOptBtn.setFont(this.font);
		backOptBtn.setLayoutX(MainApplication.W * 0.5 - MainApplication.W * 0.15);
		backOptBtn.setLayoutY(MainApplication.H * 0.565);
		backOptBtn.setOnMouseEntered(hover);
		backOptBtn.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CANCEL);
				onOption = false;
				optionPane.setVisible(false);
			}
			
		});
		
		this.onOptSound = false;
		this.onOptGameplay = false;
		
		Pane layoutContainer = new Pane();
		layoutContainer.getChildren().add(optionLbl);
		layoutContainer.getChildren().add(soundOptBtn);
		layoutContainer.getChildren().add(gameplayOptBtn);
		layoutContainer.getChildren().add(backOptBtn);
		
		this.optionPane.getChildren().add(optionLayout);
		this.optionPane.getChildren().add(layoutContainer);
	}
	
	public void initTitlePage() {
Image im = new Image(MenuSceneView.class.getResource("/titlescreen/backdrop/title_backdrop.png").toExternalForm()); 
		
		this.root.setBackground(new Background(new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
//		this.root.getStyleClass().add("option-container");

		this.titleImage = createImageViewByW("/titlescreen/title/title_logo.png", MainApplication.W * 0.5);
		this.titleImage.setY(MainApplication.H * 0.3);
		this.titleImage.setX((MainApplication.W / 2) - (MainApplication.W * 0.5 * 0.5));
		
		logoPane = new Pane();
		logoPane.getChildren().add(titleImage);
		
		this.root.setMinHeight(MainApplication.H);
		this.root.setMinWidth(MainApplication.W); 
		
		Button playButton = new Button("PLAY");
		Button optionButton = new Button("OPTIONS");
		Button exitButton = new Button("EXIT");
		Button highscoreButton = new Button("HIGHSCORE");
		
		playButton.setId("play-btn");
		playButton.setFont(this.font);
		playButton.setLayoutY(MainApplication.H * 0.685);
		playButton.setLayoutX(MainApplication.W * 0.02);
		
		playButton.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
				sound.stopMusic(SoundController.MUSIC_TITLE_MENU);
				app.changeScene(MainApplication.GAME_SCENE);
			}
			
		});
		
		playButton.setOnMouseEntered(hover);
		
		highscoreButton.setFont(this.font);
		highscoreButton.setLayoutY(MainApplication.H * 0.75);
		highscoreButton.setLayoutX(MainApplication.W * 0.02);
		
		highscoreButton.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
				highscorePane.setVisible(true);
				onHighscore = true;
				
				ResultSet rs = db.executeQuery("SELECT * FROM score ORDER BY score DESC LIMIT 6");
				
				ObservableList<ScoreModel> data = FXCollections.observableArrayList();
				try {
					while(rs.next()) {
						data.add(new ScoreModel(rs.getString("username"), rs.getInt("score")));
					}
					
					tbHighscore.setItems(data);
					tbHighscore.refresh();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		highscoreButton.setOnMouseEntered(hover);
		
		optionButton.setFont(this.font);
		optionButton.setLayoutY(MainApplication.H * 0.815);
		optionButton.setLayoutX(MainApplication.W * 0.02);
		
		optionButton.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
				optionPane.setVisible(true);
				onOption = true;
			}
			
		});
		
		optionButton.setOnMouseEntered(hover);
		
		exitButton.setFont(this.font);
		exitButton.setLayoutY(MainApplication.H * 0.88);
		exitButton.setLayoutX(MainApplication.W * 0.02);
		
		exitButton.setOnAction(new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
				Platform.exit();
			}
			
		});
		
		exitButton.setOnMouseEntered(hover);
		
		this.buttonPane = new Pane();
		this.buttonPane.getChildren().addAll(playButton, highscoreButton, optionButton, exitButton);
		
		this.sound.playMusic(SoundController.MUSIC_TITLE_MENU);
	}
	
	public ImageView createImageViewByW(String path, double w) {
		Image im = new Image(MenuSceneView.class.getResource(path).toExternalForm());
		ImageView iv = new ImageView();
		iv.setImage(im);
		iv.setPreserveRatio(true);
		iv.setFitWidth(w);
		iv.setSmooth(true);
		iv.setCache(true);
		
		return iv;
	}
	
	public ImageView createImageViewByH(String path, double h) {
		Image im = new Image(MenuSceneView.class.getResource(path).toExternalForm());
		ImageView iv = new ImageView();
		iv.setImage(im);
		iv.setPreserveRatio(true);
		iv.setFitHeight(h);
		iv.setSmooth(true);
		iv.setCache(true);
		
		return iv;
	}

	@Override
	protected Pane addComponents() {
		// TODO Auto-generated method stub
		this.root.getChildren().add(this.logoPane);
		this.root.getChildren().add(this.buttonPane);
		this.root.getChildren().add(this.optionPane);
		this.root.getChildren().add(this.highscorePane);
		
		return this.root;
	}

	@Override
	protected Scene initScene() {
		// TODO Auto-generated method stub
		this.scene = new Scene(root);
		this.scene.getStylesheets().add(getClass().getResource("../style/menu-view.css").toExternalForm());

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
