package assignment3;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

public class Project3Tester {
			@Test
			public void test() {
				int i = 0;
				int randomIndex;
				String start;
				String end;
				Random randomWord = new Random();
				ArrayList<String> dict = makeDictionary();
				ArrayList<String> BFSLadder = new ArrayList<String>();
				ArrayList<String> DFSLadder = new ArrayList<String>();
				while(i < 1000){
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
					i++;
					if(!BFSLadder.isEmpty() && !DFSLadder.isEmpty()){
						System.out.println("BFS and DFS both found a ladder.");
					}
					if(!BFSLadder.isEmpty()){
						System.out.print("BFS Ladder: ");
						Main.printLadder(BFSLadder);
					} else if (BFSLadder.isEmpty()){
						Main.printLadder(BFSLadder);
					}
					if(!DFSLadder.isEmpty()){
						System.out.print("DFS Ladder: ");
						Main.printLadder(DFSLadder);
					} else if (DFSLadder.isEmpty()){
						Main.printLadder(DFSLadder);
					}
					System.out.print('\n');
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
