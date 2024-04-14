using System;

namespace Homework_2
{
    class Program
    {
        static void Main(string[] args)
        {
            int score, score1, score2, i, j, k, turn, line, b;
            bool capacity, stop, tie;
            string winner;
            string[] A1 = new string[15];
            string[] A2 = new string[15];
            string[] A3 = new string[15];
            string[] names = {"Derya", "Elife", "Fatih", "Ali", "Azra", "Sibel", "Cem", "Nazan", "Mehmet", "Nil", "Can", "Tarkan"};
            int[] scores = { 100, 100, 95, 90, 85, 80, 80, 70, 55, 50, 30, 30 };
            string[] letters = { "D", "E", "U" };
            string[] newnames = new string[names.Length + 1];
            int[] newscores = new int[scores.Length + 1];
            score1 = 120;
            score2 = 120;
            score = 0;
            i = 0;
            j = 0;
            k = 0;
            b = 0;
            turn = 0;
            capacity = false;
            line = 0;
            stop = false;
            tie = false;
            
            
            while (stop == false)
            {
                if (turn % 2 == 0) // Determines the players turn
                    Console.WriteLine("Player1's turn"); 
                else
                    Console.WriteLine("Player2's turn");
                if (turn % 2 == 0) // Decreases players' points in turn
                    score1 = score1 - 5;
                else
                    score2 = score2 - 5;
                Console.WriteLine("Player1: " + score1 + " points" + "   Player2: " + score2 + " points");

                while (capacity == false) // Loop that fills all arrays if the game doesn't end
                {
                    Random randomline = new Random();
                    line = randomline.Next(1, 4); // Randomly determines the line (A1, A2, A3)

                    Random randomletters = new Random();
                    int letternumber = randomletters.Next(0, 3); // Randomly picks the letter number and the letters are determined by their number (D, E, U)

                    if (line == 1 && i < 15) // If line is 1, letter is added to first line array
                    {
                        A1[i] = letters[letternumber];
                        i = i + 1;
                        capacity = true;
                    }
                    else if (line == 2 && j < 15) // If line is 2, letter is added to second line array
                    {
                        A2[j] = letters[letternumber];
                        j = j + 1;
                        capacity = true;
                    }
                    else if (line == 3 && k < 15) // If line is 3, letter is added to third line array
                    {
                        A3[k] = letters[letternumber];
                        k = k + 1;
                        capacity = true;
                    }
                    else
                        capacity = false; // Provides adding to another array if the added array is full
                }

                
                Console.WriteLine("---------------------------------");
                Console.Write("A1: ");
                for (int n = 0; n < A1.Length; n++) // Prints the first array
                    Console.Write(A1[n] + " ");
                Console.WriteLine(" ");
                
                
                Console.Write("A2: ");
                for (int n = 0; n < A2.Length; n++) // Prints the second array
                    Console.Write(A2[n]+ " ");
                Console.WriteLine(" ");
                
                
                Console.Write("A3: ");
                for (int n = 0; n < A3.Length; n++) // Prints the third array
                    Console.Write(A3[n] + " ");
                Console.WriteLine(" ");
                
                
                Console.WriteLine("---------------------------------");
                Console.WriteLine(" ");
                if(line == 1) // Checks for "DEU" combinations that can occur by adding letter to the first line
                {
                    if(A1[i - 1] == "D")
                    {
                        if (i >= 3 && A1[i - 2] == "E" && A1[i - 3] == "U")
                            stop = true;
                        else if (i >= 3 && A2[i - 2] == "E" && A3[i - 3] == "U")
                            stop = true;
                        else if (i >= 1 && A2[i - 1] == "E" && A3[i - 1] == "U")
                            stop = true;
                        else if (i < 14 && A2[i] == "E" && A3[i + 1] == "U")
                            stop = true;
                    }

                    else if(A1[i - 1] =="U")
                    {

                        if (i >= 3 && A1[i - 2] == "E" && A1[i - 3] == "D")
                            stop = true;
                        else if (i >= 3 && A2[i - 2] == "E" && A3[i - 3] == "D")
                            stop = true;
                        else if (i >= 1 && A2[i - 1] == "E" && A3[i - 1] == "D")
                            stop = true;
                        else if (i < 14 && A2[i] == "E" && A3[i + 1] == "D")
                            stop = true;    
                    }
                }
                else if(line == 2) // Checks for "DEU" combinations that can occur by adding letter to the second line
                {
                    if (A2[j - 1] == "D")
                    {

                        if (j >= 3 && A2[j - 2] == "E" && A2[j - 3] == "U")
                            stop = true;  
                    }
                    else if (A2[j - 1] == "E")
                    {

                        if (j >= 2 && j < 15 && A1[j] == "D" && A3[j - 2] == "U")
                            stop = true;
                        else if (j >= 2 && j < 15 && A1[j - 2] == "U" && A3[j] == "D")
                            stop = true;
                        else if (j >= 2 && j < 15 && A1[j] == "U" && A3[j - 2] == "D")
                            stop = true;
                        else if (j >= 2 && j < 15 && A1[j - 2] == "D" && A3[j] == "U")
                            stop = true;
                        else if (j >= 1 && A1[j - 1] == "D" && A3[j - 1] == "U")
                            stop = true;
                        else if (j >= 1 && A1[j - 1] == "U" && A3[j - 1] == "D")
                            stop = true;
                    }
                    else if (A2[j - 1] == "U")
                    {

                        if (j >= 3 && A2[j - 3] == "D" && A2[j - 2] == "E")
                            stop = true; 
                    }
                }

                else if (line == 3) // Checks for "DEU" combinations that can occur by adding letter to the third line
                {
                    if (A3[k - 1] == "D")
                    {
                        if (k >= 3 && A3[k - 2] == "E" && A3[k - 3] == "U")
                            stop = true;
                        else if (k >= 3 && A2[k - 2] == "E" && A1[k - 3] == "U")
                            stop = true;
                        else if (k >=1 && A2[k - 1] == "E" && A1[k - 1] == "U" )
                                stop = true;
                        else if (k < 14 && A2[k] == "E" && A1[k + 1] == "U")
                            stop = true;
                    }
                    else if (A3[k - 1] == "U")
                    {
                        if (k >= 3 && A3[k - 2] == "E" && A3[k - 3] == "D")
                            stop = true;
                        else if (k >= 3 && A2[k - 2] == "E" && A1[k - 3] == "D")
                            stop = true;
                        else if (k >=1 && A2[k - 1] == "E" && A1[k - 1] == "D")
                            stop = true;
                        else if (k < 14 && A2[k] == "E" && A1[k + 1] == "D")
                            stop = true;
                    }
                }
                turn++;
                capacity = false;
                if (turn >= 45 && stop == false) // It provides to end the game in a draw if no DEU combination occurs and all arrays are full.
                {
                    stop = true;
                    tie = true;
                }
                Console.ReadKey(true); // Provides all steps to be displayed in order
            }

            if (tie == true) // If the game is a draw it prints "Tie" and shows the default scoreboard
            {
                Console.WriteLine(" ! Tie ! ");
                Console.WriteLine("");
                Console.WriteLine("Names" + " \t \t" + "Scores");
                Console.WriteLine("-----" + "\t \t" + "------");
                for (int v = 0; v < names.Length; v++)
                {
                    Console.Write(names[v] + "\t \t");
                    Console.WriteLine(scores[v]);
                }

            }
            else // This part shows the winner and the new scoreboard
            {
                if (turn % 2 == 0)
                {
                    Console.WriteLine("!!! Player2 Wins !!!");
                    Console.WriteLine("Player2 = " + score2 + " points");
                    score = score2;
                    winner = "Player2";
                }
                else
                {
                    Console.WriteLine("!!! Player1 Wins !!!");
                    Console.WriteLine("Player1 = " + score1 + " points");
                    score = score1;
                    winner = "Player1";
                }

                for (int a = 0; score <= scores[a]; a++) // Determines the rank of the winner on the scoreboard
                {
                    b = a + 1;
                    if (a >= scores.Length - 1)
                        break;
                }

                for (int q = 0; q <= names.Length; q++) // Creates an array of names of the new scoreboard
                {
                    if (q < b)
                        newnames[q] = names[q];
                    else if (q == b)
                        newnames[q] = winner;
                    else
                        newnames[q] = names[q - 1];
                }
                for (int s = 0; s <= scores.Length; s++) // Creates an array of scores of the new scoreboard
                {
                    if (s < b)
                        newscores[s] = scores[s];
                    else if (s == b)
                        newscores[s] = score;
                    else
                        newscores[s] = scores[s - 1];
                }
                Console.WriteLine("");
                Console.WriteLine("Names" + " \t \t" + "Scores");
                Console.WriteLine("-----" + "\t \t" + "------");
                for (int c = 0; c < newnames.Length; c++) // Prints the new scoreboard
                {
                    Console.Write(newnames[c] + "\t \t");
                    Console.WriteLine(newscores[c]);
                }
            }    
        }
    }
}
