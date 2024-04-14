import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ozan_Kalkan_2020510044 {
	
	public static int Greedy(int n, int p , int c, int playersSalary[], int yearlyPlayerDemand[]) {
		int cost = 0;
		int demand = Integer.MAX_VALUE;
		int nextYearDemand = Integer.MAX_VALUE;
		int player = 0;
		int playerForNextYear = 0;
		
		// The loop that iterates all the years one by one
		for (int i = 1; i <= n; i++) {
			demand = yearlyPlayerDemand[i];
			demand = demand - playerForNextYear; // The current demand is found by subtracting the number of players from the previous year from the current year's demand.
			playerForNextYear = 0;
			
			// If the demand is less than the number of players we can produce for free, this section is entered.
			if(demand < p){
				
				if(i < (n - 1)) {
					nextYearDemand = yearlyPlayerDemand[i + 1];
				}
				// The demand for the next year is checked, if the demand is more than the number of players we can produce for free, this section is entered.
				if(nextYearDemand > p) {
					int value = Integer.MAX_VALUE;
					int min = Integer.MAX_VALUE;
					int extra = p - demand; // The number of players we can produce for free after producing this year's demand.
					
					if((nextYearDemand - p) < extra) // If the number of players we can produce extra is more than the next year's demand, the maximum number of players we will produce extra will be the same as the next year's demand.
						extra = nextYearDemand - p;
					// All possibilities of the number of players we can produce extra from 0 to the maximum are tested and the best situation is selected.
					for(int j = 0; j <= extra; j++) { 
						int producedPlayerCount = extra - j;
						value = playersSalary[j] + (producedPlayerCount * c);
						
						if(value < min) {
							min = value;
							playerForNextYear = j;
						}
					 }
				
					if(playerForNextYear > nextYearDemand - p)
						playerForNextYear = (nextYearDemand - p);
					
					player = demand + playerForNextYear; // By adding up the demand for this year and the number of players to be produced for the next year, it is found out how many players should be produced in the current year.
					cost = cost + (playersSalary[playerForNextYear]);
				}
				else { // If the next year's demand is less than the number of players we can produce for free, this section is entered
					player = demand; // Players are produced as much as this year's demand.
					playerForNextYear = 0; // Players cannot be produced for the next year
					cost = cost;
				}
			}
			else // If the demand is more than the number of players we can produce for free, this section is entered.
			{
				player = demand; // Players are produced as much as this year's demand.
				playerForNextYear = 0; // Players cannot be produced for the next year
				cost = cost + ((player - p) * c); // Coach cost is paid for extra produced players
			}
			
			if(player - p > 0)
				System.out.println("Year " + (i) + " - " + "Number of players to be paid Coach Cost for this year: " + (player - p) + " - Players for next year: " + playerForNextYear);
			else
				System.out.println("Year " + (i) + " - " + "Number of players to be paid Coach Cost for this year: " + "0" + " - Players for next year: " + playerForNextYear);
			
		}
		
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
		    	  
		      yearlyPlayerDemand= new int[demandLineCount];
		      demandLineCounter.close();
		      yearlyPlayerDemand[0] = 0;
		      demandReader.nextLine();
		      int i = 1;
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
		    	  
		      playersSalary= new int[salaryLineCount];
		      salaryLineCounter.close();
		      playersSalary[0] = 0;
		      salaryReader.nextLine();
		      int j = 1;
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
		
		
		int cost = Greedy(n, p, c, playersSalary, yearlyPlayerDemand);
		System.out.println("Total Cost = " + cost);
		

	}

}
