/* NAMES: Dat Bui
 * PROGRAM DESCRIPTION: This program is very similar to the Battleship game. The rules are pretty concrete: a player 
 * 						sets the ships on the gameboard, then the player goes first to hit the enemy gameboard. The player 
 * 						moves and then the enemy does. The game alternate turns regardless of if you hit or miss the enemy ships.
 * 						However, if you fully destroy a ship, you get another turn to play-this applies to enemy as well.
 * 						The first person to hit all the other player's ships wins the game. Winning saves your name and score
 * 						and the losers tale is never told. The program works by mainly incorporating the buttons arrays and collisions.
 * 						The player moves the ships to place them with the mouse, collisions check these positions over a button array.
 * 						To make it fair, the CPU cannot access the location of the player's ships. Thus, the program will use logic to 
 *						enhance its chance of hitting by prioritizing the locations besides the previous location that hits the player's
 *						ship. It also follows the direction from the previous move to hit the 3-square ship. This covers the main gameplay,
 *						some other treats include the moving animations, audio, and the score writing. These use timeline timers and 
 *						keyframs, the files class, and the media and mediaplayer class to play sound.
 * 				
 * PROGRAM DETAILS: 1) JavaFX Components: the program includes JavaFX components such as: alerts (display message when the player wins/loses/exits/reads
 * 										instructions), buttons (for the gameboard of the CPU and the player, the buttons on the menu page for the user
 * 										to do certain actions: instruction button, play button, high score button, exit button), labels (display texts),
 * 										text area (display the scoreboard).
 * 
 * 					
 * 					2) Use of Layouts: The main layouts used are pane, gridpane, flowpane, tilepane, and hbox. The gridpane layout is used in the scores
 * 										menu. here the textarea and the buttons are put into the gridpane to nicely fit them into the window. Within this
 * 										gridpane is a flowpane which makes the buttons appear like in a hbox and evenly spaced. The tilepane is evidently 
 * 										used in the game boards where the buttons are all sequentially places in a 5x5 tile grid to simulate a game board grid.
 * 										The hbox is used in the main menu to organize the main menu buttons in a horizontal uniform space.
 * 					
 * 					
 * 					3) 1D and 2D Arrays: 1D array is used for the image of the ships. The program also uses 2D array for the buttons for the CPU and 
 * 										the player as their boards. 2D array is also used to record the x and y coordinates of the CPU's moves so that 
 * 										the program can analyze the next move that has a higher chance to hit. 
 * 
 * 
 * 					4) Sorting/Searching: The game uses the bubble sorting method and the linear search method or brute force method.
 * 										Bubble sort is used to sort the player score entries by the value of the score-either ascending or descending.
 * 										This happens when the sort high or sort low button is pressed in the scores menu. The linear search 
 * 										is used to search for a player name to find their score. This linear search searches all the names
 * 										read from the saved .txt file and checks the associated score if a match is found.
 * 
 * 
 * 					5) Classes (OOP): There is the Highcores class and the player profile storage class which interact together. The player profile class is used
 * 										store the name and score values of the profile, this is so the highscore class can access them at the endgame in order to write
 * 										a score. The highscores class is used to control everything in the scores menu including: searching names, sorting score
 * 										(either ascending or descending order) using bubble sort, and clearning all names and scores. 
 * 
 * 
 * 					5) Inheritance: This is used in the player profile storage class where the highscores class is extended.  This is used so the player profile can be 
 * 									passed in to the the highscores class using the keyword super. Essentially, the player profile class calls its super class method and 
 * 									passes in the stored name and score variables.
 * 
 * 
 * 					6) Animation: There is a lot of animation in the game, including: timeline and keyframe animations such as bird, background IM GONNA NEED THAT BOAT,
 * 									and the sunset boat docking background for a more temperate feel. We also have componenets in the animation timer such as the the torpedo and fire/water
 * 									explosions when the ship hits. The timelines mainly repeat things over and over. for example, the gif files are split into multiple images and are changes
 * 									at the fast pace to mimik animation of the background. The bird timeline also randomly moves the bird in either direction every set amount of time. The animation
 * 									timer controls more intricate animations that repeat at any moment like the explosions due to the player clicking the buttons. This happens as required and moves
 * 									a torpedo to the selected button, then spins and fades away a reelvant explosion type (hit/miss) at where the player pressed the button
 * 
 * 			
 * 					7) File Class: The file class is extensively used in the highscores class. This includes the file, file reader/writer and buffered reader/writer.
 * 									The data file called highScores.txt is made and stored in the root of the project folder. This permanently keeps a record od past
 *  								players scores and names. The file reader is used to scan the the stores data in the txt file to display the info in the text area.
 *  								The writer is used to take in the current player name and score and if they win the game, their name and score is saved as a record
 *  								to the next players who wish to see. The file is appended too so no data is overwritten. Finally the file can be cleared by creating a 
 *  								new data file in the root of project folder.
 * 
 * 
 * 					8) ArrayLists: ArrayList is used to track the x and y coordinates of the CPU's previous move so that the program can analyze 
 * 										the next move that follows the direction that it follows to increase the chance of winning for the CPU. The 
 * 										program also uses an ArrayList for the checkers objects since the number of checkers changes over time. In
 * 										the high score class, ArrayList is also used to obtain the player's name and score since the number of 
 * 										times the player plays is dynamic. 
 *
 * 
 *					9) EXTRAS: To make the game more engaging, music will be played throughout the game. This used the media and mediaplayer classes.
 *								Sound is converted to URI to play in java. The sound is looped until the game ends, thereafter, it is restarted. 
 */

package application;

