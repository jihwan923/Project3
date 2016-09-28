package assignment3;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.junit.Test;

public class Project3Test {

	@Test
	public void individualTest() {
		Scanner s = new Scanner(System.in);
		ArrayList<String> BFSLadder = new ArrayList<String>();
		ArrayList<String> DFSLadder = new ArrayList<String>();
		int randomIndex;
		String start;
		String end;
		ArrayList<String> dict = makeDictionary();
		
		while(!s.nextLine().contains("q")){
			Random randomWord = new Random();
			randomIndex =randomWord.nextInt(dict.size());
			start = dict.get(randomIndex);
			Main.startWord = start;
			randomIndex = randomWord.nextInt(dict.size());
			end = dict.get(randomIndex);
			Main.endWord = end;
			Main.initialize();
			while(start.equals(end) || start.equals(null) || end.equals(null)){
				randomIndex = randomWord.nextInt(dict.size());
				end = dict.get(randomIndex);
			}
			BFSLadder = Main.getWordLadderBFS(start, end);
			DFSLadder = Main.getWordLadderDFS(start, end);
			if(!BFSLadder.isEmpty() && !DFSLadder.isEmpty()){
				Main.printLadder(BFSLadder);
				Main.printLadder(DFSLadder);
			}
		}
	}
	
	public ArrayList<String>  makeDictionary() {
		ArrayList<String> words = new ArrayList<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
