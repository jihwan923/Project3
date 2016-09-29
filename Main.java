/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Jihwan Lee
 * jl54387
 * 16445
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL: https://github.com/jihwan923/Project3
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	public static Set<String> inputDictionary;
	public static String startWord;
	public static String endWord;

	
	public static void main(String[] args) throws Exception {
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		
		ArrayList<String> userInput = parse(kb);
		initialize();
		if (!userInput.isEmpty()){ 
			startWord = userInput.get(0);
			endWord = userInput.get(1);
			ArrayList<String> ladder = getWordLadderDFS(userInput.get(0), userInput.get(1));
			printLadder(ladder);
		}
	}
	
	/**
	  * Initialize important field in Main
	  * @param nothing is passed in
	  * @return nothing is returned
	  */
	public static void initialize() {
		inputDictionary = makeDictionary(); // create a static dictionary BFS and DFS can refer to
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		String inputLine;
		ArrayList<String> keyboardInput = new ArrayList<String>();
		
		inputLine = keyboard.nextLine(); // read user's input
		
		if (inputLine.contains("/quit")){ // if user enters /quit, end the program
			System.exit(1);
		}
		
		String [] inputList = inputLine.split("\\s"); // split the user inputs
		
		for(int index = 0; index < inputList.length; index++){ // add them to the array list
			keyboardInput.add(inputList[index].toUpperCase());
		}
		
		return keyboardInput;
	}
	
	/**
	  * Finds a word ladder between start and end using depth-first-search with recursion.
	  * @param start is the start word
	  * @param end is the end word
	  * @return return word ladder result if there is a path from start word to end word
	  */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		Set<String> visitedWords = new HashSet<String>();
		ArrayList<String> resultLadder = new ArrayList<String>();
		
		if (start.equals(end)){
			return resultLadder; // return empty ladder if start is start
		}
		
		try{
			resultLadder = getWordLadderDFS(start, end, visitedWords);
		}
		catch(StackOverflowError e){ // try from the reverse if start to end causes overflow path
			visitedWords = new HashSet<String>(); // create a new visited word list
			resultLadder = getWordLadderDFS(end, start, visitedWords);
			if (!resultLadder.isEmpty()){ // if there was a ladder result, reverse the ladder
				ArrayList<String> modifiedResult = new ArrayList<String>();
				for(int i = resultLadder.size() - 1; i >= 0; i--){
					modifiedResult.add(resultLadder.get(i));
				}
				return modifiedResult;
			}
		}
		return resultLadder;
	}
	
	/**
	  * Finds a word ladder between start and end using breadth-first-search.
	  * @param start is the start word
	  * @param end is the end word
	  * @return return word ladder result if there is a path from start word to end word
	  */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		Set<String> visitedWords = new HashSet<String>();
		ArrayList<String> ladderResult =  new ArrayList<String>();
		
		if (start.equals(end)){ // return an empty ladder if start word and end word are same
			return ladderResult;
		}
		
		Queue<ArrayList<String>> wordQueue = new ArrayDeque<ArrayList<String>>(); // create a FIFO queue 
		ArrayList<String> initialLadder = new ArrayList<String>();
		int i = 0;
		
		initialLadder.add(start); // start with the starting word
		wordQueue.add(initialLadder); // place the initial starting word to the queue
		visitedWords.add(start); // indicate that start word was visited
		
		while(!wordQueue.isEmpty()){ // queue will become empty when there is no more words left to progress
			ArrayList<String> topLadder = (ArrayList<String>)wordQueue.remove();
			
			String lastString = topLadder.get(topLadder.size() - 1); // get the latest string from the queue ladder
			
			if (lastString.equals(end)){ // if end word is reached, return the ladder with the path
				return topLadder;
			}
			
			char[] charArray = lastString.toCharArray(); // convert the string to character arrays
			
			i = 0;
			while(i < charArray.length){ // this loop adds every possible one-letter difference word in the level to the queue
				for(char ch = 'A'; ch <= 'Z'; ch++){
					char tempChar = charArray[i];
					
					if(charArray[i] != ch){ // be sure new character array results in different word
						charArray[i] = ch;
					}
					
					String potentialWord = new String(charArray); // create a new word using the character array
					
					/* if potential word wasn't visited and dictionary doesn't have it,
					 *  add ladder containing start to the word into the queue */
					if(!visitedWords.contains(potentialWord)){ 
						if (inputDictionary.contains(potentialWord)){
							ArrayList<String> newLadder = new ArrayList<String>();
							newLadder.addAll(topLadder);
							newLadder.add(potentialWord);
							wordQueue.add(newLadder);
							visitedWords.add(potentialWord); // add the word to visited list 
						}
					}
					
					charArray[i] = tempChar; // revert back to the original char array
				}
				i += 1;
			}
		}
		
		return ladderResult; // return an empty list if there is no path from start to end
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
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
	
	public static void printLadder(ArrayList<String> ladder) {
		if (ladder.isEmpty()){
			System.out.println("no word ladder can be found between " + startWord.toLowerCase() + " and " + endWord.toLowerCase() + ".");
		}
		else{
			int ladderCount = ladder.size() - 2;
			System.out.println("a " + ladderCount + "-rung word ladder exists between " + startWord.toLowerCase() + " and " + endWord.toLowerCase() + ".");
			/*for (int i = 0; i < ladder.size(); i++){
				System.out.println(ladder.get(i).toLowerCase());
			}*/
		}
	}
	

	/**
	  * This method is a helper method for DFS that passes in additional parameter for recursion.
	  * @param start is the start word or intermediate words to be checked between the ladder
	  * @param end is the end word
	  * @param visitedWords is the list of all visited words in the dictionary
	  * @return return word ladder result if there is a path from start word to end word
	  */
	private static ArrayList<String> getWordLadderDFS(String start, String end, Set<String> visitedWords){
		ArrayList<String> ladder = new ArrayList<String>();

		if (start.equals(end)){ // base case: if start 'reached' end, return ladder with end word
			ladder.add(end);
			return ladder;
		}
		
		ladder.add(start); // if start didn't reach end, add it to the ladder and add it to visited list
		visitedWords.add(start);

		char[] charArray = start.toCharArray(); // convert start into character array

		int i = 0;
		int j = 0;
		char tempChar;
		
		for(j = 0; j < end.length(); j++){ // Try characters that are most close to end word
			tempChar = charArray[j];
			if(charArray[j] != end.charAt(j)){
				charArray[j] = end.charAt(j);
			
				String potentialWord = new String(charArray);
				
				// recursively call DFS if potential word is in dictionary and hasn't been visited
				if (!visitedWords.contains(potentialWord)){ 
					if (inputDictionary.contains(potentialWord)){
						ArrayList<String> newLadder = getWordLadderDFS(potentialWord, end, visitedWords);
						if (newLadder.contains(end)){
							ladder.addAll(newLadder);
							return ladder;
						}
					}
				}
			}
				
			charArray[j] = tempChar;
		}
		
		for (i = 0; i < charArray.length; i++){ // try other characters and positions if previous loop didn't work
			for(char ch = 'A'; ch <= 'Z'; ch++){ 
				tempChar = charArray[i];
				if(charArray[i] != ch){
					charArray[i] = ch;

					// recursively call DFS if potential word is in dictionary and hasn't been visited
					String potentialWord = new String(charArray);
					if (!visitedWords.contains(potentialWord)){
						if (inputDictionary.contains(potentialWord)){
							ArrayList<String> newLadder = getWordLadderDFS(potentialWord, end, visitedWords);
							if (newLadder.contains(end)){
								ladder.addAll(newLadder);
								return ladder;
							}
						}
					}
				}
				charArray[i] = tempChar;
			}
		}

		
		ladder.remove(start); // if the start is a dead-end, remove it
		
		return ladder; // return just a simple ladder with just start if no path is found
	}
	
	
}
