import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;

public class MillionaireGame {
	public Scanner input = new Scanner(System.in);
	public enigma.console.Console cn = Enigma.getConsole("Mouse and Keyboard");
	public TextMouseListener tmlis;
	public KeyListener klis;
	public int mousepr; // mouse pressed?
	public int mousex, mousey; // mouse text coords.
	public int keypr = 0; // key pressed?
	public int rkey;
	public int i = 0;
	public String tempContentStr;
	public char ch;
	public Random r = new Random();
	public static int q = 0;
	public Question[] questionArr = new Question[1000];
	public static int categoryTrueIndex=0;
	public String[] categoryTrueArr=new String[500];
	public static int categoryFalseIndex=0;
	public String[] categoryFalseArr=new String[500];
	public Participant[] partArr = new Participant[1000];
	public int[][] participantAge = {{0,0,0},{0,0,0}};
	public String[] stopWordArr = new String[851];
	public String answer = "";

	public MillionaireGame() throws Exception {
		tmlis = new TextMouseListener() {
			public void mouseClicked(TextMouseEvent arg0) {
			}

			public void mousePressed(TextMouseEvent arg0) {
				if (mousepr == 0) {
					mousepr = 1;
					mousex = arg0.getX();
					mousey = arg0.getY();
				}
			}

			public void mouseReleased(TextMouseEvent arg0) {
			}
		};
		cn.getTextWindow().addTextMouseListener(tmlis);

		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		cn.getTextWindow().addKeyListener(klis);
		boolean isGameRunning = true;
		boolean menu2Avaiable=false;
		String line;
		String text = "";
		String lineStop = "";
		String lineDict = "";
		int lineCounter = 0;
		String linePart3 = "";
		

		BufferedReader bReaderDict = readingFiles("dictionary.txt");
		BufferedReader bReaderDict2 = readingFiles("dictionary.txt");

		int p = 0;
		while ((linePart3 = bReaderDict2.readLine()) != null) {
			p++;
		}
		String[] dictionary = new String[p];

		BufferedReader bReaderStop = readingFiles("stop_words.txt");

		Scanner input = new Scanner(System.in);
		String stopWords = "";
		String linePart = "";
		String linePart2 = "";
		// we printed the menu on the screen


		Scanner input1 = new Scanner(System.in);
		BufferedReader bReader = readingFiles("questions.txt");

		while ((line = bReader.readLine()) != null) {
			text += line + "\n"; // writing the read file contents to the text string
			lineCounter++; // setting up the loop to keep the number of lines in the question file
		}
		int d = 0;
		while ((lineStop = bReaderStop.readLine()) != null) {
			stopWordArr[d] = lineStop; // putting the words in the stop word into the array
			d++;
		}
		d = 0;
		while ((lineDict = bReaderDict.readLine()) != null) {
			dictionary[d] = lineDict; // putting the words in the dictionary into the array
			d++;
		}
		Scanner input3 = new Scanner(System.in);
		String[][] contentArr = new String[lineCounter][9]; // creation of a two-dimensional array that holds all
															// questions and their information
		String[] content = text.split("\n");
		String c;
		for (int u = 0; u < content.length; u ++) {
			c = content[u];
			String[] originalContentArr = c.split("#");
			contentArr[u][0] = originalContentArr[0];
			contentArr[u][1] = originalContentArr[1];
			contentArr[u][2] = originalContentArr[2];
			contentArr[u][3] = originalContentArr[3]; // placing the contents of the text in the indexes of the searcher
			contentArr[u][4] = originalContentArr[4];
			contentArr[u][5] = originalContentArr[5];
			contentArr[u][6] = originalContentArr[6];
			contentArr[u][7] = originalContentArr[7];
			Question questions = new Question(contentArr[u][0], contentArr[u][1], contentArr[u][2], contentArr[u][3],contentArr[u][4], contentArr[u][5], contentArr[u][6], contentArr[u][7], u);
			
			questionArr[u] = questions;
			questionArr[u].setQuestionId(u);
		}

		BufferedReader bReaderPart2 = readingFiles("participants.txt");
		BufferedReader bReaderPart1 = readingFiles("participants.txt");

		int f = 0;
		while ((linePart = bReaderPart1.readLine()) != null) {
			f++;
		}

		String[] participants = new String[f];
		while ((linePart2 = bReaderPart2.readLine()) != null) {
			participants[q] = linePart2;
			q++;
		}

		i = 0;

	
		String[] arrLocation = new String[4];
		
		for (int y = 0; y < q ; y++) {
			String[] arrDate = new String[3];
			String x = participants[y];
			String[] newParticipant = x.split("#");
			String strDate = newParticipant[1];
			String strLocation = newParticipant[3];
			arrLocation = strLocation.split(";");
			strDate = strDate.replace(".", " "); // We changed the dots to spaces because it gives an error when using
													// the split command by point.
			arrDate = strDate.split(" ");
			int a1 = Integer.parseInt(arrDate[0]); // Since the Date class takes 3 integer arguments, we split the
													// variables and converted them to integers.
			int a2 = Integer.parseInt(arrDate[1]);
			int a3 = Integer.parseInt(arrDate[2]);
			Date date = new Date(a1, a2, a3); // creating date type objects and assigning values to variables inside the
												// constructor
			Phone number = new Phone(newParticipant[2]);
			Location classLocation = new Location(arrLocation[0], arrLocation[1], arrLocation[2], arrLocation[3],
					arrLocation[4]);
			Participant temp = new Participant(newParticipant[0], date, number, classLocation, y);
			partArr[y] = temp;
			
		}

		bReaderDict.close();
		bReaderDict2.close();
		bReaderStop.close();
		bReader.close();
		bReaderPart1.close();
		bReaderPart2.close();

		String line1;

		int j = 0;// Necessary variables were generated to determine the distribution of questions
					// according to categories and difficulties.
		String[] category = new String[lineCounter];
		String[] difficulty = new String[lineCounter];

		BufferedReader bReader1 = readingFiles("questions.txt");

		while ((line1 = bReader1.readLine()) != null) {
			String[] splited_line = line1.split("#");
			category[j] = splited_line[0];
			difficulty[j] = splited_line[7];
			j += 1;
		}

		category(category);
		difficultyLevel(difficulty);

		bReader1.close();
		System.out.println("");
		System.out.println("Files have been uploaded.");
		System.out.println("");
		int menuInput=0;

		while (isGameRunning) {
			while(true){
				menu();
				System.out.println("> Please enter your choice:");
				menuInput = parseIntOrNull(input.next());
				if(menuInput ==1 || menuInput ==2|| menuInput ==3|| menuInput ==4)
					break;
			}
			clearScreen();

			if (menuInput == 1) {
				spellCheck(contentArr, dictionary, questionArr);
			}
			if (menuInput == 2) {
				clearScreen();
				competition(contentArr, stopWordArr, partArr);
				menu2Avaiable=true;
			}
			Participant maxPrizePart=null;
			if(menuInput==3 && menu2Avaiable) {
				
				// successful contestant
				
                for(int u=0;u<q;u++) {
                    if(partArr[u].isPlayed()) {
                        maxPrizePart=partArr[u];
                        break;
                    }
                }
                for(int u=0;u<q;u++) {
                    if((partArr[u].isPlayed()) && (partArr[u].getPrize())>maxPrizePart.getPrize()){
                        maxPrizePart=partArr[u];
                    }
                }
                System.out.println("Most successful contestant:  " + maxPrizePart.getName());
                // most correctly answered category
                
                int categoryNumber=0;
                int index=0;
                int [] categoryNumberArr=new int[500];
                String categoryTrueArrString= "a";
                String categoryTrueArrString2= "b";
                for(int u=0; u <categoryTrueIndex; u++) {
                    for(int o=u+1; o < categoryTrueIndex; o++){
                    	if(categoryTrueArr[u] != null && categoryTrueArr[o] != null) {
                    		categoryTrueArrString = categoryTrueArr[u];
                    		categoryTrueArrString2 = categoryTrueArr[o];
                    	}
                    	else {
                    		categoryTrueArrString = "a";
                    		categoryTrueArrString2 = "b";
                    	}
                    	if(categoryTrueArrString.equals(categoryTrueArrString2)&& categoryTrueArr[u] != null && categoryTrueArr[o] !=null) {
                                categoryTrueArr[o] = null;
                                categoryNumber++;
                            }
                    	}
                    if(categoryTrueArr[u] != null){
                    	categoryNumberArr[index] = categoryNumber;
                        categoryNumber=0;
                        index++;
                    }
                    else
                    	categoryNumber = 0;
                    
                }
                
                String [] finalCategoryTrueArr=new String[index + 1];
                index=0;
                for(int u=0; u < categoryTrueArr.length;u++) {
                    if(categoryTrueArr[u] != null){
                        finalCategoryTrueArr[index]=categoryTrueArr[u];
                        index++;
                    }
                }

                int maxCategoryNumber=0;
                int maxCategoryIndex=0;
                for(int u=0;u<categoryNumberArr.length;u++) {
                    if(categoryNumberArr[u]>maxCategoryNumber) {
                        maxCategoryNumber=categoryNumberArr[u];
                        maxCategoryIndex=u;
                    }
                }
                System.out.println("Most correctly answered category: " + finalCategoryTrueArr[maxCategoryIndex]);
                
                // most badly answered category
                
                categoryNumber=0;
                index=0;
                int [] categoryNumberArr2=new int[500];
                String categoryFalseArrString= "a";
                String categoryFalseArrString2= "b";
                for(int u=0; u <categoryFalseIndex; u++) {
                    for(int o=u+1; o < categoryFalseIndex; o++){
                    	if(categoryFalseArr[u] != null && categoryFalseArr[o] != null) {
                    		categoryFalseArrString = categoryFalseArr[u];
                    		categoryFalseArrString2 = categoryFalseArr[o];
                    	}
                    	else {
                    		categoryFalseArrString = "a";
                    		categoryFalseArrString2 = "b";
                    	}
                    	if(categoryTrueArrString.equals(categoryFalseArrString2)&& categoryFalseArr[u] != null && categoryFalseArr[o] !=null) {
                                categoryFalseArr[o] = null;
                                categoryNumber++;
                            }
                    	}
                    if(categoryFalseArr[u] != null){
                    	categoryNumberArr2[index] = categoryNumber;
                        categoryNumber=0;
                        index++;
                    }
                    else
                    	categoryNumber = 0;
                    
                }
                String [] finalCategoryFalseArr=new String[index+1];
                index=0;
                for(int u=0; u<categoryFalseArr.length;u++) {
                    if(categoryFalseArr[u]!=null) {
                        finalCategoryFalseArr[index]=categoryFalseArr[u];
                        index++;
                    }
                }

                maxCategoryNumber=0;
                maxCategoryIndex=0;
                for(int u=0;u<categoryNumberArr2.length;u++) {
                    if(categoryNumberArr2[u]>maxCategoryNumber) {
                        maxCategoryNumber=categoryNumberArr2[u];
                        maxCategoryIndex=u;
                    }
                }

                System.out.println("Most badly answered category: " + finalCategoryFalseArr[maxCategoryIndex]);
                
                // age-overage
                double[] age1 = averageAge(participantAge);
                
                System.out.println("Age <=30 : " + age1[0]);
                System.out.println("30 < Age <= 50 : " + age1[1]);
                System.out.println("Age > 50 : " + age1[2]);
                
                // city
                
                int cityCounter = 0;
                String[] city = new String[q];
                int [] cityNumber = new int[q];
                
                for(int l = 0; l< q ; l++) {
                	city[l] = partArr[l].getAdress().getCity();
                }
                
                int indexCounter = 0;
                for (int e = 0; e < q; e++) {
                	if(!(city[e].equals("0"))){
                		for (int k = e + 1; k < q; k++){
            				if (city[e].equals(city[k])){
            					cityCounter = cityCounter + 1;
            					city[k] = "0";
            				}
            			}
                		cityNumber[indexCounter] =  cityCounter;
                		indexCounter++;
                	}
                	cityCounter = 0;
        		}
                int indexOfCity = 0;
                String[] finalCity = new String[indexCounter];
                for(int a = 0; a < q ; a ++) {
                	if(!(city[a].equals("0"))){
                		finalCity[indexOfCity] = city[a];
                		indexOfCity++;
                	}
                }
                int maxCity = cityNumber[0];
                int indexOfMaxCity = 0;
                for(int a = 0; a < cityNumber.length; a++) {
                	if(cityNumber[a] > maxCity) {
                		maxCity = cityNumber[a];
                		indexOfMaxCity= a;
                	}
                }
                System.out.println("The city with the highest number of participants: " + finalCity[indexOfMaxCity]);
                
            }
            if(menuInput==4) {
            	System.out.println("Bye-Bye!");
            	break;
            }
				
			
		}
		input.close();
		input1.close();
		input3.close();
		
	}

