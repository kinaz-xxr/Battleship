package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HitAndMiss {
	ImageView output;
	Image hit, miss;
	
	public HitAndMiss() {
		hit = new Image("file:shipHit.PNG");
		miss = new Image("file:shipMiss.PNG");
		output = new ImageView(miss);
	}
	public void setResult (boolean boolHit) {
		if (boolHit== true) {
			output = new ImageView(hit);
		}
		else
		{
			output = new ImageView(miss);
		}
	}
	public void setX(int x) {
		output.setLayoutX(x);
	}
	public void setY(int y) {
		output.setLayoutY(y);
	}
	public ImageView getImage() {
		return output;
	}
}
