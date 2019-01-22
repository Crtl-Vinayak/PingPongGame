package dev.VinvinBleidorb.Main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class pingpong extends Application{
	final public String GameName = "Ping Pong!";
	public int WIDTH = 1080;
	public int HEIGHT = 720;
	public boolean isRunning;
	private AnimationTimer timer;
	
	public int Player_yPos = 10;
	public int Enemy_yPos = 10;
	
	public int BallxPos = 540;
	public int BallyPos = 360;
	public int Radius = 30;
	
	public double speedBallX = 3;
	public double speedBallY = 3;
	
	public int x1FrontP = 120;
	public int x2FrontP = 120;
	public int y1FrontP = Player_yPos;
	public int y2FrontP = Player_yPos + 100;
	
	public int x1FrontE = 960;
	public int x2FrontE = 960;
	public int y1FrontE = Enemy_yPos;
	public int y2FrontE = Enemy_yPos + 100;
	
	public int RightScore = 0;
	public int LeftScore = 0;
	
	private double vYplayer = 10;
	private double vYenemy = 10;
	
    public static void main(String [] args) {
        launch(args);
    }

	@Override
	public void start(Stage PP_stage) throws Exception {
		PP_stage.setTitle(GameName);
		//Image Icon = new Image(getClass().getResourceAsStream("res/PongIconBleb.png"));
		//PP_stage.getIcons().add(Icon);
		
		//pingpong scene
		Group root = new Group();
		Scene PP_scene = new Scene(root);
	    PP_stage.setScene(PP_scene);
	    
	    //main menu scene
	    //Group root2 = new Group();
	    //Scene MM = new Scene(root2);
	    //PP_stage.setScene(MM);
	    
	    isRunning = true;
	    
		Canvas canvas = new Canvas(1080, 720);
		root.getChildren().add(canvas);
		
		Text textScoreR = new Text(600, 100, "" + RightScore);
		textScoreR.setFill(Color.RED);
		textScoreR.setFont(Font.font(100));
		root.getChildren().add(textScoreR);
		
		Text textScoreL = new Text(400, 100, "" + LeftScore);
		textScoreL.setFill(Color.CYAN);
		textScoreL.setFont(Font.font(100));
		root.getChildren().add(textScoreL);
		
		Text BeginText = new Text();
		BeginText.setFill(Color.CORAL);
		
		//pause screen, for when you press escape.
		Text resume = new Text(540, 200, "resume (escape)");
		resume.setFill(Color.ANTIQUEWHITE);
		resume.setFont(Font.font(30));
		
		Text quitgame = new Text(540, 300, "quitgame (q)");
		quitgame.setFill(Color.ANTIQUEWHITE);
		quitgame.setFont(Font.font(30));
		
		PP_scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode() == KeyCode.S) {
				Player_yPos += vYplayer;
				if(Player_yPos == HEIGHT - 90) {
					Player_yPos -= vYplayer;
				}
			}
			if(key.getCode() == KeyCode.W) {
				Player_yPos -= vYplayer;
				if(Player_yPos == -vYplayer) {
					Player_yPos *= 0;
				}
			}
			if(key.getCode() == KeyCode.K) {
				Enemy_yPos += vYenemy;
				if(Enemy_yPos == HEIGHT - 90) {
					Enemy_yPos -= vYenemy;
				}
			}
			if(key.getCode() == KeyCode.I) {
				Enemy_yPos -= vYenemy;
				if(Enemy_yPos == -vYenemy) {
					Enemy_yPos *= 0;
				}
			}
			if(key.getCode() == KeyCode.Q) {
				System.exit(0);
			}
		});
		PP_scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
				if((key.getCode() == KeyCode.ESCAPE)) {
					if(isRunning) {
						isRunning = (isRunning == false);
						timer.stop();
						System.out.println("stop");
						root.getChildren().add(resume);
						root.getChildren().add(quitgame);
					}else{
						isRunning = (isRunning == false);
						timer.start();
						System.out.println("start");
						root.getChildren().remove(resume);
						root.getChildren().remove(quitgame);
					}
				}
		});
		PP_scene.addEventHandler(KeyEvent.KEY_TYPED, (key) -> {});
		
		GraphicsContext gc = canvas.getGraphicsContext2D();

				timer = new AnimationTimer() {
					
					//long last_time = System.nanoTime();
					//final double ticks = 60D;
					//double nanoSeconds = 1_000_000_000 / ticks;
					//double delta = 0;
					
					@Override
					public void handle(long arg) {
						//long now = System.nanoTime();
						//delta += (now -last_time) / nanoSeconds;
						//last_time = now;
						
						draw(gc);
						
						BallxPos += speedBallX;
						BallyPos += speedBallY;
						
						if(BallxPos + Radius >= WIDTH) {
							BallxPos = 540;
							BallyPos = 360;
							speedBallX *= -1;
							speedBallY *= -1;
							RightScore++;
							textScoreL.setText("" + RightScore);
						}
						if(BallxPos <= 0) {
							BallxPos = 540;
							BallyPos = 360;
							speedBallX *= -1;
							LeftScore++;
							textScoreR.setText("" + LeftScore);
						}
						if(BallyPos + Radius >= HEIGHT) {
							speedBallY *= -1;
						}
						if(BallyPos <= 0) {
							speedBallY *= -1;
						}
						
						//paddle collision detection with the ball
						//player & enemy front side, collision detection.
						if((y1FrontP < BallyPos && y2FrontP > BallyPos) && (x1FrontP == BallxPos && x2FrontP == BallxPos)) {
							speedBallX *= -1;
						}
						if((y1FrontE < BallyPos && y2FrontE > BallyPos) && (x1FrontE == BallxPos + 30 && x2FrontE == BallxPos + 30)) {
							speedBallX *= -1;
						}
						y1FrontP = Player_yPos;
						y2FrontP = Player_yPos + 100;
						y1FrontE = Enemy_yPos;
						y2FrontE = Enemy_yPos + 100;
					}
				}; 
				
				timer.start();
				PP_stage.show();
				
	}
	public void draw(GraphicsContext gc) {
		this.clear(gc);
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		gc.setFill(Color.CYAN);
        gc.fillRect(100, Player_yPos, 20, 100);
        gc.setFill(Color.WHITE);
        gc.fillOval(BallxPos, BallyPos, Radius, Radius);
        gc.setFill(Color.RED);
        gc.fillRect(960, Enemy_yPos, 20, 100);
        gc.setStroke(Color.GOLD);
        gc.strokeLine(x1FrontE, y1FrontE, x2FrontE, y2FrontE);
       
        gc.setStroke(Color.TRANSPARENT);
        gc.strokeArc(BallxPos, BallyPos, 30, 30, 90, -90, ArcType.OPEN);
        gc.strokeArc(BallxPos, BallyPos, 30, 30, 90, -270, ArcType.OPEN);
	}
	public void clear(GraphicsContext gc) {
		gc.fillRect(0, 0, WIDTH, HEIGHT);
	}
}