	public void spellCheck(String[][] contentArr, String[] dictionary, Question[] questionArr) {
		for (int j1 = 0; j1 < contentArr.length; j1++) {

			boolean correctSpell = false;
			boolean spellCheck = false;
			boolean reverseCheck = false;
			int letterCounter = 0;
			String yesNo = "";
			String yesNo2 = "";

			tempContentStr = contentArr[j1][1];
			for (int a = 0; a < tempContentStr.length(); a++) {
				ch = tempContentStr.charAt(a);
				if (!(65 <= (int) ch && (int) ch <= 90 || 97 <= (int) ch && (int) ch <= 122 || (int) ch == 32
						|| (int) ch == 39))
					tempContentStr = tempContentStr.replace(ch, ' ');
			}
			while (tempContentStr.indexOf("  ") != -1) {
				tempContentStr = tempContentStr.replace("  ", " ");
			}
			String question = tempContentStr;
			String[] questionWords = question.split(" ");
			for (int k = 0; k < questionWords.length; k++) {
				String questionword = questionWords[k];
				questionWords[k] = questionWords[k].toLowerCase();
				for (int l = 0; l < dictionary.length; l++) // We checked whether the word is in the dictionary or not.
				{
					if (questionWords[k].equals(dictionary[l])) {
						correctSpell = true;
						break;
					}
				}
				if (correctSpell == false) // if the word is not in the dictionary, we have shown the words in the
											// dictionary that match the number of letters to the user.
				{
					for (int m = 0; m < dictionary.length; m++) {
						if (questionWords[k].length() == dictionary[m].length()) {
							for (int n = 0; n < questionWords[k].length(); n++) {
								if (!(questionWords[k].charAt(n) == (dictionary[m].charAt(n)))) {
									letterCounter = letterCounter + 1;
									if (letterCounter >= 3)
										break;
								}
							}
							if (letterCounter == 1) {
								while (true) // We have replaced the words we showed the user with the word in the
												// question if the user wants
								{
									System.out.println(contentArr[j1][1]);
									System.out.println("The word \"" + questionWords[k]
											+ "\" in the question may be misspelled, did you mean \"" + dictionary[m]
											+ "\"? (yes/no)");
									Scanner input5 = new Scanner(System.in);
									yesNo = input5.nextLine();
									if (yesNo.equalsIgnoreCase("yes")) {
										int startIndex = contentArr[j1][1].indexOf(questionword);
										int endIndex = startIndex + questionWords[k].length();
										if (startIndex != 0) {
											contentArr[j1][1] = contentArr[j1][1].substring(0, startIndex)
													+ dictionary[m] + contentArr[j1][1].substring(endIndex);
											questionArr[j1].setQuestionText(contentArr[j1][1]);
										} else if (startIndex == 0) {
											contentArr[j1][1] = dictionary[m] + contentArr[j1][1].substring(endIndex);
											questionArr[j1].setQuestionText(contentArr[j1][1]);
										}
										spellCheck = true;
										break;
									} else if (yesNo.equalsIgnoreCase("no")) {
										break;
									}
								}
							} else if (letterCounter == 2) {
								int ch1 = 0;
								int ch2 = 0;
								boolean firstChar = false;
								for (int a = 0; a < questionWords[k].length(); a++) {
									if (questionWords[k].charAt(a) != dictionary[m].charAt(a)) {
										if (firstChar == false) {
											ch1 = a;
											firstChar = true;
										} else if (firstChar == true) {
											ch2 = a;
										}
									}
								}
								if (questionWords[k].charAt(ch1) == dictionary[m].charAt(ch2)
										&& questionWords[k].charAt(ch2) == dictionary[m].charAt(ch1)) {
									while (true) {
										System.out.println(question);
										System.out.println("The word \"" + questionWords[k]
												+ "\" in the question may be misspelled, did you mean \""
												+ dictionary[m] + "\"?(yes/no)");
										Scanner input6 = new Scanner(System.in);
										yesNo2 = input6.nextLine();
										if (yesNo2.equalsIgnoreCase("yes")) {
											int startIndex = contentArr[j1][1].indexOf(questionword);
											int endIndex = startIndex + questionWords[k].length();
											if (startIndex != 0) {
												contentArr[j1][1] = contentArr[j1][1].substring(0, startIndex)
														+ dictionary[m] + contentArr[j1][1].substring(endIndex);
												questionArr[j1].setQuestionText(contentArr[j1][1]);
											} else if (startIndex == 0) {
												contentArr[j1][1] = dictionary[m]
														+ contentArr[j1][1].substring(endIndex);
												questionArr[j1].setQuestionText(contentArr[j1][1]);
											}
											reverseCheck = true;
											break;
										} else if (yesNo2.equalsIgnoreCase("no")) {
											break;
										}

									}
								}
							}
							letterCounter = 0;
							if (spellCheck == true) {
								spellCheck = false;
								break;
							}
							if (reverseCheck == true) {
								reverseCheck = false;
								break;
							}
						}

					}
				}

				correctSpell = false;
			}
		}

	}

