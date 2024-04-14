using System;

namespace Homework_3
{
    class Program
    {
        static void Main(string[] args)
        {
            string text, pattern, controltext, patternwithoutstar;
            char control, controlpattern;
            bool controlbool = true; bool patternbool = true; bool space = true; bool charactercontrol = true; bool samewords = true; bool contains = true; bool starcontrol = true; bool lastchar = true; bool star = true; bool samewords2 = true;
            int starcounter = 0; int linecounter = 0;
            string[] words;
            string[] lastwords;
            string[] separatedpattern;
            int n = 0; int a = 0; int b = 0; int c = 0;


            do
            {
                Console.Write("Please, enter a text: ");
                text = Console.ReadLine();

                for (int i = 0; i < text.Length; i++)
                {
                    control = text[i];
                    if (!((65<= Convert.ToInt32(control) && Convert.ToInt32(control)<= 90) || (97 <= Convert.ToInt32(control) && Convert.ToInt32(control) <= 122) || Convert.ToInt32(control) == 44 || Convert.ToInt32(control) == 46 || Convert.ToInt32(control) == 32)) // Characters in text are checked against their equivalents in the ASCII Table
                    {
                        controlbool = false;
                        Console.WriteLine("You entered wrong text. Please press any key to enter new text");
                        Console.ReadKey();
                        break;
                    }
                    else
                        controlbool = true;
                }
                if (controlbool == false)
                    Console.Clear();
            } while (!controlbool); // This part checks whether correct and true text is received from the user.

            do
            {
                Console.Write("Please, enter a pattern: ");
                pattern = Console.ReadLine();
                for (int i = 0; i < pattern.Length; i++)
                {
                    controlpattern = pattern[i];
                    if (!((65 <= Convert.ToInt32(controlpattern) && Convert.ToInt32(controlpattern) <= 90) || (97 <= Convert.ToInt32(controlpattern) && Convert.ToInt32(controlpattern) <= 122) || Convert.ToInt32(controlpattern) == 42 || Convert.ToInt32(controlpattern) == 45)) // Characters in pattern are checked against their equivalents in the ASCII Table
                    {
                        patternbool = false;
                        Console.WriteLine("You entered wrong pattern. Please press any key to enter new pattern");
                        Console.ReadKey();
                        break;
                    }
                    else if (Convert.ToInt32(controlpattern) == 42) // This part counts the number of stars in the pattern
                    {
                        starcounter++;
                        patternbool = true;
                    }
                    else if (Convert.ToInt32(controlpattern) == 45) // This part counts the number of lines in the pattern
                    {
                        linecounter++;
                        patternbool = true;
                    }
                    else
                        patternbool = true;

                    if (starcounter > 0 && linecounter > 0) // Pattern is considered incorrect if line and star are included at the same time
                    {
                        patternbool = false;
                        Console.WriteLine("You entered wrong pattern. Please press any key to enter new pattern");
                        Console.ReadKey();
                        break;
                    }
                }
                if (starcounter == 0 && linecounter == 0) // if the pattern does not have any of the lines and stars, the pattern is considered incorrect.
                {
                    if (patternbool == true)
                    {
                        Console.WriteLine("You entered wrong pattern. Please press any key to enter new pattern");
                        Console.ReadKey();
                    }
                    Console.Clear();
                    patternbool = false;
                }
                if (patternbool == false)
                {
                    Console.Clear();
                    linecounter = 0;
                    starcounter = 0;
                }
                
            } while(!patternbool); // This part checks whether correct and true pattern is received from the user.

            // This part converts text and pattern to controllable
            pattern = pattern.ToLower();
            controltext = text;
            controltext = controltext.Replace(",", " "); 
            controltext = controltext.Replace(".", " ");
            if (controltext.Contains("  ")) // if the text contains two adjacent spaces, the loop continues until all the two adjacent spaces are converted to single spaces
            {
                while (space)
                {
                    controltext = controltext.Replace("  ", " ");
                    if (controltext.Contains("  "))
                        space = true;
                    else
                        space = false;
                }
            }
            lastwords = controltext.Split(' '); // A new array holding all the words to print the same as the originally entered words
            controltext = controltext.ToLower();
            words = controltext.Split(' ');

            if (linecounter > 0) // if the pattern contains a line or lines, it enters this part
            {
                for (int i = 0; i < words.Length; i++)
                {
                    if(words[i].Length == pattern.Length) // The words whose length is the same as the pattern are compared with the pattern and the same words with the pattern are written
                    {
                        for (int j = 0; j < pattern.Length; j++)
                        {
                            if(pattern[j] != '-')
                            {
                                if(words[i][j] != pattern[j])
                                {
                                    charactercontrol = false;
                                    break;
                                }
                            }
                            if (charactercontrol == false)
                                break;
                        }
                        if (charactercontrol == true)
                        {
                            for (int k = 0; k < i; k++) // A loop that ensures that the same text is not printed again if it was printed earlier
                            {
                                if (lastwords[i] != lastwords[k])
                                    samewords = true;
                                else
                                {
                                    samewords = false;
                                    break;
                                }
                                    
                            }
                            if (samewords == true)
                                Console.WriteLine(lastwords[i]);
                        }
                    }
                    charactercontrol = true;
                }

            }

            else if(starcounter > 0) // if the pattern contains a star or stars, it enters this part
            {
                if(pattern.Contains("**")) // Since the meaning of two stars next to each other and one star is the same, all the stars next to each other are converted into one star
                {
                    while(star)
                    {
                        pattern = pattern.Replace("**", "*");
                        if (pattern.Contains("**"))
                            star = true;
                        else
                            star = false;
                    }
                }
                separatedpattern = pattern.Split('*');
                patternwithoutstar = pattern.Replace("*", "");  

                for (int i = 0; i < words.Length; i++) // This part checks whether all the elements in the pattern are in the word
                {
                    for (int j = 0; j < separatedpattern.Length; j++)
                    {
                        if (words[i].Contains(separatedpattern[j]))
                        {
                            contains = true;
                        }
                        else
                        {
                            contains = false;
                            break;
                        }
                    }
                    if (contains == true)
                    {
                        while (starcontrol == true)
                        {
                            if (patternwithoutstar == words[i]) // When the stars in the pattern are removed, the words that are the same as the pattern are selected. For example, cal*led or called*
                                starcontrol = true;
                            else
                            {
                                for (int k = 0; k < pattern.Length; k++)
                                {
                                    if ((pattern[k] != '*') && (k + a <= words[i].Length - 1)) // if the character in the pattern is not an star, it must be the same as the index corresponding to that character in the word.
                                    {
                                        if (pattern[k] == words[i][k + a])
                                        {
                                            starcontrol = true;
                                        }
                                        else
                                        {
                                            starcontrol = false;
                                            break;
                                        }
                                    }
                                    else if ((pattern[k] == '*') && k != (pattern.Length - 1) && (k + a + 1 <= words[i].Length - 1)) // if the character in the pattern is an star and not the last character, the loop continues until the character after the star and the character in the word are equal.
                                    {
                                        while (!(pattern[k + 1] == words[i][(k + 1) + a + b]))
                                        {

                                            if ((((k + 1) + a + b) == words[i].Length - 1) && (pattern[k + 1] != words[i][(k + 1) + a + b])) // The part that ends the loop if it is checked until the last character and no equality is found
                                            {
                                                lastchar = false;
                                                starcontrol = false;
                                                break;
                                            }
                                            b++;
                                        }
                                        if (lastchar == false)
                                            break;
                                        if (pattern[k + 1] == words[i][(k + 1) + a + b])  
                                        {
                                            if ((k + 2 + a + b) <= words[i].Length - 1)
                                            {
                                                if ((words[i][(k + 1) + a + b] == words[i][(k + 2) + a + b])) // if there are two same letters next to each other in the word and they correspond to the star in pattern, it finds which letter the star will be based on.
                                                {
                                                    while (words[i][(k + 1) + a + b] == words[i][(k + 2) + a + b + c])
                                                    {

                                                        if ((k + 2) + a + b + c == words[i].Length - 1)
                                                            break;
                                                        c++;
                                                    }
                                                }
                                            }
                                        }
                                        if ((k + 2) <= (pattern.Length - 1))
                                        {
                                            if (pattern[k + 1] == pattern[k + 2])
                                                a = a + b;
                                            else
                                                a = a + b + c;
                                        }
                                        else
                                            a = a + b + c;

                                        b = 0;
                                        c = 0;
                                    }
                                    else if ((pattern[k] == '*') && (k == pattern.Length - 1)) // if the star is the last character in the pattern and the previous controls have been provided, the text is considered to match the pattern.
                                    {
                                        starcontrol = true;
                                    }

                                }
                            }

                            if(starcontrol == true)
                            {
                                for (int m = 0; m < i; m++) // A loop that ensures that the same text is not printed again if it was printed earlier
                                {
                                    if (lastwords[i] != lastwords[m])
                                        samewords2 = true;
                                    else
                                    {
                                        samewords2 = false;
                                        break;
                                    }

                                }
                                if (pattern[pattern.Length - 1] != '*') // Equality of the last characters is checked when the last character is not a star
                                {
                                    if (pattern[pattern.Length - 1] != words[i][words[i].Length - 1])
                                        samewords2 = false;
                                }
                                if (samewords2 == true)
                                    Console.WriteLine(lastwords[i]);
                                
                            }
                            lastchar = true;
                            a = 0;
                            break;
                        }
                        starcontrol = true;
                    }
                }
            }
        }
    }
}
