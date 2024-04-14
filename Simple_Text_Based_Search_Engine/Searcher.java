import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

// !!! Note: The address required for reading text files is located on line 207. !!!

public class Searcher {
	private HashedDictionary<Integer, AList<ListEntry>> hashTable = new HashedDictionary<Integer, AList<ListEntry>>();
	
	public Searcher() throws FileNotFoundException {
		
		for(int i = 1; i <= 511; i++) { // a loop that allows 511 text files to be read one by one and added to the hash table.
			String text = fileReader(i);
			text = text.toLowerCase(Locale.ENGLISH);
			String [] splittedText = splitText(text);
			int totalWordCount = splittedText.length;
			String fileName = getFileName(i); // the method that returns which text file should be read according to the number "i".
			addingToHash(splittedText, fileName, totalWordCount); // a method that adds split words to the hash table.
		}
		
		mostRelevantDocument(); // a method that takes three separate words from the user and returns the most relevant file.
		
	}
	
	
	public void mostRelevantDocument () {
		String [] words = getThreeWord();
		AList<ListEntry> fileList = new AList<ListEntry>();
		boolean isWordsExistsInAnyTxt = false;
		for (int i = 0 ; i < words.length; i ++) {
			int key = polynomialAccumulationFunction(words[i]);
			//int key = simpleSummationFunction(words[i]);
			if((words[i]!= "") && hashTable.contains(key, words[i])) {
				AList value = hashTable.getValue(key, words[i]);
				isWordsExistsInAnyTxt = true;
				for(int j = 1; j <= value.getLength(); j++) { // a new arraylist is created based on the file names.
					ListEntry entry = (ListEntry)value.getEntry(j);
					String fileName = entry.getFileName();
					int count = entry.getCount();
					int totalCount = entry.getTotalWordCount();
					ListEntry fileEntry = new ListEntry("" , fileName, totalCount);
					fileEntry.setCount(count);
					fileList = fileCounter(fileList , fileEntry);
					
				}
				
			}
		}
		if(isWordsExistsInAnyTxt == true) { // if the words received from the user are in the hash table, the most relevant file is searched.
			double max = 0.0;
			double wordRatio = 0.0;
			ListEntry maxEntry = null;
			for(int k = 1; k <= fileList.getLength(); k++) {
				ListEntry entry = (ListEntry)fileList.getEntry(k);
				wordRatio = (double)entry.getCount()/ (double) entry.getTotalWordCount(); // the ratio is found by dividing the total number of searched words in the text file by the total number of words in the text file.
				
				if(wordRatio >  max) {
					max = wordRatio;
					maxEntry = entry;
				}
			}
			System.out.println("Most relevant file is : " + maxEntry.getFileName());
		}
		else {
			System.out.println("These words could not be found in any txt !");
		}
		
	}
	
	public AList<ListEntry> fileCounter(AList<ListEntry> fileList, ListEntry newEntry) { // it checks the entries in the file list, and if the file name of a newly added entry is in the list, the count of the entry that is in the list is increased by the number of the newly added entry. If the newly added file is not in the entry list, it is added last.
		boolean isFileContains = false;
		for(int i = 1 ; i <= fileList.getLength(); i++) {
			ListEntry fileEntry = (ListEntry) fileList.getEntry(i);
			if(fileEntry.getFileName().equals(newEntry.getFileName())) {
				fileEntry.setCount(fileEntry.getCount() + newEntry.getCount());
				isFileContains = true;
				break;
			}
		}
		if(isFileContains == false) {
			fileList.add(newEntry);
		}
		return fileList;
	}
	
	public String [] getThreeWord() { // it takes 3 separate words from the user
		Scanner input = new Scanner(System.in);
		System.out.println("Please, enter three words separated by a single space :");
		String threeWords = input.nextLine();
		threeWords = threeWords.toLowerCase(Locale.ENGLISH);
		String [] words = threeWords.split(" ");
		
		// if the user enters the same words
		if(words.length >= 3) {
			if(words[0].equals(words[1]))
				words[1] = "";
			if (words[0].equals(words[2]))
				words[2] = "";
			if (words[1].equals(words[2]))
				words[2] = "";
		}
		
		input.close();
		return words;
	}	
	
	public void addingToHash(String[] text, String fileName, int totalWordCount) {// the method that adds words to the hash table
		for(int i = 0; i < text.length ; i++) {
			if((text[i] != "") && (text[i] != null)) {
				boolean isAdded = false;
				int key = polynomialAccumulationFunction(text[i]);
				//int key = simpleSummationFunction(text[i]);
				
				if(hashTable.contains(key, text[i])) { // if the word is in the table, the arraylist, which is the value of the word, is looked at.
					AList<ListEntry> value = hashTable.getValue(key, text[i]);
					
					for(int j = 1; j <= value.getLength(); j ++) {
						if(value.getEntry(j).getFileName().equals(fileName)) {
							value.getEntry(j).setCount(value.getEntry(j).getCount() + 1); // if there is an entry in the arraylist with the same name of the text file, the count of this entry is increased by 1.
							isAdded = true;
							break;
						}
					}
					
					if(isAdded == false) { // if there is no entry with the same text file name, a new entry is created and added to the arraylist.
						ListEntry listValue = new ListEntry(text[i], fileName, totalWordCount);
						value.add(listValue);
					}
				}
				
				else { // if the word is not in the hash table, it is added by finding the appropriate index.
					AList<ListEntry> newAList = new AList<ListEntry>();
					ListEntry listValue = new ListEntry(text[i], fileName, totalWordCount);
					newAList.add(listValue);
					hashTable.add(key, newAList);
				}
			}
		}
	}
	
