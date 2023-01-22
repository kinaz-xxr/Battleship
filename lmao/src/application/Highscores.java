package application;

//import classes
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Highscores {
	// define local vars

	private File dataFile;

	private FileWriter out;
	private BufferedWriter writeFile;

	private FileReader in;
	private BufferedReader readFile;
	private String score;
	public ArrayList<String> namescores, tempnamescores;
	public ArrayList<Integer> scores, tempscores;

	// default contrsuctor
	Highscores() throws Exception {

		// init local vars

		dataFile = new File("highScores.txt");

		out = new FileWriter(dataFile, true); // will append NOT overwrite
		writeFile = new BufferedWriter(out);

		in = new FileReader(dataFile);
		readFile = new BufferedReader(in);

		score = "";

		scores = new ArrayList<Integer>();
		tempscores = new ArrayList<Integer>();

		namescores = new ArrayList<String>();
		tempnamescores = new ArrayList<String>();

	}

	// methods

	// this method takes in the information for a score: name and number, and
	// appends to a file
	public void writeScore(String name, int score) {
		try {

			// write the name then score on next line and flush to file
			writeFile.write(name);
			writeFile.newLine();
			writeFile.write(Integer.toString(score));
			writeFile.newLine();
			writeFile.flush();

		} catch (Exception e) {
			
			//print errors catch errors
			System.err.println(e);

		}

	}

	// this method reads all the scores currently stored in the file name then,
	// score
	public void readScore() {
		try {
			//read information only if not an empty line
			while ((score = readFile.readLine()) != null) {
				namescores.add(score); // add name
				scores.add(Integer.parseInt(readFile.readLine())); // add score time

			}
		} catch (Exception e) {
			//print errors catch errors
			System.out.print("File does not exist or is not found");
			System.out.println(e.getMessage());
		}
	}

	// this methods clears all scores from the txt file
	public void clearScores() throws Exception {
		// create new empty file and set append to true
		out = new FileWriter(dataFile);
		out = new FileWriter(dataFile, true);

		// read file again after clearing
		readScore();
	}

	// this method sorts the scores
	public void sortScores(boolean sortingOrder) {
		if (sortingOrder) { // high to low sort

			// create temp arraylists to sort values
			tempscores.addAll(scores);
			tempnamescores.addAll(namescores);
			bubbleSort(true, tempscores);

			// organize the names to match scores
			for (int b = 0; b < namescores.size(); b++) {
				for (int a = 0; a < scores.size(); a++) {
					if (tempscores.get(a).equals(scores.get(b))) {
						
						//replace temp arraylist with the modified matching names
						tempnamescores.remove(a);

						tempnamescores.add(a, namescores.get(b));

					}
				}
			}

			// reset temp arrays to empty and write changes to permanent array
			scores.clear();
			scores.addAll(tempscores);
			namescores.clear();
			namescores.addAll(tempnamescores);
			tempscores.clear();
			tempnamescores.clear();

		} else if (!sortingOrder) { // low to high sort

			// create temp arraylists to sort values
			tempscores.addAll(scores);
			tempnamescores.addAll(namescores);
			bubbleSort(false, tempscores);

			// organize the names to match scores
			for (int b = 0; b < namescores.size(); b++) {
				for (int a = 0; a < scores.size(); a++) {
					if (tempscores.get(a).equals(scores.get(b))) {
						
						//replace temp arraylist with the modified matching scores
						tempnamescores.remove(a);

						tempnamescores.add(a, namescores.get(b));

					}
				}
			}

			// reset temp arrays to empty and write changes to permanent array
			scores.clear();
			scores.addAll(tempscores);
			namescores.clear();
			namescores.addAll(tempnamescores);
			tempscores.clear();
			tempnamescores.clear();

		}
	}

	// this method searches for a score based on the name and returns (linear
	// search)
	public int searchScores(String name) {

		// create int to return
		int valueScore = 0;

		// search the read array list for the corresponding name
		for (int a = 0; a < namescores.size(); a++) {
			if (namescores.get(a).equals(name)) {

				valueScore = Integer.parseInt(scores.get(a).toString());
				return valueScore;
			} else {
				valueScore = 0;

			}
		}

		// return int

		return valueScore;

	}

	// this sorting algorithm uses bubble sort in either ascending or descending
	// order based on passed in boolean
	public void bubbleSort(boolean sortingOrder, ArrayList<Integer> array) {

		if (sortingOrder) { // high to low sort
			boolean done = false;

			for (int end = array.size() - 1; end > 0 && !done; end--) {
				done = true;

				for (int i = 0; i < end; i++) {
					if (array.get(i).compareTo(array.get(i + 1)) < 0) {
						done = false;
						Collections.swap(array, i, i + 1);

					}
				}
			}

		} else if (!sortingOrder) { // low to high sort
			boolean done = false;

			for (int end = array.size() - 1; end > 0 && !done; end--) {
				done = true;

				for (int i = 0; i < end; i++) {
					if (array.get(i).compareTo(array.get(i + 1)) > 0) {
						done = false;
						Collections.swap(array, i, i + 1);

					}
				}
			}
		}

	}
}
