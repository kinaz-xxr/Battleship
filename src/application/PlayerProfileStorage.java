package application;
//import classes

public class PlayerProfileStorage extends Highscores {

	// define local vars
	private int pScore;
	private String pName;

	// constructor to store player name and score

	PlayerProfileStorage(String name, int score) throws Exception {
		
		//save the passed in vars
		pName = name;
		pScore = score;
		
		
		//call the super class to write the score (higshcores class)
		super.writeScore(pName, pScore);
	}

	// methods to return score
	public int getScoretime() {
		System.out.println(pScore);
		return pScore;
	}
	
	// methods to return name
	public String getPName() {
		System.out.println(pName);
		return pName;
	}
	
	// methods to set score
	public void setScoretime(int score) {
		pScore = score;
	}

	// methods to set name
	public void setPName(String name) {
		pName = name;
	}

}