	//The method that calculates the times required to fill the table
	/*
	public void printPerformanceTable(long indexingTime){

		try {
			File txtFile = new File ("C:\\Users\\Ozan\\Desktop\\search.txt");
			Scanner fileReader = new Scanner(txtFile);
			String searchWord = "";
			long averageTime = 0;
			long maxTime = 0;
			long minTime = 2111111111;
			int count = 0;
			String minWord = null;
			String maxWord = null;
			while(fileReader.hasNext()) {
				count++;
				searchWord = fileReader.nextLine();
				
				long searchTime = searchTime(searchWord);
				averageTime = averageTime + searchTime;
				if(searchTime > maxTime) {
					maxTime = searchTime;
					maxWord = searchWord;
				}
				else if(searchTime < minTime) {
					minTime = searchTime;
					minWord = searchWord;
				}
			}
			averageTime = averageTime / count;
			System.out.println("---------------------------------------------------------");;
			System.out.println("Indexing Time = " + indexingTime);
			System.out.println("Average Time = " + averageTime);
			System.out.println("Max Time = " + maxTime + " - Max Word = " + maxWord);
			System.out.println("Min Time = " + minTime + " - Min Word = " + minWord);
			fileReader.close();
		}
		catch(FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	*/
	
	/*
	public long searchTime(String word) {
		int key = polynomialAccumulationFunction(word);    // (key for PAF)
		//int key = simpleSummationFunction(word);
		 
		long time1 = System.nanoTime();
		hashTable.search(key, word);
		long time2 = System.nanoTime();
		long searchTime = time2-time1;
		return searchTime;
	}
	*/
	
	// a function that reads the file and assigns the words in the file to the string.
	public String fileReader(int i) {
		String text = "";
		String fileName= getFileName(i);
		
		try {
			String txtFileAddress = "C:\\Users\\Ozan\\Desktop\\sport\\" + fileName;
			File txtFile = new File (txtFileAddress);
			Scanner fileReader = new Scanner(txtFile);
			while(fileReader.hasNext()) {
				text = text + fileReader.nextLine() + " ";
			}
			fileReader.close();
			return text;
		}
		catch(FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return text;
	}
	
	// The function used to calculate the collision counts for the table.
	/*
	public void collisionCounter() {
		int totalCollision = 0;
		try {
			File txtFile = new File ("C:\\Users\\Ozan\\Desktop\\search.txt");
			Scanner fileReader = new Scanner(txtFile);
			String word = null;
			while(fileReader.hasNext()) {
				word = fileReader.nextLine();
				int key = polynomialAccumulationFunction(word);
				//int key = simpleSummationFunction(word);
				int collisionCount = hashTable.collisionCount(key, word);
				//System.out.println(word + " - " + collisionCount + " - " + hashTable.contains(key, word) );
				totalCollision = totalCollision + collisionCount;
				
			}
			fileReader.close();
		}
		catch(FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      }
			System.out.println("Total Collision : " + totalCollision);
			
		
	}
	*/

	
	public String[] splitText(String text) { // a function that clears all characters that are not alphabetic characters and splits the text by a space
		
		for(int i = 0; i < text.length(); i ++) {
			char ch = text.charAt(i);
			int charInt = (int) ch;
			if (charInt > 122 || charInt < 97)
				text = text.replace(ch, ' ');
		}
		while(text.contains("  ")) { // converts all multiple spaces into a single space.
			text = text.replaceAll("  ", " ");
		}
		
		
		String[] splittedText = text.split(" ");
		return splittedText;
	}
	
	public int getCharValue(char ch) { // a function that converts the ASCII values of chars to their order in the alphabet.
		ch = Character.toLowerCase(ch);
		int value = (int)ch - 96;
		
		return value;
	}
	
	public int simpleSummationFunction(String text) {
		int value = 0;
		int charValue = 0;
		for(int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			charValue = getCharValue(ch);
			value = value + charValue;
		}
		
		return value;
	}
	
	public int polynomialAccumulationFunction (String text) { // PAF function allows overflow.
		long value = 0;
		long charValue = 0;
		long constant = 0;
		long multiply;
		for(int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			charValue = getCharValue(ch);
			constant =(long) Math.pow(31, (text.length() - (i+1)));
			multiply =  (charValue * constant);
			value =(value + multiply);
		}
		int intValue = (int) value;
		return intValue;
	}
	
	public String getFileName(int i) { // the function that generates the name of the text file according to the entered number
		String fileName="";
		if(i < 10)
			fileName = "00" + i + ".txt";
		else if(10 <= i && i <100)
			fileName = "0" + i + ".txt";
		else
			fileName = i + ".txt";
		
		return fileName;
	}
}


