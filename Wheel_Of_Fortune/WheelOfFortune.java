import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class WheelOfFortune {
	
	public Stack S2 = new Stack(26);
	public Stack tempS2 = new Stack(26);
	public Stack S3 = new Stack(10);
	public Stack unsortedS3 = new Stack(10);
	public Stack tempS3 = new Stack(10);
	public Stack S4 = new Stack(10);
	public Stack unsortedS4 = new Stack(10);
	public Stack tempS4 = new Stack(10);
	public Queue Q1 = new Queue(10000); //I have defined high bounds in order to be able to add and subtract multiple times to the linear queue.
	public Queue Q2 = new Queue(10000);
	public Queue tempQ1 = new Queue(10000);
	public Queue tempQ2 = new Queue(10000);
	public int step = 1;
	public int score = 0;
	public int value = 0;
	
	public WheelOfFortune() throws InterruptedException, IOException {
		Scanner input = new Scanner(System.in);
		String name ="";
		while(true) {
			System.out.println("Please enter your name (without spaces): ");
			name = input.nextLine();
			if(!name.contains(" "))
			break;
		}
		input.close();
		File countriesFile = new File("countries.txt");
		Scanner reader = new Scanner(countriesFile);
		
		File countriesFileLineCounter = new File("countries.txt");
		Scanner lineReader = new Scanner(countriesFileLineCounter);
		
		//Since the number of countries is uncertain, I first determine the borders of countries stack by reading the text file.
		int lineCounter = 0;
		while(lineReader.hasNextLine()) {
			lineReader.nextLine();
			lineCounter++;
		}
		Stack S1 = new Stack(lineCounter);
		Stack unsortedS1 = new Stack(lineCounter);
		
		// Creating an unsorted S1 stack.
		while (reader.hasNextLine()) { 
			String data = reader.nextLine();
			unsortedS1.push(data.toUpperCase(Locale.ENGLISH));
			}
		reader.close();
		lineReader.close();
		
		// Sorting S1 stack.
		S1= sortingStack(unsortedS1, S1); 
		
		//Creating S2 Stack for letters.
		String alphabetLetters= "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
		for(int i = 25; i >= 0; i--) {
			S2.push(alphabetLetters.charAt(i));
		}
		
		// Random country selection from the S1 stack.
		String country="";
		int random = (int)(Math.random() * 196) + 1; // Generates a random number in the range [1,197) (Countries text file has 196 country names).
		for(int i = 0; i < random; i ++) { 
			country = (String)S1.pop();
		}
		
		// Creating queues Q1 and Q2 based on selected country.
		for(int i = 0 ; i < country.length(); i++) { 
			Q1.enqueue(country.charAt(i));
			Q2.enqueue('-');
		}
		
		System.out.println("Randomly Generated Number: " + random);
		System.out.println("");
		
		// Game part.
		int wordLetterCounter = 0;
		while(true) {
			boolean validLetter = false;
			char letter = ' ';
			System.out.print("Word: " );
			for(int i = 0 ; i < Q1.size(); i++) {
				System.out.print(Q2.peek());
				Q2.enqueue(Q2.dequeue());	
			}
			//Printing first part of the screen
			System.out.print("		");
			System.out.print("Step: " + step + "   ");
			System.out.print("Score: " + score);
			System.out.print("		");
			// Printing valid letters
			while(!S2.isEmpty()) {
				tempS2.push(S2.peek());
				System.out.print(S2.pop());
			}
			// Getting value from the wheel
			value = wheel();
			// Selecting valid letter for guess
			while(value != 2) { // if value = 2, wheel is bankrupt.
				int randomLetter = (int)(Math.random() * 26) ; // Generates a random number in the range [0,26)
				letter = alphabetLetters.charAt(randomLetter);
				while(!S2.isEmpty()) {
					if(S2.peek().equals(letter)) {
						S2.pop();
						validLetter = true;
					}
					else {
						tempS2.push(S2.peek());
						S2.pop();
					}
				}
				while(!tempS2.isEmpty()) {
					S2.push(tempS2.peek());
					tempS2.pop();
				}
				if(validLetter == true)
					break;
			}
			
			//Printing second part of the screen
			System.out.println("");
			printWheel(value);
			System.out.println("Guess: " + letter );
			System.out.println("_______________________________________________________________________________________________________________________________________________________________________");
			System.out.println("");
			// Fixing stack for bankrupt status.
			while(!tempS2.isEmpty()) {
				S2.push(tempS2.peek());
				tempS2.pop();
			}
			
			int letterCounter = 0;
			for(int i = 0 ; i < Q1.size(); i++) { 
				if(letter == (char)Q1.peek()) {
					Q2.dequeue();
					Q2.enqueue(letter);
					letterCounter++;
				}
				else {
					Q2.enqueue(Q2.dequeue());
				}
				Q1.enqueue(Q1.dequeue());	
			}
			
			wordLetterCounter = wordLetterCounter + letterCounter;
			step++;
			
			// Calculating score
			score = scoreCalculator(letterCounter, value, score);
			
			//Game ending status
			if(wordLetterCounter == Q1.size()) {
				System.out.print("Word: " );
				for(int i = 0 ; i < Q2.size(); i++) {
					System.out.print(Q2.peek());
					Q2.enqueue(Q2.dequeue());	
				}
				System.out.print("		");
				System.out.print("Step: " + step + "   ");
				System.out.println("Score: " + score);
				System.out.println("");
				System.out.println("You win " + score + " !!!");
				System.out.println("_______________________________________________________________________________________________________________________________________________________________________");
				System.out.println("");
				break;
			}
				
			
		}
		highScoreTable(name, score);
		
	}
	
	public void printWheel(int value) {
		if(value == 1)
			System.out.println("Wheel: Double Money");
		else if(value == 2)
			System.out.println("Wheel: Bankrupt");
		else
			System.out.println("Wheel: " + value );
	}
	
	public Stack sortingStack(Stack unsortedStack, Stack sortedStack) {
		String temp;
		String temp2;
		sortedStack.push((String)unsortedStack.pop());
		while(!unsortedStack.isEmpty()) // Creating a sorted S1 stack.
	    {
			temp = (String)unsortedStack.pop();
			temp2 = (String)sortedStack.peek();
	        int compare = temp2.compareToIgnoreCase(temp);
	        while(!sortedStack.isEmpty() && compare < 0){
	        	unsortedStack.push(sortedStack.pop());
	            if(sortedStack.isEmpty())
	            	break;
	            else {
	            	temp2 = (String)sortedStack.peek();
		            compare = temp2.compareToIgnoreCase(temp);
	            }
	            
	        }
	        sortedStack.push(temp);
	    }
		
		return sortedStack;
	}
	
	public void highScoreTable(String playerName , int playerScore) throws IOException {
		File scoresFile = new File("HighScoreTable.txt");
		Scanner scoreReader = new Scanner(scoresFile);
	    String name ="";
	    String [] line = new String[2];
	    String textLine ="";
	    int score = 0;
	    while (scoreReader.hasNextLine()) { // Creating unsorted S4 and S3 stacks.
	        textLine = scoreReader.nextLine();
	        line = textLine.split(" ");
	        name = line[0];
	        score = Integer.parseInt(line[1]);
	        if((!unsortedS3.isFull()) && (!unsortedS4.isFull())) {
	        	unsortedS3.push(name);
		        unsortedS4.push(score);
	        }
	      }
	      scoreReader.close();
	      int tempScore = 0;
	      int tempScore2 = 0;
	      String tempName = "";
	      if(!unsortedS4.isEmpty()) {
	    	  S4.push((int)unsortedS4.pop());
	      }  
	      if(!unsortedS3.isEmpty()) {
	    	  S3.push((String)unsortedS3.pop());
	      }
	      while((!unsortedS4.isEmpty()) && (!unsortedS3.isEmpty())) // Creating sorted S4 and S3 stacks.
		    {
	    	  	tempScore = (int)unsortedS4.pop();
	    	  	if(!S4.isEmpty()) {
	    	  		tempScore2 = (int)S4.peek();
	    	  	}
	    	  	tempName = (String)unsortedS3.pop();
		        while((!S4.isEmpty()) && (!S3.isEmpty()) && (!unsortedS4.isFull()) && (!unsortedS3.isFull()) && (tempScore < tempScore2)){
		            unsortedS4.push(S4.pop());
		            unsortedS3.push(S3.pop());
		            if(S4.isEmpty())
		            	break;
		            else {
		            	tempScore2 = (int)S4.peek();
		            }
		        }
		        S4.push(tempScore);
		        S3.push(tempName);
		    }
	      int compareScore = 0;
	      while((!tempS4.isFull()) && (!tempS3.isFull())){
	    	  while((!S4.isEmpty()) && (!S3.isEmpty())){
	    		  compareScore =(int)S4.peek();
		    	  if(compareScore >= playerScore){
		    		  tempS4.push(S4.pop());
		    		  tempS3.push(S3.pop());
		    	  }  
		    	  else
		    		  break;
	    	  }
	    	  if((!tempS4.isFull()) && (!tempS4.isFull())) {
	    		  tempS4.push(playerScore);
	    		  tempS3.push(playerName);
	    		  while((!tempS4.isFull()) && (!tempS3.isFull()) && (!S3.isEmpty()) && (!S4.isEmpty())) {
	    			  tempS4.push(S4.pop());
		    		  tempS3.push(S3.pop());
	    		  }
	    	  }
	      }
	      // Fixing stacks to be able to reuse
	      while(!S3.isEmpty()) {
    		  S3.pop();
    	  }
    	  while(!S4.isEmpty()) {
    		  S4.pop();
    	  }
    	  while((!tempS3.isEmpty()) && (!tempS4.isEmpty()) && (!S3.isFull()) && (!S4.isFull())) {
    		  S3.push(tempS3.pop());
    		  S4.push(tempS4.pop());
    	  }
    	  int counter = 1; // Counter to print only the first 10, even if reading from a different text file
    	  FileWriter writer = new FileWriter("HighScoreTable.txt");
    	  System.out.println("-- High Score Table --");
	      while((!S4.isEmpty()) && (!S3.isEmpty()) && counter <= 10) {
	          writer.write(S3.peek() + " " + S4.peek() + "\n");
	    	  System.out.println(" " + S3.pop() + "\t" + S4.pop());
	    	  counter++;
	      }
	      writer.close();
	      
	}
	
	public int wheel() {
		int value = 0;
		int randomNumber = (int)(Math.random() * 8) + 1; // Generates a random number in the range [1,9)
		switch(randomNumber) {
		case 1:
			value = 10;
			break;
		case 2:
			value = 50;
			break;
		case 3:
			value = 100;
			break;
		case 4:
			value = 250;
			break;
		case 5:
			value = 500;
			break;
		case 6:
			value = 1000;
			break;
		case 7:
			value = 1; //Double Money
			break;
		case 8:
			value = 2; //Bankrupt
			break;
		}
		return value;
	}

	public int scoreCalculator(int letterCounter, int value,int score){
		switch(value) {
		case 10:
			score = score + (10 * letterCounter);
			break;
		case 50:
			score = score + (50 * letterCounter);
			break;
		case 100:
			score = score + (100 * letterCounter);
			break;
		case 250:
			score = score + (250 * letterCounter);
			break;
		case 500:
			score = score + (500 * letterCounter);
			break;
		case 1000:
			score = score + (1000 * letterCounter);
			break;
		case 1:
			if(letterCounter > 0) {
				score = score * 2;
			}
			break;
		case 2:
			score = 0;
			break;
		}
		return score;
	}
}