//import classes
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class BattleshipMain extends Application {

	// deep computing subroutine engine
	private boolean NEURAL_ENGINE_ACTIVE = true;

	// audio vars
	private Media sound;
	private MediaPlayer mplayer;

	// define local vars
	private ImageView iviewMenuBackground;
	private Image imgBackground;
	public Pane root;
	private Scene scene;
	private int width, height;
	private AnimationTimer timer;
	private boolean birdExists = false;
	private int birbDir = 0, yPosBirb = 0;

	// game play vars
	private Random rnd;
	private Image imgExplosion, imgMissplosion;
	private ImageView ivBackplay;
	public Button[][] p1shipPos;
	private Button[][] CPUshipPos;
	private TilePane p1TilePane;
	private ImageView[] shipsIVarray, shipsIVarray2;
	private int shipNum = 0, buttonDisabledCount = 0, p1HitCount = 0;
	public int checkerCount = -1, CPUHitCount = 0;
	private boolean setShips = false;
	private int[][] CPUTableInt = new int[5][5];
	private int[][] p1TableInt = new int[5][5];
	public ArrayList<HitAndMiss> checkers;
	private ArrayList<Integer> CPUHitPos;
	private int completelyDestroy0, completelyDestroy1, completelyDestroy2;
	private int completelyDestroyCPU0, completelyDestroyCPU1, completelyDestroyCPU2;
	private int[] arrayHoldCPUPos;
	private int ship0Dir = 0, ship1Dir = 0, dir, dirCount;
	private final int UP = 0, DOWN = 180, RIGHT = 90, LEFT = 270;
	private boolean keyReleased = false;

	// menu vars
	private HBox buttons;
	private Label title;
	private Image imgTitle;
	private ImageView iviewTitle;

	// instruction vars
	private Alert instructionsAlert;
	private Button placeShipButton;
	protected double mouseY;
	protected double mouseX;

	// high score vars
	public String playerName = "";
	private Label setShipsLabel;
	private ImageView[] menubackAnimation;
	private boolean onMenu = true;
	private ImageView[] backAnimation;
	private Highscores highscore;
	private TextArea scorelogs;
	public int scoreSeconds = 0;
	private Animation secondsTimer;
	public boolean hitAnimation = false;
	public boolean missAnimation = false;
	private ImageView ivtorp;
	private ImageView ivExplosion;
	private double animationYpos;
	private ImageView ivMissplosion;
	private double animationXpos;
	protected boolean missplosionAdded;
	protected boolean explosionAdded;
	private ImageView instructionsBack;
	private PlayerProfileStorage pstorage;

	// this variable controls hit and miss epxlosions enabled or disabled
	protected boolean gameOver = false;
	protected boolean playerTurn = true;
	protected ImageView ivBirb;

	// reset variables
	public void resetVariables() {

		// reset all variables like defined in start
		shipNum = 0;
		buttonDisabledCount = 0;
		checkerCount = -1;
		p1HitCount = 0;
		CPUHitCount = 0;
		gameOver = false;
		CPUHitPos = new ArrayList<Integer>();
		shipNum = 0;
		buttonDisabledCount = 0;
		p1HitCount = 0;
		checkerCount = -1;
		CPUHitCount = 0;
		setShips = false;
		keyReleased = false;
		onMenu = true;
		scoreSeconds = 0;
		hitAnimation = false;
		missAnimation = false;
		gameOver = false;
		playerTurn = true;

	}

	public void start(Stage primaryStage) {
		try {

			// init audio stuff and play the sound immediately
			sound = new Media(new File("bgmega.wav").toURI().toString());
			mplayer = new MediaPlayer(sound);
			mplayer.setVolume(.25);
			mplayer.setCycleCount(MediaPlayer.INDEFINITE);
			mplayer.play();

			// setup layout
			root = new Pane();

			// access the neural engine boolean to make it look like it does something
			if (NEURAL_ENGINE_ACTIVE == true) {
				NEURAL_ENGINE_ACTIVE = true;
			}

			// setup image and iview for menu background and
			imgBackground = new Image("file:menu_background.jpg");
			iviewMenuBackground = new ImageView(imgBackground);
			iviewMenuBackground.setFitHeight(600);
			iviewMenuBackground.setFitWidth(1000);

			// setup width and height for playbackground
			width = 1000;
			height = 600;

			// setup the scene
			scene = new Scene(root, width, height);

			// setup random class
			rnd = new Random();

			// setup high scores class and read all the data from the file
			highscore = new Highscores();
			highscore.readScore();

			// set window event for close x button
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent e) {

					// create the two possible alerts, and create graphic for alerts
					Alert exit = new Alert(AlertType.CONFIRMATION,
							"Are you sure you want to abandon ship? Score will not be saved!", ButtonType.YES,
							ButtonType.NO);
					Alert bye = new Alert(AlertType.INFORMATION, "Thank you for playing!", ButtonType.OK);
					ImageView exitGraphic = new ImageView(new Image("file:gameExit.png"));

					// setup two alerts settings
					exit.setTitle("Exit Game...");
					exit.setHeaderText(null);
					exit.setGraphic(exitGraphic);

					bye.setHeaderText(null);
					bye.setTitle("Goodbye...");

					// store the result of the alert button type
					Optional<ButtonType> result = exit.showAndWait();

					// remove alert if no, show bye alert and exit if yes
					if (result.get() == ButtonType.YES) {
						bye.setGraphic(exitGraphic);
						bye.showAndWait();
						System.exit(0);
					} else {
						e.consume();
					}

				}
			});

			// mouse pressed event to control 2d array
			scene.setOnMouseMoved(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent e) {

					// update mouse x and y positions if mouse is moved.
					mouseX = e.getSceneX();
					mouseY = e.getSceneY();

				}
			});

			// key pressed and released event
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				public void handle(KeyEvent e) {

					// rotate the ships when the LEFT and RIGHT arrow keys are pressed
					if (e.getCode() == KeyCode.RIGHT) {
						if (setShips) {
							if (keyReleased == false) {
								keyReleased = true;
								shipsIVarray[shipNum].setRotate(shipsIVarray[shipNum].getRotate() + 90);
							}
						}
					} else if (e.getCode() == KeyCode.LEFT) {

						if (setShips) {
							if (keyReleased == false) {
								keyReleased = true;
								shipsIVarray[shipNum].setRotate(shipsIVarray[shipNum].getRotate() - 90);
							}
						}

						// set the ships down once the space bar is pressed
					} else if (e.getCode() == KeyCode.SPACE) {
						if (setShips) {
							// check number of buttons disabled in one for loop cycle
							for (int i = 0; i < 5; i++) {
								for (int j = 0; j < 5; j++) {
									if (shipsIVarray[shipNum].getBoundsInParent()
											.intersects(p1shipPos[i][j].getBoundsInParent())) {

										// itterate if true
										buttonDisabledCount++;
									}

								}
							}

							// check that the mouse is within the grid to avoid player placing ship off
							// screen
							if (mouseX > width / 2 - 150 + shipsIVarray[shipNum].getFitWidth() / 2
									&& mouseX < width / 2 + 150 - shipsIVarray[shipNum].getFitWidth() / 2
									&& mouseY > height / 2 - 150 + shipsIVarray[shipNum].getFitHeight() / 2
									&& mouseY < height / 2 + 150 - shipsIVarray[shipNum].getFitHeight() / 2) {

								// check if number of disabled buttons is correct for current ship (for example:
								// first ship covers 2 squares
								if (shipNum == 0) {
									if (buttonDisabledCount == 2) {
										// disable colliding buttons to exclude them from later ship selections
										for (int i = 0; i < 5; i++) {
											for (int j = 0; j < 5; j++) {
												if (shipsIVarray[shipNum].getBoundsInParent()
														.intersects(p1shipPos[i][j].getBoundsInParent())) {
													// mark the buttons as "chosen" and save the x and y coordinates of
													// the chosen button to a 2D array
													p1shipPos[i][j].setAccessibleHelp("chosen");
													p1TableInt[i][j] = shipNum;
												}
											}
										}

										// move to next ship
										shipNum++;

										// move to next ship imageview and add to root
										root.getChildren().addAll(shipsIVarray[shipNum]);
									}
									// if it is the second ship
								} else if (shipNum == 1) {
									boolean check = true;
									// check first that current ship doesnt collide with last ship for all buttons
									for (int i = 0; i < 5; i++) {
										for (int j = 0; j < 5; j++) {
											// check if colliding
											if (shipsIVarray[shipNum].getBoundsInParent()
													.intersects(p1shipPos[i][j].getBoundsInParent())) {
												// check if button already disabled
												if (p1shipPos[i][j].getAccessibleHelp() == "chosen") {
													check = false;
												}
											}
										}
									}

									if (check) {
										if (buttonDisabledCount == 3) {
											for (int i = 0; i < 5; i++) {
												for (int j = 0; j < 5; j++) {
													// only set relevant buttons
													if (shipsIVarray[shipNum].getBoundsInParent()
															.intersects(p1shipPos[i][j].getBoundsInParent())) {
														// set border and mark the button as "chosen"
														p1shipPos[i][j]
																.setBorder(new Border(new BorderStroke(Color.BLACK,
																		BorderStrokeStyle.SOLID, null, null)));
														p1shipPos[i][j].setAccessibleHelp("chosen");

														// record the x and y coordinates of the button to a 2D array
														// for further use
														p1TableInt[i][j] = shipNum;
													}
												}
											}

											// move to next ship imageview and add to root
											shipNum++;
											root.getChildren().addAll(shipsIVarray[shipNum]);
										}

									}
								} else {
									boolean check = true;
									// check first that current ship doesnt collide with last ship for all buttons
									for (int i = 0; i < 5; i++) {
										for (int j = 0; j < 5; j++) {
											// check if colliding
											if (shipsIVarray[shipNum].getBoundsInParent()
													.intersects(p1shipPos[i][j].getBoundsInParent())) {
												// check if button already disabled
												if (p1shipPos[i][j].getAccessibleHelp() == "chosen") {
													check = false;
												}
											}
										}
									}

									if (check) {
										if (buttonDisabledCount == 4) {
											// set disabled and stroke only for relevant colliding buttons
											for (int i = 0; i < 5; i++) {
												for (int j = 0; j < 5; j++) {
													// only set relevant buttons
													if (shipsIVarray[shipNum].getBoundsInParent()
															.intersects(p1shipPos[i][j].getBoundsInParent())) {
														// set border and disable buttons
														p1shipPos[i][j]
																.setBorder(new Border(new BorderStroke(Color.BLACK,
																		BorderStrokeStyle.SOLID, null, null)));
														p1shipPos[i][j].setAccessibleHelp("chosen");
														placeShipButton.setDisable(false);

														p1TableInt[i][j] = shipNum;
													}
												}
											}
											// disable set ship phase
											setShips = false;
										}

									}

								}

							}
							// reset buttons before re running for loop
							buttonDisabledCount = 0;
						}

					}
				}
			});

			// KeyReleased method so that the player cannot keep rotating the ships forever
			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {

					if (e.getCode() == KeyCode.RIGHT) {
						if (setShips) {
							keyReleased = false;
						}
					} else if (e.getCode() == KeyCode.LEFT) {

						if (setShips) {
							keyReleased = false;
						}

					}

				}
			});

			// call the menu method to show menu
			menuStart();

			// create imageview array for menu background animation frames and counter for
			// frame number
			menubackAnimation = new ImageView[43];

			// for loop adds all 43 the images to array
			for (int a = 0; a < menubackAnimation.length; a++) {
				menubackAnimation[a] = new ImageView(new Image("file:menubackFrames\\" + a + ".png"));
				menubackAnimation[a].setFitHeight(height);
				menubackAnimation[a].setFitWidth(width);
			}

			// create imageview array for regular background animation frames and counter
			// for frame number
			backAnimation = new ImageView[16];

			// for loop adds all 16 the images to array
			for (int a = 0; a < backAnimation.length; a++) {
				backAnimation[a] = new ImageView(new Image("file:backFrames\\" + a + ".png"));
				backAnimation[a].setFitHeight(height);
				backAnimation[a].setFitWidth(width);

			}

			// make keyframe to control the bird random flying animation to reflect a
			// and make related timer
			KeyFrame kfBirb = new KeyFrame(Duration.millis(9000), new EventHandler<ActionEvent>() {

				public void handle(ActionEvent e) {

					if (!birdExists) {
						// random bird direction
						birbDir = rnd.nextInt(2);

						if (birbDir == 0) { // move right and set directional image

							ivBirb = new ImageView(new Image("file:birdR.png"));
							ivBirb.setLayoutX(-ivBirb.getImage().getWidth()); // position left side
						} else if (birbDir == 1) { // move left and set directional image

							ivBirb = new ImageView(new Image("file:birdL.png"));
							ivBirb.setLayoutX(width); // position right side
						}

						// generate random height and set and add to root and change naimation variable
						yPosBirb = rnd.nextInt(height - (int) ivBirb.getImage().getHeight());
						ivBirb.setLayoutY(yPosBirb);
						root.getChildren().add(ivBirb);
						birdExists = true;
					}
				}
			});

			// Timerline for the bird
			Timeline timerBirb = new Timeline(kfBirb);
			timerBirb.setCycleCount(Timeline.INDEFINITE);
			timerBirb.play();

			// make keyframe and timer timeline to countdown seconds elapsed for players
			// game
			KeyFrame kfSeconds = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {

				public void handle(ActionEvent e) {
					scoreSeconds++; // add amount of seconds by 1 every seconds to record player score
				}
			});

			secondsTimer = new Timeline(kfSeconds);
			secondsTimer.setCycleCount(Timeline.INDEFINITE);

			// make keyframe for background animation and corresponding timer
			KeyFrame kfBackground = new KeyFrame(Duration.millis(110), new EventHandler<ActionEvent>() {

				// value to count frame numbers of animation background
				private int backAnimationCounter;

				public void handle(ActionEvent e) {

					if (onMenu) {
						// set counter to 0 to loop again
						if (backAnimationCounter > 42) {
							backAnimationCounter = 0;
						}

						// init counter to 0 only if old background is in place
						if (root.getChildren().contains(ivBackplay)) {
							backAnimationCounter = 0;
						}

						// add next frame to children
						root.getChildren().remove(0);
						root.getChildren().add(0, menubackAnimation[backAnimationCounter]);

						// itterate to next frame
						backAnimationCounter++;

					} else if (onMenu == false) {
						// set counter to 0 to loop again
						if (backAnimationCounter > 15) {
							backAnimationCounter = 0;
						}

						// init counter to 0 only if old background is in place
						if (root.getChildren().contains(ivBackplay)) {
							backAnimationCounter = 0;
						}

						// add next frame to children
						root.getChildren().remove(0);
						root.getChildren().add(0, backAnimation[backAnimationCounter]);

						// itterate to next frame
						backAnimationCounter++;
					}

				}
			});

			// Timeline for the background
			Timeline backgroundTimer = new Timeline(kfBackground);
			backgroundTimer.setCycleCount(Timeline.INDEFINITE);
			backgroundTimer.play();

			timer = new AnimationTimer() {

				public void handle(long now) {

					// this code checks the ships number hit by either cpu or player and ends the
					// game in runnable because alerts are here
					Platform.runLater(new Runnable() {

						public void run() {

							// test if win conditions are met if game is not over
							if (!gameOver) {

								if (p1HitCount == 9) {
									// set gameover to true
									gameOver = true;

									// player 1 win

									// stop the score timer and save a score result by calling the player profile
									// class to handle the inheritance interactions between classes
									secondsTimer.stop();
									try {
										pstorage = new PlayerProfileStorage(playerName, scoreSeconds);
									} catch (Exception e) {

										e.printStackTrace();
									}

									// create alert that tells you win and score
									Alert win = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
									win.setHeaderText(null);
									win.setTitle("You WIN!");
									win.setContentText(
											"You won the naval warfare. Enemy fleet destroyed! Your valiance will be known...");
									win.setGraphic(new ImageView(new Image("file:captainWinning.png")));
									win.showAndWait();

									// go back to menu
									menuStart();

								} else if (CPUHitCount == 9) {
									// set gameover to true
									gameOver = true;

									// CPU wins

									// stop the score timer
									secondsTimer.stop();

									// create alert that tells you lose
									Alert lose = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
									lose.setHeaderText(null);
									lose.setTitle("You LOSE!");
									lose.setContentText(
											"Oh no! You lost the game, you ships have been absolutely decimated...nobody lives to tell your tale...");
									lose.setGraphic(new ImageView(new Image("file:captainRage.png")));
									lose.showAndWait();

									// go back to menu
									menuStart();
								}
							}
						}

					});

					// start animation for bird if created
					if (birdExists) {
						// move the bird based on direction
						if (birbDir == 0 && root.getChildren().contains(ivBirb)) { // move right
							ivBirb.setX(ivBirb.getX() + 3);

							// check for out of bounds directional right
							// check if bird out of bounds
							if (ivBirb.getX() > width) {

								// remove the root and disable boolean
								birdExists = false;
								root.getChildren().remove(ivBirb);

							}

						} else if (birbDir == 1 && root.getChildren().contains(ivBirb)) { // move left
							ivBirb.setX(ivBirb.getX() - 3);

							// check for out of bounds directional left
							// check if bird out of bounds
							if (ivBirb.getX() < -width) {

								// remove the root and disable boolean
								birdExists = false;
								root.getChildren().remove(ivBirb);

							}
						}

					}

					if (setShips) {
						// update button of ship number
						// placeShipButton.setText("SET SHIP " + shipNum + " OF 3");
						/*
						 * mouseX -= (p1TilePane.getLayoutX()); mouseY-=(p1TilePane.getLayoutY());
						 */
						shipsIVarray[shipNum].setX(mouseX - shipsIVarray[shipNum].getImage().getWidth() / 2);
						shipsIVarray[shipNum].setY(mouseY - shipsIVarray[shipNum].getImage().getHeight() / 2);

						// check if imageview intersects button
						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 5; j++) {

								if (shipsIVarray[shipNum].getBoundsInParent()
										.intersects(p1shipPos[i][j].getBoundsInParent())) {
									p1shipPos[i][j].setBorder(new Border(
											new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));

								} else {
									p1shipPos[i][j].setBorder(Border.EMPTY);
								}
							}
						}

					}

					// animation the movement of the torp down
					if (root.getChildren().contains(ivtorp)) {
						// if torpedo is not fully moved yet move it down
						if (ivtorp.getY() < animationYpos) {
							ivtorp.setY(ivtorp.getY() + 8);

						} else {

							root.getChildren().remove(ivtorp); // remove ivtorp and move out of loop once done moving
						}

					}

					if (!root.getChildren().contains(ivtorp) && hitAnimation) { // animate hit explosion after
						// torpedo

						if (!explosionAdded) {
							// place the torpedo in the x and y positions according to animation pos vars
							// and add to root
							ivExplosion.setLayoutX(animationXpos - imgExplosion.getWidth() / 2 + 25);
							ivExplosion.setLayoutY(animationYpos - imgExplosion.getHeight() / 2 + 25);
							root.getChildren().add(ivExplosion);
							explosionAdded = true;
						}

						// move the explosion animation
						if (root.getChildren().contains(ivExplosion)) {
							ivExplosion.setRotate(ivExplosion.getRotate() + 9);
							ivExplosion.setOpacity(ivExplosion.getOpacity() - .05);

							// remove it if opacity done
							// if torpedo is not fully moved yet move it down
							if (ivExplosion.getOpacity() < 0) {

								root.getChildren().remove(ivExplosion); // remove ivtorp and move out of loop once done
								// reset animation boolean and remove transparent imageview
								hitAnimation = false;
								root.getChildren().remove(instructionsBack);

								// move the cpu and remove explosion

								if (playerTurn) {
									root.getChildren().remove(ivExplosion);
									doCPUMove();
								}

							}
						}

					} else if (!root.getChildren().contains(ivtorp) && missAnimation) { // animate miss explosion after
						// torpedo
						if (!missplosionAdded) {
							// place the torpedo in the x and y positions according to animation pos vars
							// and add to root
							ivMissplosion.setLayoutX(animationXpos - imgMissplosion.getWidth() / 2 + 25);
							ivMissplosion.setLayoutY(animationYpos - imgMissplosion.getHeight() / 2 + 25);
							root.getChildren().add(ivMissplosion);
							missplosionAdded = true;
						}

						// move the miss animation
						if (root.getChildren().contains(ivMissplosion)) {
							ivMissplosion.setRotate(ivMissplosion.getRotate() + 4);
							ivMissplosion.setOpacity(ivMissplosion.getOpacity() - .05);

							// remove it if opacity done
							// if torpedo is not fully moved yet move it down
							if (ivMissplosion.getOpacity() < 0) {

								root.getChildren().remove(ivMissplosion);
								// reset animation boolean and remove transparent imageview
								missAnimation = false;
								root.getChildren().remove(instructionsBack);

								// move the cpu and remove explosion

								if (playerTurn) {
									root.getChildren().remove(ivMissplosion);
									doCPUMove();
								}

							}
						}
					}
				}
			};

			// start animation timer
			timer.start();

			// setup the stage
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Battleship: Conquest and Conquer");

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		launch(args);
	}

	// method called when it is the CPU's turn to go
	public void doCPUMove() {
		// CPU only moves when the animation is over
		playerTurn = false;
		boolean doHit = false, doMiss = false;
		int temp = 0, temp2 = 0;

		// loops until we obtain the x and y values of a button that is valid (not
		// disabled yet)
		while (true) {
			// checks if the CPU ever hits ships
			if (CPUHitPos.size() != 0) {
				// make x and y coordinates as the same as the previous button that the ship
				// hits (which will be change later because we don't want the CPU to choose the
				// same button) so that the CPU will make smarter moves instead of random moves,
				// increasing its chance to win
				temp = CPUHitPos.get(CPUHitPos.size() - 2);
				temp2 = CPUHitPos.get(CPUHitPos.size() - 1);

				// makes the next button that the CPU selects either up, down, left, or right to
				// the previous button
				while (true) {
					if (dir == UP) {
						if (temp2 - 1 >= 0) {
							if (!p1shipPos[temp][temp2 - 1].isDisabled()) {
								temp2 = temp2 - 1;
								dir = UP;
								break;
							} else {
								dir = DOWN;
							}
						} else {
							dir = DOWN;
						}
					}
					if (dir == DOWN) {
						if (temp2 + 1 < 5) {
							if (!p1shipPos[temp][temp2 + 1].isDisabled()) {
								temp2 = temp2 + 1;
								dir = DOWN;
								break;
							} else {
								dir = LEFT;
							}
						} else {
							dir = LEFT;
						}
					}
					if (dir == LEFT) {
						if (temp - 1 >= 0) {
							if (!p1shipPos[temp - 1][temp2].isDisabled()) {
								temp = temp - 1;
								dir = LEFT;
								break;
							} else {
								dir = RIGHT;
							}
						} else {
							dir = RIGHT;
						}
					}
					if (dir == RIGHT) {
						if (temp + 1 < 5) {
							if (!p1shipPos[temp + 1][temp2].isDisabled()) {
								temp = temp + 1;
								dir = RIGHT;
								break;
							} else {
								dir = UP;
							}
						} else {
							dir = UP;
						}
					}
					// if those 4 buttons are not available (either disabled or do not exist),
					// select a random button
					temp = rnd.nextInt(5);
					temp2 = rnd.nextInt(5);
					dir = UP;
				}

			} else {
				temp = rnd.nextInt(5);
				temp2 = rnd.nextInt(5);
			}

			// checks if the button is already disabled or not
			if (!p1shipPos[temp][temp2].isDisabled()) {
				checkers.add(new HitAndMiss());
				checkerCount++;
				// if there is a ship at this button
				if (p1shipPos[temp][temp2].getAccessibleHelp() == ("chosen")) {
					// set the result to true and in the HitAndMiss class, it will change the image
					// of the checker accordingly
					checkers.get(checkerCount).setResult(true);
					CPUHitCount++;
					// add the x and y coordinates of the chosen button to a 2D array so that the
					// next move will be made based on this button
					CPUHitPos.add(temp);
					CPUHitPos.add(temp2);
					dirCount++;

					// dirCount is used to count if the CPU hits 2 button consecutively to determine
					// the direction of the ship. Once we know the direction, the next move will be
					// based on that direction (continue that direction), increasing its chance to
					// hit a ship
					if (dirCount > 1) {
						// if the x coordinate does not change, it can only be either UP or DOWN
						if (CPUHitPos.get(CPUHitPos.size() - 2) == CPUHitPos.get(CPUHitPos.size() - 4)) {
							// if the y coordinate is 1 unit larger, it is DOWN
							if (CPUHitPos.get(CPUHitPos.size() - 1) == CPUHitPos.get(CPUHitPos.size() - 3) + 1) {
								dir = DOWN;
							} else {
								dir = UP;
							}
							// if the y coordinate does not change, it can only be either be LEFT or RIGHT
						} else if (CPUHitPos.get(CPUHitPos.size() - 1) == CPUHitPos.get(CPUHitPos.size() - 3)) {
							if (CPUHitPos.get(CPUHitPos.size() - 2) == CPUHitPos.get(CPUHitPos.size() - 4) + 1) {
								dir = RIGHT;
							} else {
								dir = LEFT;
							}
						} else {
							dir = UP;
						}
					}

					// checks which ship the CPU hits so that we can count the total number of
					// squares of a ship is already destroyed
					if (p1TableInt[temp][temp2] == 0) {
						completelyDestroyCPU0++;
						doHit = true;
					} else if (p1TableInt[temp][temp2] == 1) {
						completelyDestroyCPU1++;
						doHit = true;
					} else if (p1TableInt[temp][temp2] == 2) {
						completelyDestroyCPU2++;
						doHit = true;
					}

					// checks if the cpu completely hits one ship so it can move again
					if (completelyDestroyCPU0 == 2) {
						completelyDestroyCPU0 = 0;
						doHit = true;
						playerTurn = true;

					}
					if (completelyDestroyCPU1 == 3) {
						completelyDestroyCPU1 = 0;
						doHit = true;
						playerTurn = true;

					}
					if (completelyDestroyCPU2 == 4) {
						completelyDestroyCPU2 = 0;
						doHit = true;
						playerTurn = true;

					}
				}
				// if the ship misses
				else {
					// set the result to false and in the HitAndMiss class, it will change the image
					// of the checker accordingly
					checkers.get(checkerCount).setResult(false);
					dirCount = 0;
					// update the x and y position of cpu button for hit/miss animations and start
					// the animation boolean
					doMiss = true;
				}
				// add the checker to the table (either hit or miss)
				checkers.get(checkerCount).setX((int) p1shipPos[temp][temp2].getLayoutX());
				checkers.get(checkerCount).setY((int) p1shipPos[temp][temp2].getLayoutY());
				root.getChildren().add(checkers.get(checkerCount).getImage());
				p1shipPos[temp][temp2].setDisable(true);
				break;
			}
		}

		// call hit and miss animations again based on set booleans
		if (doHit) {
			doHit = false;
			hitMissAnimation(p1shipPos[temp][temp2].getLayoutX(), p1shipPos[temp][temp2].getLayoutY(), true);

		} else if (doMiss) {
			doMiss = false;
			hitMissAnimation(p1shipPos[temp][temp2].getLayoutX(), p1shipPos[temp][temp2].getLayoutY(), false);

		}

	}

	// this method controls the hit and miss animations
	public void hitMissAnimation(double x, double y, boolean hitMiss) {

		// animation for if a ship is hit successfully
		if (hitMiss) {

			// stop user from pressing buttons again with mouse during cutscene by
			// overlaying transparent imageview
			instructionsBack = new ImageView(new Image("file:instructionsBack.jpg"));
			instructionsBack.setFitHeight(height);
			instructionsBack.setFitWidth(width);
			instructionsBack.setOpacity(0);
			root.getChildren().add(instructionsBack);

			// init the imageviews for the hit and miss animations
			// create all possible image views
			ivtorp = new ImageView(new Image("file:torpedo.png"));
			ivtorp.setOpacity(1);
			imgExplosion = new Image("file:explosion.png");
			ivExplosion = new ImageView(imgExplosion);
			ivExplosion.setOpacity(1);

			// place the torpedo in the x and y positions according to animation pos vars
			// and add to root
			ivtorp.setLayoutX(x);
			ivtorp.setLayoutY(0);
			root.getChildren().add(ivtorp);

			animationXpos = x;
			animationYpos = y;

			explosionAdded = false;
			// store that hit animation is ongiong
			hitAnimation = true;

		}

		// animation for if a ship is hit unsuccessfully
		else if (!hitMiss) {

			// stop user from pressing buttons again with mouse during cutscene by
			// overlaying transparent imageview
			instructionsBack = new ImageView(new Image("file:instructionsBack.jpg"));
			instructionsBack.setFitHeight(height);
			instructionsBack.setFitWidth(width);
			instructionsBack.setOpacity(0);
			root.getChildren().add(instructionsBack);

			// init the imageviews for the hit and miss animations
			// create all possible image views
			ivtorp = new ImageView(new Image("file:torpedo.png"));
			ivtorp.setOpacity(1);
			imgMissplosion = new Image("file:splashmiss.jpg");
			ivMissplosion = new ImageView(imgMissplosion);
			ivMissplosion.setOpacity(1);

			// place the torpedo in the x and y positions according to animation pos vars
			// and add to root
			ivtorp.setLayoutX(x);
			ivtorp.setLayoutY(0);
			root.getChildren().add(ivtorp);

			animationYpos = y;
			animationXpos = x;

			missplosionAdded = false;
			// store that miss animation is ongiong
			missAnimation = true;

		}
	}

	// method for instructions
	public void instructionClicked() {
		// remove all nodes
		root.getChildren().removeAll(root.getChildren());

		// setup a new background for instructions
		ImageView instructionsBack = new ImageView(new Image("file:instructionsBack.jpg"));
		instructionsBack.setFitHeight(height);
		instructionsBack.setFitWidth(width);
		instructionsBack.setOpacity(20);
		root.getChildren().add(instructionsBack);

		// setup alerts
		instructionsAlert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
		instructionsAlert.setHeaderText(null);
		instructionsAlert.setTitle("Battleship: Conquest and Conquer - INSTRUCTIONS");
		instructionsAlert.setContentText(
				"Welcome to Battleship! This twist on the original game involves many of the facets from the original but takes a twist of its own (que the bird flying animation). \r\n"
						+ "\r\n"
						+ "First, you will start by placing your fleet of ships; in this case there are 3 ships: a square 4 piece, a straight 3 piece, and a straight 2 piece. You may rotate them with the L/R arrow keys and press SPACE to set them down and place the next ship. Press the READY button to advance.\r\n"
						+ "\r\n"
						+ "Next, we are introduced to the main gameplay. Here you are pit against the enemy CPU, which places its own ships randomly on a congruent game board. You must click a square to try and hit the enemy ships. Once you hit an enemy ship-hit or miss-it is their turn and they hit your ship as well. Fully destroying an enemy ship allows you a free extra turn, this is the same for the enemy CPU. \r\n"
						+ "\r\n"
						+ "This continues until all the ships have been hit. The first player to hit all the other player�s ships wins the game.\r\n"
						+ "Winning the game gives you a score that�s recorded with your name and time. \r\n" + "\r\n"
						+ "Good luck and fair seas!\r\n" + ""); // sequential instructions in google docs
		instructionsAlert.setGraphic(new ImageView(new Image("file:captainStanding.png")));

		// show alert and revert back to main
		instructionsAlert.showAndWait();
		root.getChildren().removeAll(root.getChildren());
		root.getChildren().addAll(iviewMenuBackground, buttons, title);
	}

	// once the Play button is clicked
	public void playClicked() {

		// change boolean value for keyframe background animation
		onMenu = false;

		// remove all nodes except first
		root.getChildren().remove(1, root.getChildren().size() - 1);
		root.getChildren().remove(iviewTitle);

		// make alert to enter name
		TextInputDialog whatName = new TextInputDialog();
		whatName.setTitle("Enter name...");
		whatName.setHeaderText(null);
		whatName.setContentText("What is your name?");

		// check if the player enters anything or he/she leaves it empty
		playerName = "";
		while (playerName.equals("")) {
			Optional<String> result = whatName.showAndWait();
			if (result.isPresent()) {

				playerName = (result.get().trim());

			}
		}

		// make button for confirm ship placement and add to root
		placeShipButton = new Button();
		placeShipButton.setText("SET SHIPS READY!");
		placeShipButton.setPrefSize(200, 80);
		placeShipButton.setFont(Font.font(20));
		placeShipButton.setOnAction(e -> actualGame());
		placeShipButton.setLayoutX(400);
		placeShipButton.setLayoutY(500);
		placeShipButton.setDisable(true);

		// make label to indicate user what they're doing address them by name, add to
		// root
		setShipsLabel = new Label();
		setShipsLabel.setText("Place your ships, Captain " + playerName + "!");
		setShipsLabel.setPrefSize(950, 100);
		setShipsLabel.setTextFill(Color.RED);
		setShipsLabel.setAlignment(Pos.CENTER);
		setShipsLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
		setShipsLabel.setLayoutX(width / 2 - setShipsLabel.getPrefWidth() / 2);
		setShipsLabel.setLayoutY(0);
		root.getChildren().addAll(setShipsLabel);

		// tilepane layout for all buttons for main board game
		p1TilePane = new TilePane();
		p1TilePane.setPrefColumns(5);
		p1TilePane.setPrefRows(5);
		p1TilePane.setOrientation(Orientation.HORIZONTAL);
		p1TilePane.setLayoutX(width / 2 - (50 * 5) / 2);
		p1TilePane.setLayoutY(height / 2 - (50 * 5) / 2);

		// fill 2d array with buttons
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				root.getChildren().addAll(p1shipPos[i][j]);
			}
		}
		root.getChildren().add(placeShipButton);

		// setup all ships imageview and add to an array
		ImageView ship2S = new ImageView(new Image("file:2StraightShipBlue.PNG"));
		ImageView ship3S = new ImageView(new Image("file:3StraightShipBlue.PNG"));
		ImageView ship3L = new ImageView(new Image("file:3LshipBlue0.PNG"));

		// add all imageview to array
		shipsIVarray = new ImageView[3];
		shipsIVarray[0] = ship2S;
		shipsIVarray[1] = ship3S;
		shipsIVarray[2] = ship3L;

		// second set of ships for the CPU
		ImageView ship2S2 = new ImageView(new Image("file:2StraightShipBlue.PNG"));
		ImageView ship3S2 = new ImageView(new Image("file:3StraightShipBlue.PNG"));
		ImageView ship3L2 = new ImageView(new Image("file:3LshipBlue0.PNG"));

		shipsIVarray2 = new ImageView[3];
		shipsIVarray2[0] = ship2S2;
		shipsIVarray2[1] = ship3S2;
		shipsIVarray2[2] = ship3L2;

		// add gameboard tilepane
		root.getChildren().addAll(p1TilePane);

		// add all imageview array elements to root
		root.getChildren().addAll(shipsIVarray[shipNum]);

		// remove the title
		root.getChildren().remove(title);
		// enable boolean for setting ships phase
		setShips = true;

	}

	// when the actual game starts
	public void actualGame() {

		// start the counter for actual game
		secondsTimer.play();

		// remove specific nodes
		root.getChildren().removeAll(setShipsLabel, placeShipButton, title);

		// move the gameboard

		checkers = new ArrayList<HitAndMiss>();

		// these variables are used to count to check if a certain type of ship is
		// completely destroyed
		// for example: completelyDestroy0 is for the first ship and it has 2 squares
		completelyDestroy0 = 0;
		completelyDestroy1 = 0;
		completelyDestroy2 = 0;

		completelyDestroyCPU0 = 0;
		completelyDestroyCPU1 = 0;
		completelyDestroyCPU2 = 0;

		arrayHoldCPUPos = new int[6];

		// randomize the location of the first ship (either horizontal or vertical)
		if (rnd.nextInt(2) == 0) {
			int temp = rnd.nextInt(4);
			int temp2nd = rnd.nextInt(5);
			CPUshipPos[temp][temp2nd].setAccessibleHelp("chosen");
			CPUshipPos[temp + 1][temp2nd].setAccessibleHelp("chosen");
			CPUTableInt[temp][temp2nd] = 0;
			CPUTableInt[temp + 1][temp2nd] = 0;
			arrayHoldCPUPos[0] = temp;
			arrayHoldCPUPos[1] = temp2nd;
			ship0Dir = 0;
			shipsIVarray2[0].setRotate(90);
		} else {
			int temp = rnd.nextInt(5);
			int temp2nd = rnd.nextInt(4);
			CPUshipPos[temp][temp2nd].setAccessibleHelp("chosen");
			CPUshipPos[temp][temp2nd + 1].setAccessibleHelp("chosen");
			CPUTableInt[temp][temp2nd] = 0;
			CPUTableInt[temp][temp2nd + 1] = 0;
			arrayHoldCPUPos[0] = temp;
			arrayHoldCPUPos[1] = temp2nd;
			ship0Dir = 1;
		}

		// randomize the location of the second ship (either horizontal or vertical)
		if (rnd.nextInt(2) == 0) {
			ship1Dir = 0;
			shipsIVarray2[1].setRotate(90);
			while (true) {
				int temp1 = rnd.nextInt(3);
				int temp2 = rnd.nextInt(5);
				if (CPUshipPos[temp1][temp2].getAccessibleHelp() != "chosen"
						&& CPUshipPos[temp1 + 1][temp2].getAccessibleHelp() != "chosen"
						&& CPUshipPos[temp1 + 2][temp2].getAccessibleHelp() != "chosen") {
					CPUshipPos[temp1][temp2].setAccessibleHelp("chosen");
					CPUshipPos[temp1 + 1][temp2].setAccessibleHelp("chosen");
					CPUshipPos[temp1 + 2][temp2].setAccessibleHelp("chosen");

					CPUTableInt[temp1][temp2] = 1;
					CPUTableInt[temp1 + 1][temp2] = 1;
					CPUTableInt[temp1 + 2][temp2] = 1;
					arrayHoldCPUPos[2] = temp1;
					arrayHoldCPUPos[3] = temp2;
					break;
				}
			}
		} else {
			ship1Dir = 1;
			while (true) {
				int temp1 = rnd.nextInt(5);
				int temp2 = rnd.nextInt(3);
				if (CPUshipPos[temp1][temp2].getAccessibleHelp() != "chosen"
						&& CPUshipPos[temp1][temp2 + 1].getAccessibleHelp() != "chosen"
						&& CPUshipPos[temp1][temp2 + 2].getAccessibleHelp() != "chosen") {
					CPUshipPos[temp1][temp2].setAccessibleHelp("chosen");
					CPUshipPos[temp1][temp2 + 1].setAccessibleHelp("chosen");
					CPUshipPos[temp1][temp2 + 2].setAccessibleHelp("chosen");

					CPUTableInt[temp1][temp2] = 1;
					CPUTableInt[temp1][temp2 + 1] = 1;
					CPUTableInt[temp1][temp2 + 2] = 1;
					arrayHoldCPUPos[2] = temp1;
					arrayHoldCPUPos[3] = temp2;
					break;
				}
			}
		}
		// randomize the location of the third ship
		while (true) {
			int temp1 = rnd.nextInt(4);
			int temp2 = rnd.nextInt(4);
			if (CPUshipPos[temp1][temp2].getAccessibleHelp() != "chosen"
					&& CPUshipPos[temp1][temp2 + 1].getAccessibleHelp() != "chosen"
					&& CPUshipPos[temp1 + 1][temp2].getAccessibleHelp() != "chosen"
					&& CPUshipPos[temp1 + 1][temp2 + 1].getAccessibleHelp() != "chosen") {
				CPUshipPos[temp1][temp2].setAccessibleHelp("chosen");
				CPUshipPos[temp1 + 1][temp2].setAccessibleHelp("chosen");
				CPUshipPos[temp1][temp2 + 1].setAccessibleHelp("chosen");
				CPUshipPos[temp1 + 1][temp2 + 1].setAccessibleHelp("chosen");

				CPUTableInt[temp1][temp2] = 2;
				CPUTableInt[temp1 + 1][temp2] = 2;
				CPUTableInt[temp1 + 1][temp2 + 1] = 2;
				CPUTableInt[temp1][temp2 + 1] = 2;
				arrayHoldCPUPos[4] = temp1;
				arrayHoldCPUPos[5] = temp2;
				break;
			}
		}

		// move player table to the right and creating the CPU ship buttons
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int tempi = i;
				int tempj = j;
				CPUshipPos[i][j].setPrefSize(60, 60);
				CPUshipPos[i][j].setFocusTraversable(false);

				CPUshipPos[i][j].setLayoutX(250 + i * 60);
				CPUshipPos[i][j].setLayoutY(100 + j * 60);

				p1shipPos[i][j].setPrefSize(60, 60);
				p1shipPos[i][j].setFocusTraversable(false);
				p1shipPos[i][j].setBorder(null);
				p1shipPos[i][j].setLayoutX(650 + i * 60);
				p1shipPos[i][j].setLayoutY(100 + j * 60);
				root.getChildren().addAll(CPUshipPos[i][j]);
				CPUshipPos[i][j].setOnAction(e -> buttonClicked(tempi, tempj));
			}
		}

		// move ship images over players ship button grid
		for (int i = 0; i < 3; i++) {
			shipsIVarray[i].setX(shipsIVarray[i].getX() + 300);
			shipsIVarray[i].setY(shipsIVarray[i].getY() - 50);
		}

		// Label to indicate that these buttons are the enemy ships
		Label lblCPUTable = new Label();
		lblCPUTable.setText("ENEMY SHIPS");
		lblCPUTable.setPrefSize(200, 80);
		lblCPUTable.setTextFill(Color.RED);
		lblCPUTable.setFont(Font.font("System", FontWeight.BOLD, 20));
		lblCPUTable.setLayoutX(250 + 200 - lblCPUTable.getPrefWidth() / 2);
		lblCPUTable.setLayoutY(30);

		// Label to indicate that these buttons are the player's ship
		Label lblP1Table = new Label();
		lblP1Table.setText("YOUR SHIPS");
		lblP1Table.setPrefSize(200, 80);
		lblP1Table.setTextFill(Color.RED);
		lblP1Table.setFont(Font.font("System", FontWeight.BOLD, 20));
		lblP1Table.setLayoutX(650 + 200 - lblP1Table.getPrefWidth() / 2);
		lblP1Table.setLayoutY(30);

		// add all components to the roots
		root.getChildren().addAll(lblCPUTable, lblP1Table);
	}

	// method called to display the leaderboard / highscore
	public void highscoreClicked() {

		// remove unrequired nodes
		root.getChildren().removeAll(buttons, title);

		// read the score file
		highscore.readScore();

		// change title name and move it center
		title.setText("PAST PLAYER SCORES");
		title.setPrefSize(950, 50);
		title.setAlignment(Pos.CENTER);
		title.setLayoutX(width / 2 - title.getPrefWidth() / 2);
		title.setLayoutY(0);

		// back button
		Button goBack = new Button("BACK");
		goBack.setPrefHeight(50);
		goBack.setFont(Font.font("Comic Sans MS", 20));
		goBack.setOnAction(e -> menuStart());

		// search button
		Button search = new Button("SEARCH");
		search.setPrefHeight(50);
		search.setFont(Font.font("Comic Sans MS", 20));
		search.setOnAction(new EventHandler<ActionEvent>() {

			private String searchKey = "";
			private int score = 0;

			public void handle(ActionEvent e) {

				// create textinput to search for player name until string is entered
				TextInputDialog searchInput = new TextInputDialog();
				searchInput.setTitle("Search for a player score...");
				searchInput.setHeaderText(null);
				searchInput.setContentText("What is the player name you want to search for?");

				Optional<String> result = searchInput.showAndWait();
				if (result.isPresent()) {
					searchKey = result.get().trim();

				} else {
					e.consume();
				}

				// call highscores class method and get score of relevant player
				score = highscore.searchScores(searchKey);
				if (score == 0) {

					// create alert for no player found
					Alert notfound = new Alert(AlertType.ERROR, "Player name was not found!", ButtonType.OK);
					notfound.setHeaderText(null);
					notfound.setTitle("Player lookup error...");
					notfound.showAndWait();
					searchKey = "";

				} else if (score > 0) {

					// convert score in seconds to minutes (MM/SS)
					int minutes = score / 60;
					int seconds = score % 60;

					// create alert for player found and their score
					Alert found = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
					found.setHeaderText(null);
					found.setTitle("Player was found!");
					found.setContentText("Player was found! Their time was " + minutes + " minute(s) and " + seconds
							+ " second(s).");
					found.showAndWait();
					searchKey = "";
				}

			}
		});

		// sort button: high to low
		Button sortHigh = new Button("SORT HIGHEST");
		sortHigh.setPrefHeight(50);
		sortHigh.setFont(Font.font("Comic Sans MS", 20));
		sortHigh.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				highscore.sortScores(true); // true sorts high to low
				highscoreClicked();
			}
		});

		// sort button: low to high
		Button sortLow = new Button("SORT LOWEST");
		sortLow.setFont(Font.font("Comic Sans MS", 20));
		sortLow.setPrefHeight(50);
		sortLow.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				highscore.sortScores(false); // false sorts low to high
				highscoreClicked();
			}
		});

		// clear button
		Button clrBtn = new Button("CLEAR SCORES");
		clrBtn.setFont(Font.font("Comic Sans MS", 20));
		clrBtn.setPrefHeight(50);
		clrBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				try {
					// create alert sure you want to delete all scores
					Alert confirmClr = new Alert(AlertType.WARNING,
							"Are you sure you want to clear scores? Continuing will permanently delete data!",
							ButtonType.YES, ButtonType.NO);
					confirmClr.setHeaderText(null);
					confirmClr.setTitle("Delete scores...");
					Optional<ButtonType> result = confirmClr.showAndWait();

					if (result.get() == ButtonType.YES) {
						// clear highscores files and clear the textarea
						highscore.clearScores();
						scorelogs.clear();
					} else {
						e.consume(); // destroy the actionevent
					}

				} catch (Exception error) {
					System.out.println("error on clearing scores, file exceptions.");
				}

			}
		});

		// create textarea for scrollable scores and all text to the textarea
		scorelogs = new TextArea();
		scorelogs.setEditable(false);
		scorelogs.setWrapText(true);
		// scorelogs.setLayoutX(title);
		scorelogs.setLayoutY(title.getLayoutY() + 50);
		scorelogs.setPrefWidth(scene.getWidth() - 200);
		scorelogs.setPrefHeight(250);
		Font scoresFont = new Font("Papyrus", 21);
		scorelogs.setFont(scoresFont);
		scorelogs.setOpacity(1);

		// access all elements of name and score arraylists and write them in the text
		// area
		for (int a = 0; a < highscore.namescores.size(); a++) {
			scorelogs.appendText("NAME: " + highscore.namescores.get(a) + " , TIME: " + highscore.scores.get(a) / 60
					+ " minute(s) and " + highscore.scores.get(a) % 60 + " second(s).\n"); // append the name and time
			// in seconds for score
		}

		// add buttons to a flowpane layout
		FlowPane fpButtons = new FlowPane(Orientation.HORIZONTAL, 15, 15);
		fpButtons.setAlignment(Pos.CENTER);
		fpButtons.setPrefWidth(600);
		fpButtons.getChildren().addAll(goBack, search, sortHigh, sortLow, clrBtn);

		// add textarea and flowpane layout to the gridpane and add gridpane to root and
		// the title
		GridPane gpHighscores = new GridPane();
		gpHighscores.setVgap(20);
		gpHighscores.setAlignment(Pos.CENTER);
		gpHighscores.setLayoutX(width / 2 - fpButtons.getPrefWidth() / 2 - 90);
		gpHighscores.setLayoutY(50);
		gpHighscores.add(scorelogs, 0, 0);
		gpHighscores.add(fpButtons, 0, 1);
		GridPane.setHalignment(fpButtons, HPos.CENTER);
		gpHighscores.setGridLinesVisible(false);

		root.getChildren().addAll(gpHighscores, title);

	}

	// when the user clicks the exit button
	public void exitClicked() {
		// create the two possible alerts, and create graphic for alerts
		Alert exit = new Alert(AlertType.CONFIRMATION,
				"Are you sure you want to abandon ship? Score will not be saved!", ButtonType.YES, ButtonType.NO);
		Alert bye = new Alert(AlertType.INFORMATION, "Thank you for playing!", ButtonType.OK);
		ImageView exitGraphic = new ImageView(new Image("file:gameExit.png"));

		// setup two alerts settings
		exit.setTitle("Exit Game...");
		exit.setHeaderText(null);
		exit.setGraphic(exitGraphic);

		bye.setHeaderText(null);
		bye.setTitle("Goodbye...");

		// store the result of the alert button type
		Optional<ButtonType> result = exit.showAndWait();

		// remove alert if no, show bye alert and exit if yes
		if (result.get() == ButtonType.YES) {
			bye.setGraphic(exitGraphic);
			bye.showAndWait();
			System.exit(0);
		} else {
			// do nothing
		}
	}

	// method runs when the menu starts
	public void menuStart() {

		// remove everything first
		root.getChildren().removeAll(root.getChildren());

		// reset all variables
		resetVariables();

		// setup title label
		title = new Label();
		title.setText("BATTLESHIP");
		title.setTextFill(Color.BLUE);
		title.setFont(Font.font("System", FontWeight.BOLD, 36));
		title.setLayoutX(scene.getWidth() / 2 - 100);
		title.setLayoutY(10);

		// Image for the title
		imgTitle = new Image("file:title.png");
		iviewTitle = new ImageView(imgTitle);
		iviewTitle.setLayoutX(width / 2 - imgTitle.getWidth() / 2);
		iviewTitle.setLayoutY(10);

		// layout vbox for menu buttons
		buttons = new HBox();
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(50);
		buttons.setLayoutX(50);
		buttons.setLayoutY(300);

		// button for the instructions
		Button instruction = new Button();
		instruction.setText("INSTRUCTION");
		instruction.setPrefSize(190, 70);
		instruction.setFont(Font.font("Comic Sans MS", 20));
		instruction.setOnAction(e -> instructionClicked());

		// button for the highscore
		Button highScore = new Button();
		highScore.setText("PLAYER SCORES");
		highScore.setPrefSize(190, 70);
		highScore.setFont(Font.font("Comic Sans MS", 20));
		highScore.setOnAction(e -> highscoreClicked());

		Button exit = new Button();
		exit.setText("EXIT");
		exit.setPrefSize(190, 70);
		exit.setFont(Font.font("Comic Sans MS", 20));
		exit.setOnAction(e -> exitClicked());

		Button play = new Button();
		play.setText("PLAY");
		play.setPrefSize(190, 70);
		play.setFont(Font.font("Comic Sans MS", 20));
		play.setOnAction(e -> playClicked());

		// initialize the 2D array buttons for the CPU and the player
		CPUshipPos = new Button[5][5];
		p1shipPos = new Button[5][5];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				CPUshipPos[i][j] = new Button();
				p1shipPos[i][j] = new Button();

				int tempi = i;
				int tempj = j;
				p1shipPos[i][j] = new Button();
				p1shipPos[i][j].setPrefSize(60, 60);
				p1shipPos[i][j].setFocusTraversable(false);

				p1shipPos[i][j].setLayoutX(width / 2 - 150 + i * 60);
				p1shipPos[i][j].setLayoutY(height / 2 - 150 + j * 60);

				p1shipPos[i][j].setOnAction(e -> buttonClicked(tempi, tempj));
				p1shipPos[i][j].setMouseTransparent(true);
			}
		}

		// add the buttons to the vbox layout manager
		buttons.getChildren().addAll(instruction, play, highScore, exit);

		// get the children and show the stage/scene for menu
		root.getChildren().addAll(iviewMenuBackground, buttons, iviewTitle);

	}

	// method called when the user chooses the button to hit the CPU's ships
	public void buttonClicked(int x, int y) {

		// player hits the button and calls animation, torps are added
		checkers.add(new HitAndMiss());
		checkerCount++;
		// if the playe hits the CPU's ships
		if (CPUshipPos[x][y].getAccessibleHelp() == "chosen") {
			checkers.get(checkerCount).setResult(true);
			p1HitCount++;

			// update the x and y position of selected button for hit/miss animations and
			// start the animation boolean
			playerTurn = true;
			hitMissAnimation(CPUshipPos[x][y].getLayoutX(), CPUshipPos[x][y].getLayoutY(), true);

			checkers.get(checkerCount).setX((int) CPUshipPos[x][y].getLayoutX());
			checkers.get(checkerCount).setY((int) CPUshipPos[x][y].getLayoutY());
			root.getChildren().add(checkers.get(checkerCount).getImage());
			CPUshipPos[x][y].setDisable(true);

			// increase the variables if the player hits the ships accordingly. For example:
			// completelyDestroy0 is for the first 2 square ship. So when it is equal to 2,
			// we know that the first ship is completely destroyed
			if (CPUTableInt[x][y] == 0) {
				completelyDestroy0++;
			} else if (CPUTableInt[x][y] == 1) {
				completelyDestroy1++;
			} else if (CPUTableInt[x][y] == 2) {
				completelyDestroy2++;
			}

			// check if the 1st ship is completely destroyed
			if (completelyDestroy0 == 2) {
				completelyDestroy0 = 0;
				// checks the direction of the ships and draw the image accordingly
				if (ship0Dir == 0) {
					shipsIVarray2[0].setLayoutX(CPUshipPos[arrayHoldCPUPos[0]][arrayHoldCPUPos[1]].getLayoutX() + 50);
					shipsIVarray2[0].setLayoutY(CPUshipPos[arrayHoldCPUPos[0]][arrayHoldCPUPos[1]].getLayoutY() - 20);
				} else {
					shipsIVarray2[0].setLayoutX(CPUshipPos[arrayHoldCPUPos[0]][arrayHoldCPUPos[1]].getLayoutX() + 15);
					shipsIVarray2[0].setLayoutY(CPUshipPos[arrayHoldCPUPos[0]][arrayHoldCPUPos[1]].getLayoutY() + 10);
				}
				playerTurn = false;
				root.getChildren().add(shipsIVarray2[0]);
			}
			// check if the 2nd ship is completely destroyed
			if (completelyDestroy1 == 3) {
				completelyDestroy1 = 0;
				// checks the direction of the ships and draw the image accordingly
				if (ship1Dir == 0) {
					shipsIVarray2[1].setLayoutX(CPUshipPos[arrayHoldCPUPos[2]][arrayHoldCPUPos[3]].getLayoutX() + 65);
					shipsIVarray2[1].setLayoutY(CPUshipPos[arrayHoldCPUPos[2]][arrayHoldCPUPos[3]].getLayoutY() - 45);
				} else {
					shipsIVarray2[1].setLayoutX(CPUshipPos[arrayHoldCPUPos[2]][arrayHoldCPUPos[3]].getLayoutX() + 10);
					shipsIVarray2[1].setLayoutY(CPUshipPos[arrayHoldCPUPos[2]][arrayHoldCPUPos[3]].getLayoutY() + 10);
				}
				playerTurn = false;
				root.getChildren().add(shipsIVarray2[1]);
			}
			// check if the 3rd ship is completely destroyed
			if (completelyDestroy2 == 4) {
				completelyDestroy2 = 0;
				shipsIVarray2[2].setLayoutX(CPUshipPos[arrayHoldCPUPos[4]][arrayHoldCPUPos[5]].getLayoutX() + 10);
				shipsIVarray2[2].setLayoutY(CPUshipPos[arrayHoldCPUPos[4]][arrayHoldCPUPos[5]].getLayoutY() + 20);
				playerTurn = false;
				root.getChildren().add(shipsIVarray2[2]);
			}

			// else if the player misses
		} else {
			// draws the checker
			checkers.get(checkerCount).setResult(false);
			playerTurn = true;
			hitMissAnimation(CPUshipPos[x][y].getLayoutX(), CPUshipPos[x][y].getLayoutY(), false);
			checkers.get(checkerCount).setX((int) CPUshipPos[x][y].getLayoutX());
			checkers.get(checkerCount).setY((int) CPUshipPos[x][y].getLayoutY());
			root.getChildren().add(checkers.get(checkerCount).getImage());
			CPUshipPos[x][y].setDisable(true);
		}
	}

	// return the playername and their score at end game
	public String getPName() {
		System.out.println("main name:" + playerName);
		return playerName;
	}

	public int getScoretime() {
		System.out.println("main score:" + scoreSeconds);
		return scoreSeconds;
	}

}
