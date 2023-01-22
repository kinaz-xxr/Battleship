package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//this class is used to display the checkers
public class HitAndMiss {
	ImageView output;
	Image hit, miss;

	// constructor to initialize the pictures
	public HitAndMiss() {
		hit = new Image("file:shipHit.PNG");
		miss = new Image("file:shipMiss.PNG");
		output = new ImageView(miss);
	}

	// setResult method will receive the boolean from main to check if the player or
	// the CPU hits or miss and change the image accordingly
	public void setResult(boolean boolHit) {
		if (boolHit == true) {
			output = new ImageView(hit);
		} else {
			output = new ImageView(miss);
		}
	}

	//pass in the x coordinate of the checkers
	public void setX(int x) {
		output.setLayoutX(x);
	}

	//pass in the y coordinate of the checkers
	public void setY(int y) {
		output.setLayoutY(y);
	}

	//return the checker image
	public ImageView getImage() {
		return output;
	}
}
