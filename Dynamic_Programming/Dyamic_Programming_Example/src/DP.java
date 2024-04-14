import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DP {

	public static int DP(int n, int p , int c, int playersSalary[], int yearlyPlayerDemand[] ) {
		
		int totalDemand = 0;
		for(int i = 0 ; i < n; i++) {
			totalDemand = totalDemand + yearlyPlayerDemand[i];
		}
		
		int DynamicMatrix[][] = new int[n + 1][totalDemand + 1];
		String traceBack[][] = new String [n + 1][totalDemand + 1];
		DynamicMatrix[0][0] = 0;
		// The array holding the players' salary is embedded in the first row of the main matrix.
		for(int i = 1; i < DynamicMatrix[0].length; i++) {
			DynamicMatrix[0][i] =  playersSalary[i - 1];
		}
		
		// The loop that iterates all the years one by one
		for(int i = 1; i < DynamicMatrix.length; i++) {
			for(int j = 0; j < DynamicMatrix[0].length; j++) { // Loop that iterates all player numbers that can be produced extra in the current year
				int demand = yearlyPlayerDemand[i - 1]; 
				int extraPlayerCount = ((demand + j) - p);
				int min = Integer.MAX_VALUE;
				
				// If the number of players requested is more than the number of players we can produce without paying the coach cost, this section is entered.
				if(demand > p) { 
					String bestStatus = "";
						for(int k = 0; k <= extraPlayerCount; k++) { // The loop where all possibilities are tried according to the number of players we received from the previous year
							int producedPlayerCount = extraPlayerCount - k;
							int preProducedPlayerCount = k;
							
							if(k <= totalDemand) { 
								int value = DynamicMatrix[i - 1][preProducedPlayerCount] + (producedPlayerCount * c); // The cost is calculated by adding the cost of the previous year and the coach cost for the extra players we will produce this year.
								if(value < min) { // The probability with the lowest cost is selected
									min = value;
									bestStatus = producedPlayerCount + " " + k;
								}
									
							}
						}
						
						if(j == 0) { // If we are not going to produce extra players, the cost is calculated without adding salary cost
							DynamicMatrix[i][j] = min;
						}
						else { // If we are going to produce extra players, the cost is calculated by adding the salary cost
							DynamicMatrix[i][j] = min + playersSalary[j - 1];
						}
						traceBack[i][j] = bestStatus; // In order to be able to return by following the steps, the best choices for each step are written to the traceBack matrix.
					
				}
				// // If the number of players requested is less than the number of players we can produce without paying the coach cost, this section is entered.
				else {
					String bestStatusElse = "";
					if(j == 0) { // If we are not going to produce extra players, the cost of the previous year is assigned to the cost of this year.
						DynamicMatrix[i][j] = DynamicMatrix[i - 1][j];
						traceBack[i][j] = j + " " + 0;
					}
					else if((j != 0) && (demand + j) < p) { // If we are going to produce extra players, but the total number of players we will produce is less than the number of players we can produce without paying a coach cost, this block is entered.
						DynamicMatrix[i][j] = DynamicMatrix[i - 1][0] + playersSalary[j - 1];
						traceBack[i][j] = j + " " + 0;
					}
					else { // If we are going to produce extra players and the total number of players we will produce is more than the number of players we can produce without paying the coach cost, this block is entered.
						for(int k = 0; k <= extraPlayerCount; k++) { // The loop where all possibilities are tried according to the number of players we received from the previous year
							int producedPlayerCount = extraPlayerCount - k;
							int preProducedPlayerCount = k;
							int value = DynamicMatrix[i - 1][preProducedPlayerCount] + (producedPlayerCount * c);
							
							if(value < min) {
								min = value;
								bestStatusElse = producedPlayerCount + " " + k;
							}
						}
						DynamicMatrix[i][j] = min + playersSalary[j - 1];
						traceBack[i][j] = bestStatusElse;
					}
				}
				
				
			}
			
		}
	
		// To see all elements of the Matrix, it is needed to take out this loop from the command line
		/*
		for(int i= 0; i< DynamicMatrix.length; i++) {
			for(int j = 0; j <DynamicMatrix[0].length; j++)
				System.out.print(DynamicMatrix[i][j] + " ");
			System.out.println("");
		}
		*/
		
		String traceLine = traceBack[traceBack.length-1][0];
		String traceLineSplitted[] = traceLine.split(" ");
		
		int index = 0;
		for(int i = traceBack.length-2 ; i >= 0; i--) { // Using the traceBack matrix, how many extra players should be produced each year and how many players should be recruited from the previous year are printed on the screen.
			index = Integer.parseInt(traceLineSplitted[1]);
			System.out.println("Year:" + (i + 1)  + "   " + "Extra players to be produced this year: " +traceLineSplitted[0] + "   " +"Players from the previous year: " + traceLineSplitted[1]);
			if(i != 0) {
				traceLine = traceBack[i][index];
				traceLineSplitted = traceLine.split(" ");
			}
			
		}
		
		// The total minimum cost is returned.
		int cost = DynamicMatrix[n][0];
		return cost;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=3, p=5, c=5;
		int yearlyPlayerDemand[] = new int[0];
		int playersSalary[] = new int[0];
		
		try {
		      File demandTxt = new File("C:\\Users\\Ozan\\Desktop\\yearly_player_demand.txt");
		      Scanner demandReader = new Scanner(demandTxt);
		      Scanner demandLineCounter = new Scanner(demandTxt);
		      int demandLineCount = 0;
		      
		      while (demandLineCounter.hasNextLine()) {
		    	  demandLineCount++;
		    	  demandLineCounter.nextLine();
		      }
		    	  
		      yearlyPlayerDemand= new int[demandLineCount - 1];
		      demandLineCounter.close();
		      
		      demandReader.nextLine();
		      int i = 0;
		      while (demandReader.hasNextLine()) {
		        String data = demandReader.nextLine();
		        String demandData[] = data.split("	");
		        yearlyPlayerDemand[i] = Integer.parseInt(demandData[1]);
		        i++;
		      }
		      demandReader.close();
		    } 
		catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		try {
		      File demandTxt = new File("C:\\Users\\Ozan\\Desktop\\players_salary.txt");
		      Scanner salaryReader = new Scanner(demandTxt);
		      Scanner salaryLineCounter = new Scanner(demandTxt);
		      int salaryLineCount = 0;
		      
		      while (salaryLineCounter.hasNextLine()) {
		    	  salaryLineCount++;
		    	  salaryLineCounter.nextLine();
		      }
		    	  
		      playersSalary= new int[salaryLineCount - 1];
		      salaryLineCounter.close();
		      
		      salaryReader.nextLine();
		      int j = 0;
		      while (salaryReader.hasNextLine()) {
		        String sdata = salaryReader.nextLine();
		        String salaryData[] = sdata.split("	");
		        playersSalary[j] = Integer.parseInt(salaryData[1]);
		        j++;
		      }
		      salaryReader.close();
		    } 
		catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		
		int cost = DP(n, p, c, playersSalary, yearlyPlayerDemand);
		System.out.println("Total Cost = " + cost);

	}

}