	public BufferedReader readingFiles(String fileName) throws FileNotFoundException {
		FileInputStream fStream = new FileInputStream(fileName);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(fStream));
		return bReader;
	}

	public void menu() {
        System.out.println(".---------------  + Who wants to be a Millionaire + --------------.");
        System.out.println("|                       1.Spell Check                             |");
        System.out.println("|                       2.Start Competition                       |");
        System.out.println("|                       3.Show Statistics                         |");
        System.out.println("|                       4.Exit                                    |");
        System.out.println(" ------------------------------------------------------------------");
        System.out.println("");
    }

	public void wordCloud(String[][] contentArr, String[] stopWordArr) {
		boolean stopWordCheck = false;
		String[] wordCloudArr = null;
		for (int l = 0; l < contentArr.length; l++) {
			tempContentStr = contentArr[l][1];
			for (int a = 0; a < tempContentStr.length(); a++) {
				ch = tempContentStr.charAt(a);
				if (!(65 <= (int) ch && (int) ch <= 90 || 97 <= (int) ch && (int) ch <= 122 || (int) ch == 32
						|| (int) ch == 39)) {
					tempContentStr = tempContentStr.replace(ch, ' ');
				}
			}
			while (tempContentStr.indexOf("  ") != -1) {
				tempContentStr = tempContentStr.replace("  ", " ");
			}
			String[] contentIndex1 = tempContentStr.split(" ");
			String wordCloud = "";
			for (int n = 0; n < contentIndex1.length; n++) {
				stopWordCheck = false;
				for (int k = 0; k < stopWordArr.length; k++) {
					if (contentIndex1[n].equalsIgnoreCase(stopWordArr[k])) {
						stopWordCheck = true;
						break;
					}
				}
				if (stopWordCheck == false) {
					wordCloud += contentIndex1[n] + " ";
				}
			}
			wordCloud = wordCloud.trim();
			wordCloudArr = wordCloud.split(" ");
			Random r = new Random();
			int wordCloudIndex = r.nextInt(wordCloudArr.length);
			contentArr[l][8] = wordCloudArr[wordCloudIndex];
		}
	}

	public void category(String[] category) {
		int wrc = 1;
		System.out.println("");
		System.out.println("Category" + "             " + "The Number Of Questions");
		System.out.println("---------" + "            " + "-----------------------");
		int position = 3;
		for (int n = 0; n < category.length; n++) // Nested loops were used to count categories.
		{
			for (int k = n + 1; k < category.length; k++) {

				if (category[n].equals(category[k])) {
					wrc = wrc + 1;
					category[k] = "0";
				}
			}
			if (category[n] != "0") {
				System.out.print(category[n]);
				cn.getTextWindow().setCursorPosition(32,position);
				System.out.println(wrc);
				position++;
			}
				
			wrc = 1;

		}
	}

	public void difficultyLevel(String[] difficulty) {
		System.out.println("\n\n\n");
		System.out.println("Difficulty Level" + "             " + "The Number Of Questions");
		System.out.println("----------------" + "             " + "-----------------------");
		int z1 = 0;
		for (int ax = 1; ax <= 5; ax++) {
			for (int a = 0; a < difficulty.length; a++) {
				String[] difficultyarr = difficulty[a].split(" ");
				String strdifficulty = difficultyarr[0];
				int difficultynumber = Integer.parseInt(strdifficulty); // //Difficulty levels were converted from
																		// string to integer by using Integer.parseInt
																		// function.
				if (difficultynumber == ax)
					z1 = z1 + 1;
			}
			System.out.println("     " + ax + "                                     " + z1);
			z1 = 0;
		}

	}

	public void competition(String[][] contentArr, String[] stopWordArr, Participant[] partArr)
			throws InterruptedException, IOException {
		wordCloud(contentArr, stopWordArr);
		int randomPart = 0;
		int questionNumber = 1;
		int difficulty2 = 1;
		boolean changeParticipant = false;
		int counterParticipant = 0;
		int prize = 0;
		boolean menu2Avaiable = true;
		int bottomLine = 0;
		boolean lifeLine1Flag = true;
		boolean lifeLine2Flag = true;
		boolean timeOut = false;
		boolean isAnsweredCorrectly = false;

		for (int m = 0; m < 1; m++) {
			randomPart = r.nextInt(0, q);
			if (partArr[randomPart].isPlayed() == true) {
				m--;
			} else {
				partArr[randomPart].setPlayed(true);
			}
		}

		System.out.println("Contestant:" + partArr[randomPart].getName());
		counterParticipant++;
		if(partArr[randomPart].getBirthDate().getAge()<=30){             
			participantAge[0][0]+=1;         
			}          
		else if(partArr[randomPart].getBirthDate().getAge()>30&&partArr[randomPart].getBirthDate().getAge()<50){             
			participantAge[0][1]+=1;         
		}          
		else if(partArr[randomPart].getBirthDate().getAge()>=50){             
			participantAge[0][2]+=1;         
			}

		System.out.println("---------------------------------------");
		boolean gameOver = false;
		String strLifeLine1 = "Press 'F' to use the %50 .";
		String strLifeLine2 = "Press 'G' to use the Double Dip.";
		while (!gameOver) {
			answer = "";
			isAnsweredCorrectly=false;
			wordCloud(contentArr, stopWordArr);

			String difficultyLevel = "";
			switch (difficulty2) { // to read difficulty level in string array
			case 1:
				difficultyLevel = "1";
				break;
			case 2:
				difficultyLevel = "2";
				break;
			case 3:
				difficultyLevel = "3";
				break;
			case 4:
				difficultyLevel = "4";
				break;
			case 5:
				difficultyLevel = "5";
				break;
			}

			if (changeParticipant) {
				for (int m = 0; m < 1; m++) {
					randomPart = r.nextInt(0, q);
					if (partArr[randomPart].isPlayed() == true) {
						m--;
					} else {
						partArr[randomPart].setPlayed(true);
						lifeLine1Flag = true;
						lifeLine2Flag = true;
						timeOut=false;
						prize = 0;
						strLifeLine1 = "Press 'F' to use the %50 .";
						strLifeLine2 = "Press 'G' to use the Double Dip.";
					}
				}
				if(partArr[randomPart].getBirthDate().getAge()<=30){             
					participantAge[0][0]+=1;         
					}          
				else if(partArr[randomPart].getBirthDate().getAge()>30&&partArr[randomPart].getBirthDate().getAge()<50){             
					participantAge[0][1]+=1;         
				}          
				else if(partArr[randomPart].getBirthDate().getAge()>=50){             
					participantAge[0][2]+=1;         
					}

				System.out.println("Contestant:" + partArr[randomPart].getName());
				counterParticipant++;
				changeParticipant = false;

				System.out.println("---------------------------------------");
			}
			
			int wordCloudCounter = 0;
			String[] wordCloudArray = new String[12];
			System.out.println("Word Cloud:");
			while (wordCloudCounter <= 10){
				Random random = new Random();
				int k = random.nextInt(contentArr.length );
				if (questionArr[k].getDifficulty().equalsIgnoreCase(difficultyLevel) && questionArr[k].getIsShowed() == false) {// if the level of the word to be printed in
																			// word cloud is equal to the level of the
																			// next question
					boolean isPrinted = false;
					for(int u = 0 ;u < wordCloudCounter; u++) {
						if(wordCloudArray[u].equalsIgnoreCase(contentArr[k][8])) {
							isPrinted = true;
							break;
						}
					}
					if(isPrinted == false) {
						System.out.print(contentArr[k][8] + "   ");
						wordCloudArray[wordCloudCounter] = contentArr[k][8];
						wordCloudCounter++;
						bottomLine++;
						if (bottomLine % 5 == 0) // line control to print word cloud in a format
						{
							if (bottomLine % 2 != 0)
								System.out.print("   ");
							System.out.println("");
						
					}
					
					}
					if(wordCloudCounter >= 10) {
						break;
					}
				}
			}
			String selection;
			System.out.println("");
			Scanner inputSelection = new Scanner(System.in);		
			boolean isWordCloudTrue = false;
			while(true) {
				System.out.print("> Enter your selection: ");
				selection = inputSelection.nextLine();
				for(int u = 0; u< wordCloudArray.length; u++) {
					if(selection.equalsIgnoreCase(wordCloudArray[u])) {
						isWordCloudTrue = true;
						break;
					}
				}
				if(isWordCloudTrue) {
					break;
					
				}
				inputSelection.reset();
			}
			clearScreen();
			int k = 0;
			for (int p = 0; p < contentArr.length; p++) { // Checking whether the word the competitor
				if (questionArr[p].getDifficulty().equalsIgnoreCase(difficultyLevel)&& contentArr[p][8].equalsIgnoreCase(selection)) {// typed exists in the word cloud
					k = p;
					break;
				}
					
			}
					keypr=0;
					questionArr[k].display(questionArr[k], questionNumber);
					questionArr[k].setIsShowed();
					System.out.println("> Enter your choice: ");
					long time1, time2;
					int time = 20;
					time1 = System.currentTimeMillis();

					while (!(answer.equalsIgnoreCase("A") || answer.equalsIgnoreCase("B")
							|| answer.equalsIgnoreCase("C") || answer.equalsIgnoreCase("D")
							|| answer.equalsIgnoreCase("E") || answer.equalsIgnoreCase("1")
							|| answer.equalsIgnoreCase("2"))) {
						Thread.sleep(20);
						time2 = System.currentTimeMillis();
						cn.getTextWindow().setCursorPosition(40,6);
						System.out.println(strLifeLine1);
						cn.getTextWindow().setCursorPosition(40,7);
						System.out.println(strLifeLine2);
						cn.getTextWindow().setCursorPosition(40,5);
						System.out.println("Remainig Time = ");
						if (time2 - time1 >= 1000) {
							time1 = time2;
							time = time - 1;
							cn.getTextWindow().setCursorPosition(58,5);
							System.out.print(time + "   ");
							
						}
						if (keypr == 1) {

							if (rkey == KeyEvent.VK_A) {
								answer = "A";
							}
							if (rkey == KeyEvent.VK_B) {
								answer = "B";

							}
							if (rkey == KeyEvent.VK_C) {
								answer = "C";
							}
							if (rkey == KeyEvent.VK_D) {
								answer = "D";
							}
							if (rkey == KeyEvent.VK_E) {
								answer = "E";
							}							
							if (rkey == KeyEvent.VK_F) {
								answer = "1";
							}
							if (rkey == KeyEvent.VK_G) {
								answer = "2";
							}
							keypr = 0;
						}
						if(time <= 0) {
							timeOut = true;
							break;
						}
						if (lifeLine1Flag == false && answer.equalsIgnoreCase("1"))
							answer = "";
						else if (lifeLine2Flag == false && answer.equalsIgnoreCase("2"))
							answer = "";

					}
					cn.getTextWindow().setCursorPosition(0,15);
					System.out.println("ANSWER:" + answer);
							questionArr[k].getAnswer();

					if (questionArr[k].getAnswer().equalsIgnoreCase(answer)) { // Checking if the answer given by the
																			// competitor is correct
						System.out.println("Correct Answer!\n");
						partArr[randomPart].setTrueAnswers();
						isAnsweredCorrectly = true;
						if(partArr[randomPart].getBirthDate().getAge()<=30){                             
							participantAge[1][0]+=1;                         
							}                          
						else if(partArr[randomPart].getBirthDate().getAge()>30&&partArr[randomPart].getBirthDate().getAge()<50){
							participantAge[1][1]+=1;                         
							}                          
						else if(partArr[randomPart].getBirthDate().getAge()>=50){                             
							participantAge[1][2]+=1;                         
							}
						difficulty2++; // increasing the difficulty level for the next question
						questionNumber++;
						categoryTrueArr[categoryTrueIndex]=contentArr[k][0];
						categoryTrueIndex++;
					}
					if (answer.equals("1") && lifeLine1Flag == true) {
						String trueAnswer = questionArr[k].getAnswer();

						String[] options = { "A", "B", "C", "D" };
						String[] newOptions = new String[3];
						int b = 0;
						for (int a = 0; a < 4; a++) {
							if (!(options[a].equalsIgnoreCase(trueAnswer))) {
								newOptions[b] = options[a];
								b++;
							}
						}
						int wrongAnswer = r.nextInt(0, 3);
						int wrongAnswer2 = r.nextInt(0, 3);
						while (wrongAnswer2 == wrongAnswer) {
							wrongAnswer2 = r.nextInt(0, 3);
						}
						toNumberOption(questionArr[k], newOptions[wrongAnswer]);
						toNumberOption(questionArr[k], newOptions[wrongAnswer2]);
						questionArr[k].display(questionArr[k], questionNumber);
						System.out.print("> Enter your choice: ");
						while (true) {
							Thread.sleep(20);
							time2 = System.currentTimeMillis();
							if (time2 - time1 >= 1000) {
								time1 = time2;
								time = time - 1;
								cn.getTextWindow().setCursorPosition(58,5);
								System.out.print(time + "   " );
								
							}
							if (keypr == 1) {

								if (rkey == KeyEvent.VK_A) {
									answer = "A";
								}
								if (rkey == KeyEvent.VK_B) {
									answer = "B";

								}
								if (rkey == KeyEvent.VK_C) {
									answer = "C";
								}
								if (rkey == KeyEvent.VK_D) {
									answer = "D";
								}
								if (rkey == KeyEvent.VK_E) {
									answer = "E";
								}
								if (rkey == KeyEvent.VK_G) {
									answer = "2";
								}
								keypr = 0;
							}
							if(time <= 0) {
								timeOut = true;
								break;
							}
							if ((answer.equalsIgnoreCase("A") || answer.equalsIgnoreCase("B")
									|| answer.equalsIgnoreCase("C") || answer.equalsIgnoreCase("D")
									|| answer.equalsIgnoreCase("2"))
									&& !(toGetOption(questionArr[k], answer).equalsIgnoreCase(""))) {

								if (questionArr[k].getAnswer().equalsIgnoreCase(answer)) { // Checking if the answer given
																						// by the competitor is correct
									cn.getTextWindow().setCursorPosition(0,75);
									System.out.println("Correct Answer!\n");
									isAnsweredCorrectly = true;
									partArr[randomPart].setTrueAnswers();
									if(partArr[randomPart].getBirthDate().getAge()<=30){                             
										participantAge[1][0]+=1;                         
										}                          
									else if(partArr[randomPart].getBirthDate().getAge()>30&&partArr[randomPart].getBirthDate().getAge()<50){
										participantAge[1][1]+=1;                         
										}                          
									else if(partArr[randomPart].getBirthDate().getAge()>=50){                             
										participantAge[1][2]+=1;                         
										}
									difficulty2++; // increasing the difficulty level for the next question
									questionNumber++;
									categoryTrueArr[categoryTrueIndex]=contentArr[k][0];
									categoryTrueIndex++;
								}
								strLifeLine1 = "The 50% lifeline was used.";
								lifeLine1Flag = false;
								break;
							}
						}
						cn.getTextWindow().setCursorPosition(0,75);

					}
					if (answer.equals("2") && lifeLine2Flag == true) {
						int y = 0;
						System.out.print("> Enter your choice: ");
						while (y < 2) {
							while (true) {
								time2 = System.currentTimeMillis();
								if (time2 - time1 >= 1000) {
									time1 = time2;
									time = time - 1;
									cn.getTextWindow().setCursorPosition(40,5);
									System.out.println("Remainig Time = ");
									cn.getTextWindow().setCursorPosition(58,5);
									System.out.print(time + "   " );
									
								}
								if (keypr == 1) {

									if (rkey == KeyEvent.VK_A) {
										if(questionArr[k].getFirstOption()!= "") {
											answer = "A";
											break;	
										}
										
									}
									if (rkey == KeyEvent.VK_B) {
										if(questionArr[k].getSecondOption()!= "") {
											answer = "B";
											break;	
										}

									}
									if (rkey == KeyEvent.VK_C) {
										if(questionArr[k].getThirdOption()!= "") {
											answer = "C";
											break;	
										}
									}
									if (rkey == KeyEvent.VK_D) {
										if(questionArr[k].getFourthOption()!= "") {
											answer = "D";
											break;	
										}
									}
									if (rkey == KeyEvent.VK_E) {
										answer = "E";
									}
									keypr = 0;
								}
								if(time <= 0) {
									timeOut = true;
									break;
								}
							}
							if(time <= 0) {
								timeOut = true;
								break;
							}
							if (questionArr[k].getAnswer().equalsIgnoreCase(answer)) { // Checking if the answer given by
																					// the competitor is correct
								cn.getTextWindow().setCursorPosition(0,75);
								System.out.println("Correct Answer!\n");
								partArr[randomPart].setTrueAnswers();
								isAnsweredCorrectly = true;
								if(partArr[randomPart].getBirthDate().getAge()<=30){                             
									participantAge[1][0]+=1;                         
									}                          
								else if(partArr[randomPart].getBirthDate().getAge()>30&&partArr[randomPart].getBirthDate().getAge()<50){
									participantAge[1][1]+=1;                         
									}                          
								else if(partArr[randomPart].getBirthDate().getAge()>=50){                             
									participantAge[1][2]+=1;                         
									}
								difficulty2++; // increasing the difficulty level for the next question
								questionNumber++;
								categoryTrueArr[categoryTrueIndex]=contentArr[k][0];
								categoryTrueIndex++;
								lifeLine2Flag = false;
								break;
							} else if ((answer.equalsIgnoreCase("A") || answer.equalsIgnoreCase("B")
									|| answer.equalsIgnoreCase("C") || answer.equalsIgnoreCase("D"))
									&& !(toGetOption(questionArr[k], answer).equalsIgnoreCase(""))) {
								clearScreen();
								toNumberOption(questionArr[k], answer);
								questionArr[k].display(questionArr[k], questionNumber);
								System.out.print("> Enter your choice: ");
								keypr = 0;
								y++;
							}
							lifeLine2Flag = false;
							strLifeLine2 = "The Double Dip lifeline was used.";
							
						}
						cn.getTextWindow().setCursorPosition(0,55);
					}
					if (answer.equals("E")) {
						switch (partArr[randomPart].getTrueAnswers()) {
						case 1:
							prize = 20000;
							break;
						case 2:
							prize = 100000;
							break;
						case 3:
							prize = 250000;
							break;
						case 4:
							prize = 500000;
							break;
						case 5:
							prize = 1000000;
							break;
						}
						partArr[randomPart].setPrize(prize);
						System.out.println("Number of correct answers: " + partArr[randomPart].getTrueAnswers());
						System.out.println("Prize: " + partArr[randomPart].getPrize());
						gameOver = true;
					}
					// !
					if (((!(questionArr[k].getAnswer().equalsIgnoreCase(answer))) && !(answer.equalsIgnoreCase("E"))) || timeOut == true) {
						clearScreen();
						System.out.println("Wrong answer, GAME OVER!\n");
						categoryFalseArr[categoryFalseIndex]=contentArr[k][0];
						categoryFalseIndex++;
						if (partArr[randomPart].getTrueAnswers() == (1) || partArr[randomPart].getTrueAnswers() == (0)) {
							prize = 0000;
						} else if (partArr[randomPart].getTrueAnswers() == (2) || partArr[randomPart].getTrueAnswers() == 3) {
							prize = 100000;
						} else if (partArr[randomPart].getTrueAnswers() == (4)) {
							prize = 500000;
						} else {
							prize = 1000000;
						}
						partArr[randomPart].setPrize(prize);
						System.out.println("Number of correct answers: " + partArr[randomPart].getTrueAnswers());
						System.out.println("Prize: " + partArr[randomPart].getPrize());
						gameOver = true;
					}
					if(questionArr[k].getDifficulty().equalsIgnoreCase("5") && (questionArr[k].getAnswer().equalsIgnoreCase(answer))) {
						clearScreen();
						System.out.println("Congratulations, you are a millionaire now!\n");
						categoryFalseArr[categoryFalseIndex]=contentArr[k][0];
						categoryFalseIndex++;
						prize = 1000000;
						partArr[randomPart].setPrize(prize);
						System.out.println("Prize: " + partArr[randomPart].getPrize());
						System.out.println("Number of correct answers: 5");
						gameOver = true;
					}

					if (questionNumber > 5) {
						gameOver = true;
					}
					String str = partArr[randomPart].getParticipantId() + " " + questionArr[k].getQuestionId() + " " + isAnsweredCorrectly + "\n"; 
					File file = new File("answershistory.txt"); 
					if (!file.exists()) { 
						file.createNewFile(); 
					} 
					FileWriter fileWriter = new FileWriter(file, true); 
					BufferedWriter bWriter = new BufferedWriter(fileWriter); 
					bWriter.write(str); 
					bWriter.close();

					if (gameOver == true) {
						if (counterParticipant == q && !(questionArr[k].getAnswer().equalsIgnoreCase(answer))) {
							menu2Avaiable = false;
							break;
						}
						System.out.println("Next contestant? (yes/no)");
						Scanner input7 = new Scanner(System.in);
						String playAgain = input7.nextLine();
						if (playAgain.equalsIgnoreCase("no"))
							break;
						else { // if the competitor wants to play again, the level and number of questions are
								// reset
							changeParticipant = true;
							gameOver = false;
							difficulty2 = 1;
							questionNumber = 1;
							System.out.println("");
						}

					}
					
		}
	}

	public void toNumberOption(Question question, String answer) {
		switch (answer) {
		case "A":
			question.setFirstOption("");
			break;
		case "B":
			question.setSecondOption("");
			break;
		case "C":
			question.setThirdOption("");
			break;
		case "D":
			question.setFourthOption("");
			break;
		case "a":
			question.setFirstOption("");
			break;
		case "b":
			question.setSecondOption("");
			break;
		case "c":
			question.setThirdOption("");
			break;
		case "d":
			question.setFourthOption("");
			break;
		}
	}

	public String toGetOption(Question question, String answer) {
		String option = "";
		switch (answer) {
		case "A":
		case "a":
			option = question.getFirstOption();
			break;
		case "B":
		case "b":
			option = question.getSecondOption();
			break;
		case "C":
		case "c":
			option = question.getThirdOption();
			break;
		case "D":
		case "d":
			option = question.getFourthOption();
			break;
		case "2":
			option = " ";
			break;
		}
		return option;
	}

	public void clearScreen() {
		   char[] buffer = new char[Math.max(0, cn.getTextWindow().getColumns() * cn.getTextWindow().getRows() - 1)];
		   Arrays.fill(buffer, ' ');
		   cn.getTextWindow().setCursorPosition(0, 0);
		   cn.getTextWindow().output(buffer, 0, buffer.length);
		   cn.getTextWindow().output(cn.getTextWindow().getColumns() - 1, cn.getTextWindow().getRows() - 1, ' ');
		   cn.getTextWindow().setCursorPosition(0, 0);
	   }

	public double[] averageAge(int[][] participantAge){         
		double[] averageArr= new double[3];         
		if(participantAge[0][0]==0){             
			averageArr[0]=0;        
			}          
		if(participantAge[0][1]==0){    
			averageArr[1]=0;  
			}         
		if(participantAge[0][2]==0){             
			averageArr[2]=0;        
			}
		if(participantAge[0][0]!= 0) {
			averageArr[0]=(double)participantAge[1][0]/participantAge[0][0];
		}
		else {
			averageArr[0]=0;
		}
		if(participantAge[0][1]!= 0) {
			averageArr[1]=(double)participantAge[1][1]/participantAge[0][1];
		}
		else {
			averageArr[1]=0;
		}
		if(participantAge[0][2]!= 0){
			averageArr[2]=(double)participantAge[1][2]/participantAge[0][2];
		}
		else {
			averageArr[2]=0;
		}         
		return averageArr;       
	
	}

	public int parseIntOrNull(String value) {
	    try {
	        return Integer.parseInt(value);
	    } catch (NumberFormatException e) {
	        return 5;
	    }
	}

}