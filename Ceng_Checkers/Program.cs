using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chinese_Checkers
{
    class Program
    {
        static void finish(string[,] array)
        {

            for (int i = 1; i < 9; i++)// X AXIS
            {
                for (int k = 1; k < 9; k++) // Y AXIS
                {
                    Console.SetCursorPosition(i * 2 - 1, k);
                    Console.ForegroundColor = ConsoleColor.White;
                    if (array[i, k] == " " && (i + k) % 2 == 0) // THIS IS HOW TO MAKE THE GUI LOOK LIKE A CHECKERS BOARD
                    {
                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                        Console.Write("█");
                    }
                    else
                    {
                        if (array[i, k] == "█")
                        {
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                        }
                        else
                        {
                            Console.ForegroundColor = ConsoleColor.DarkBlue;
                        }
                        Console.Write(array[i, k]);
                    }
                    Console.SetCursorPosition(i * 2, k);
                    if (array[i, k] == " " && (i + k) % 2 == 0) // THIS IS HOW TO MAKE THE GUI LOOK LIKE A CHECKERS BOARD
                    {
                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                        Console.Write("█");
                    }
                    else
                    {
                        if (array[i, k] == "█")
                        {
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                        }
                        else
                        {
                            Console.ForegroundColor = ConsoleColor.DarkBlue;
                        }
                        Console.Write(array[i, k]);
                    }
                }
            }

        }

        static void Main(string[] args)
        {


            bool game = true;
            bool chain = false;
            int cursorX = 1, cursorY = 1;
            Random Random = new Random();
            string[,] arrays = new string[,] {  {"Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y"},
                                    {"Y", "█", "█", "█", " ", " ", " ", " ", " ", "Y", "Y"},
                                    {"Y", "█", "█", "█", " ", " ", " ", " ", " ", "Y", "Y"},
                                    {"Y", "█", "█", "█", " ", " ", " ", " ", " ", "Y", "Y"},
                                    {"Y", " ", " ", " ", " ", " ", " ", " ", " ", "Y", "Y"},
                                    {"Y", " ", " ", " ", " ", " ", " ", " ", " ", "Y", "Y"},
                                    {"Y", " ", " ", " ", " ", " ", "▓", "▓", "▓", "Y", "Y"},
                                    {"Y", " ", " ", " ", " ", " ", "▓", "▓", "▓", "Y", "Y"},
                                    {"Y", " ", " ", " ", " ", " ", "▓", "▓", "▓", "Y", "Y"},
                                    {"Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y"},
                                    {"Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y", "Y"}};
            while (true)
            {
                Console.Clear();
                Console.WriteLine("1-MAIN GAME\n2-EASIER GAME\n3-Info\nESC-Exit");
                ConsoleKey keyinfo = Console.ReadKey().Key;
                if (keyinfo == ConsoleKey.NumPad1 || keyinfo == ConsoleKey.D1)
                {
                    while (game)
                    {
                        Console.SetCursorPosition(cursorX, cursorY);

                        bool turn = true;
                        while (turn)
                        {
                            Console.ForegroundColor = ConsoleColor.White;
                            Console.Clear();
                            Console.Write("╔════════════════╗\n║                ║1\n║                ║2\n║                ║3\t IF YOU DID A JUMP\n║                ║4\t PRESS ENTER TO END YOUR TURN\n║                ║5\n║                ║6\n║                ║7\n║                ║8\n╚════════════════╝\n A B C D E F G H ");
                            for (int i = 1; i < 9; i++)// X AXIS
                            {
                                for (int k = 1; k < 9; k++) // Y AXIS
                                {
                                    Console.SetCursorPosition(i * 2 - 1, k);
                                    Console.ForegroundColor = ConsoleColor.White;
                                    if (arrays[i, k] == " " && (i + k) % 2 == 0) // THIS IS HOW TO MAKE THE GUI LOOK LIKE A CHECKERS BOARD
                                    {
                                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                                        Console.Write("█");
                                    }
                                    else
                                    {
                                        if (arrays[i, k] == "█")
                                        {
                                            Console.ForegroundColor = ConsoleColor.DarkRed;
                                        }
                                        else
                                        {
                                            Console.ForegroundColor = ConsoleColor.DarkBlue;
                                        }
                                        Console.Write(arrays[i, k]);
                                    }
                                    Console.SetCursorPosition(i * 2, k);
                                    if (arrays[i, k] == " " && (i + k) % 2 == 0) // THIS IS HOW TO MAKE THE GUI LOOK LIKE A CHECKERS BOARD
                                    {
                                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                                        Console.Write("█");
                                    }
                                    else
                                    {
                                        if (arrays[i, k] == "█")
                                        {
                                            Console.ForegroundColor = ConsoleColor.DarkRed;
                                        }
                                        else
                                        {
                                            Console.ForegroundColor = ConsoleColor.DarkBlue;
                                        }
                                        Console.Write(arrays[i, k]);
                                    }
                                }
                            }
                            Console.SetCursorPosition(cursorX, cursorY);
                            keyinfo = Console.ReadKey(true).Key;

                            switch (keyinfo) // PLAYERS GAMEPLAY
                            {
                                case ConsoleKey.LeftArrow:
                                    if (cursorX > 1 && !chain)
                                    {
                                        cursorX -= 2;
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.UpArrow:
                                    if (cursorY > 1 && !chain)
                                    {
                                        cursorY--;
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.RightArrow:
                                    if (cursorX < 15 && !chain)
                                    {
                                        cursorX += 2;
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.DownArrow:
                                    if (cursorY < 8 && !chain)
                                    {
                                        cursorY++;
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.A: // STEP/JUMP TO LEFT SIDE
                                    if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorX > 1 && arrays[cursorX / 2, cursorY] == " " && chain == false)
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2, cursorY] = "▓";
                                        turn = false;
                                        cursorX -= 2;
                                        Console.Beep(600, 100);
                                    }
                                    else if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorX > 1 && arrays[cursorX / 2, cursorY] != " " && arrays[cursorX / 2 - 1, cursorY] == " ")
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 - 1, cursorY] = "▓";
                                        chain = true;
                                        cursorX -= 4;
                                        Console.Beep(600, 100);
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.D: // STEP/JUMP TO RIGHT SIDE
                                    if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorX < 17 && arrays[cursorX / 2 + 2, cursorY] == " " && chain == false)
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 + 2, cursorY] = "▓";
                                        turn = false;
                                        cursorX += 2;
                                        Console.Beep(600, 100);
                                    }
                                    else if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorX < 17 && arrays[cursorX / 2 + 2, cursorY] != " " && arrays[cursorX / 2 + 3, cursorY] == " ")
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 + 3, cursorY] = "▓";
                                        chain = true;
                                        cursorX += 4;
                                        Console.Beep(600, 100);
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.W: // STEP/JUMP TO ABOVE
                                    if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorY > 1 && arrays[cursorX / 2 + 1, cursorY - 1] == " " && chain == false)
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 + 1, cursorY - 1] = "▓";
                                        turn = false;
                                        cursorY--;
                                        Console.Beep(600, 100);
                                    }
                                    else if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorY > 1 && arrays[cursorX / 2 + 1, cursorY - 1] != " " && arrays[cursorX / 2 + 1, cursorY - 2] == " ")
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 + 1, cursorY - 2] = "▓";
                                        chain = true;
                                        cursorY -= 2;
                                        Console.Beep(600, 100);
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.S: // STEP/JUMP TO ABOVE
                                    if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorY >= 1 && arrays[cursorX / 2 + 1, cursorY + 1] == " " && chain == false)
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 + 1, cursorY + 1] = "▓";
                                        turn = false;
                                        cursorY++;
                                        Console.Beep(600, 100);
                                    }
                                    else if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorY < 8 && arrays[cursorX / 2 + 1, cursorY + 1] != " " && arrays[cursorX / 2 + 1, cursorY + 2] == " ")
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 + 1, cursorY + 2] = "▓";
                                        chain = true;
                                        cursorY += 2;
                                        Console.Beep(600, 100);
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.Enter:
                                    if (chain)
                                    {
                                        turn = false;
                                    }
                                    break;
                            }
                        }

                        // WINNING PROPERTIES FOR THE PLAYER
                        if (arrays[1, 1] == "▓" && arrays[1, 2] == "▓" && arrays[1, 3] == "▓" &&
                           arrays[2, 1] == "▓" && arrays[2, 2] == "▓" && arrays[2, 3] == "▓" &&
                           arrays[3, 1] == "▓" && arrays[3, 2] == "▓" && arrays[3, 3] == "▓")
                        {
                            game = false;
                            Console.Clear();
                            finish(arrays);
                            Console.SetCursorPosition(20, 0);
                            Console.ForegroundColor = ConsoleColor.DarkBlue;
                            Console.WriteLine("     The Winner Is BLUE, Press Enter To Close The Console");
                            Console.ReadKey();
                        }
                        if (!turn && game) // THE BOT GAMEPLAY
                        {
                            bool flag = false;
                            for (int i = 1; i < 9; i++) // CHECKING IF ANY OF THE PAWNS CAN JUMP OR NOT. IF IT CAN IT JUMPS
                            {
                                if (!flag)
                                {
                                    for (int k = 1; k < 9; k++)
                                    {
                                        if (arrays[i, k] == "█" && ((arrays[i, k + 1] != " " && arrays[i, k + 2] == " ") || (arrays[i + 1, k] != " " && arrays[i + 2, k] == " ")))
                                        {
                                            flag = true;
                                            while (arrays[i, k] == "█" && ((arrays[i, k + 1] != " " && arrays[i, k + 2] == " ") || (arrays[i + 1, k] != " " && arrays[i + 2, k] == " ")))
                                            {
                                                if (arrays[i, k + 1] != " " && arrays[i, k + 2] == " ")
                                                {
                                                    arrays[i, k] = " ";
                                                    arrays[i, k + 2] = "█";
                                                    k += 2;

                                                }
                                                else
                                                {
                                                    arrays[i, k] = " ";
                                                    arrays[i + 2, k] = "█";
                                                    i += 2;
                                                }

                                            }
                                            break;
                                        }

                                    }
                                }
                            }

                            if (!flag) // IF NONE OF THE PAWNS CAN JUMP THE BOT RANDOMLY STEPS
                            {
                                int i = Random.Next(1, 9);
                                int k = Random.Next(1, 9);
                                int count = 0;
                                while ((arrays[i, k] != "█" || (arrays[i + 1, k] != " " && arrays[i, k + 1] != " ")) && count < 128) 
                                {
                                    i = Random.Next(1, 9);
                                    k = Random.Next(1, 9);
                                    count++;
                                }

                                if (count != 128) // IF BOT CAN STEP TO BOTTOM OR RIGHT IT STEPS TO BOTTOM OR RIGHT 
                                {
                                    if (arrays[i, k] == "█" && arrays[i + 1, k] == " ")
                                    {
                                        arrays[i, k] = " ";
                                        arrays[i + 1, k] = "█";
                                        flag = true;
                                    }
                                    else if (arrays[i, k] == "█" && arrays[i, k + 1] == " ")
                                    {
                                        arrays[i, k] = " ";
                                        arrays[i, k + 1] = "█";
                                        flag = true;
                                    }
                                }
                                else // IT STEPS UPWARDS OR LEFT
                                {
                                    while (arrays[i, k] != "█" || (arrays[i - 1, k] != " " && arrays[i, k - 1] != " "))
                                    {
                                        i = Random.Next(1, 9);
                                        k = Random.Next(1, 9);
                                    }
                                    if (arrays[i, k] == "█" && arrays[i - 1, k] == " ")
                                    {
                                        arrays[i, k] = " ";
                                        arrays[i - 1, k] = "█";
                                        flag = true;
                                    }
                                    else if (arrays[i, k] == "█" && arrays[i, k - 1] == " ")
                                    {
                                        arrays[i, k] = " ";
                                        arrays[i, k - 1] = "█";
                                        flag = true;
                                    }
                                }
                            }
                        }
                        // WINNING PROPERTIES FOR THE BOT
                        if (arrays[6, 6] == "█" && arrays[6, 7] == "█" && arrays[6, 8] == "█" &&
                           arrays[7, 6] == "█" && arrays[7, 7] == "█" && arrays[7, 8] == "█" &&
                           arrays[8, 6] == "█" && arrays[8, 7] == "█" && arrays[8, 8] == "█")
                        {
                            game = false;
                            Console.Clear();
                            finish(arrays);
                            Console.SetCursorPosition(20, 0);
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                            Console.WriteLine("     The Winner Is RED, Press Enter To Close The Console");
                            Console.ReadKey();
                        }

                        chain = false;
                        turn = true;
                    }
                }
                else if (keyinfo == ConsoleKey.NumPad2 || keyinfo == ConsoleKey.D2)
                {
                    while (game)
                    {
                        Console.SetCursorPosition(cursorX, cursorY);

                        bool turn = true;
                        while (turn)
                        {
                            Console.ForegroundColor = ConsoleColor.White;
                            Console.Clear();
                            Console.Write("╔════════════════╗\n║                ║1\n║                ║2\n║                ║3\t IF YOU DID A JUMP\n║                ║4\t PRESS ENTER TO END YOUR TURN\n║                ║5\n║                ║6\n║                ║7\n║                ║8\n╚════════════════╝\n A B C D E F G H ");
                            for (int i = 1; i < 9; i++)// X AXIS
                            {
                                for (int k = 1; k < 9; k++) // Y AXIS
                                {
                                    Console.SetCursorPosition(i * 2 - 1, k);
                                    Console.ForegroundColor = ConsoleColor.White;
                                    if (arrays[i, k] == " " && (i + k) % 2 == 0) // THIS IS HOW TO MAKE THE GUI LOOK LIKE A CHECKERS BOARD
                                    {
                                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                                        Console.Write("█");
                                    }
                                    else
                                    {
                                        if (arrays[i, k] == "█")
                                        {
                                            Console.ForegroundColor = ConsoleColor.DarkRed;
                                        }
                                        else
                                        {
                                            Console.ForegroundColor = ConsoleColor.DarkBlue;
                                        }
                                        Console.Write(arrays[i, k]);
                                    }
                                    Console.SetCursorPosition(i * 2, k);
                                    if (arrays[i, k] == " " && (i + k) % 2 == 0) // THIS IS HOW TO MAKE THE GUI LOOK LIKE A CHECKERS BOARD
                                    {
                                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                                        Console.Write("█");
                                    }
                                    else
                                    {
                                        if (arrays[i, k] == "█")
                                        {
                                            Console.ForegroundColor = ConsoleColor.DarkRed;
                                        }
                                        else
                                        {
                                            Console.ForegroundColor = ConsoleColor.DarkBlue;
                                        }
                                        Console.Write(arrays[i, k]);
                                    }
                                }
                            }
                            Console.SetCursorPosition(cursorX, cursorY);
                            keyinfo = Console.ReadKey(true).Key;

                            switch (keyinfo) // PLAYERS GAMEPLAY
                            {
                                case ConsoleKey.LeftArrow:
                                    if (cursorX > 1 && !chain)
                                    {
                                        cursorX -= 2;
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.UpArrow:
                                    if (cursorY > 1 && !chain)
                                    {
                                        cursorY--;
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.RightArrow:
                                    if (cursorX < 15 && !chain)
                                    {
                                        cursorX += 2;
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.DownArrow:
                                    if (cursorY < 8 && !chain)
                                    {
                                        cursorY++;
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.A: // STEP/JUMP TO LEFT LEFT SIDE
                                    if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorX > 1 && arrays[cursorX / 2, cursorY] == " " && chain == false)
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2, cursorY] = "▓";
                                        turn = false;
                                        cursorX -= 2;
                                        Console.Beep(600, 100);
                                    }
                                    else if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorX > 1 && arrays[cursorX / 2, cursorY] != " " && arrays[cursorX / 2 - 1, cursorY] == " ")
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 - 1, cursorY] = "▓";
                                        chain = true;
                                        cursorX -= 4;
                                        Console.Beep(600, 100);
                                        Console.SetCursorPosition(cursorX, cursorY);
                                    }
                                    break;
                                case ConsoleKey.W: // STEP/JUMP TO ABOVE
                                    if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorY > 1 && arrays[cursorX / 2 + 1, cursorY - 1] == " " && chain == false)
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 + 1, cursorY - 1] = "▓";
                                        turn = false;
                                        cursorY--;
                                        Console.Beep(600, 100);
                                    }
                                    else if (arrays[cursorX / 2 + 1, cursorY] == "▓" && cursorY > 1 && arrays[cursorX / 2 + 1, cursorY - 1] != " " && arrays[cursorX / 2 + 1, cursorY - 2] == " ")
                                    {
                                        arrays[cursorX / 2 + 1, cursorY] = " ";
                                        arrays[cursorX / 2 + 1, cursorY - 2] = "▓";
                                        chain = true;
                                        cursorY -= 2;
                                        Console.SetCursorPosition(cursorX, cursorY);
                                        Console.Beep(600, 100);
                                    }
                                    break;
                                case ConsoleKey.Enter:
                                    if (chain)
                                    {
                                        turn = false;
                                    }
                                    break;
                            }
                        }

                        // WINNING PROPERTIES FOR THE PLAYER
                        if (arrays[1, 1] == "▓" && arrays[1, 2] == "▓" && arrays[1, 3] == "▓" &&
                           arrays[2, 1] == "▓" && arrays[2, 2] == "▓" && arrays[2, 3] == "▓" &&
                           arrays[3, 1] == "▓" && arrays[3, 2] == "▓" && arrays[3, 3] == "▓")
                        {
                            game = false;
                            Console.Clear();
                            finish(arrays);
                            Console.SetCursorPosition(20, 0);
                            Console.ForegroundColor = ConsoleColor.DarkBlue;
                            Console.WriteLine("     The Winner Is BLUE, Press Enter To Close The Console");
                            Console.ReadKey();
                        }
                        // TIE SITUATION
                        if ((arrays[1, 1] == "▓" && arrays[1, 2] == "▓" && arrays[1, 3] == "▓" &&
                           arrays[1, 4] == "▓") || (arrays[1, 1] == "▓" && arrays[2, 1] == "▓" &&
                           arrays[3, 1] == "▓" && arrays[4, 1] == "▓"))
                        {
                            game = false;
                            Console.Clear();
                            finish(arrays);
                            Console.SetCursorPosition(20, 0);
                            Console.ForegroundColor = ConsoleColor.DarkBlue;
                            Console.WriteLine("     The game cannot continue, Press Enter To Close The Console");
                            Console.ReadKey();
                        }
                        if (!turn && game) // THE BOT GAMEPLAY
                        {
                            int random_x = Random.Next(1, 9);
                            int random_y = Random.Next(1, 9);
                            while (!((arrays[random_x, random_y] == "█") && ((arrays[random_x + 1, random_y] != " " && arrays[random_x + 2, random_y] == " ") || (arrays[random_x, random_y + 1] != " " && arrays[random_x, random_y + 2] == " ") || arrays[random_x + 1, random_y] == " " || arrays[random_x, random_y + 1] == " ")))
                            {
                                random_x = Random.Next(1, 9);
                                random_y = Random.Next(1, 9);
                            }
                            int random_direction = Random.Next(1, 3); // DECIDING WHICH WAY TO GO IF THERE IS NO CHAIN-JUMP SITUATION
                            chain = false;
                            while ((arrays[random_x + 1, random_y] != " " && arrays[random_x + 2, random_y] == " ") || (arrays[random_x, random_y + 1] != " " && arrays[random_x, random_y + 2] == " "))
                            {
                                random_direction = Random.Next(1, 3); // DECIDING WHICH WAY TO GO IF THERE IS A CHAIN-JUMP SITUATION
                                if (random_direction == 1)
                                {
                                    if (arrays[random_x + 1, random_y] != " " && arrays[random_x + 2, random_y] == " ") // JUMP TO THE RIGHT
                                    {
                                        arrays[random_x + 2, random_y] = arrays[random_x, random_y];
                                        arrays[random_x, random_y] = " ";
                                        random_x += 2;
                                        chain = true;
                                    }
                                    else if (arrays[random_x, random_y + 1] != " " && arrays[random_x, random_y + 2] == " ")  // JUMP TO THE BOTTOM
                                    {
                                        arrays[random_x, random_y + 2] = arrays[random_x, random_y];
                                        arrays[random_x, random_y] = " ";
                                        random_y += 2;
                                        chain = true;
                                    }
                                }
                                else
                                {
                                    if (arrays[random_x, random_y + 1] != " " && arrays[random_x, random_y + 2] == " ")  // JUMP TO THE BOTTOM
                                    {
                                        arrays[random_x, random_y + 2] = arrays[random_x, random_y];
                                        arrays[random_x, random_y] = " ";
                                        random_y += 2;
                                        chain = true;
                                    }
                                    else if (arrays[random_x + 1, random_y] != " " && arrays[random_x + 2, random_y] == " ")  // JUMP TO THE RIGHT
                                    {
                                        arrays[random_x + 2, random_y] = arrays[random_x, random_y];
                                        arrays[random_x, random_y] = " ";
                                        random_x += 2;
                                        chain = true;
                                    }
                                }
                            }
                            if (!chain)
                            {
                                if (random_direction == 1)
                                {
                                    if (arrays[random_x + 1, random_y] == " ") // STEP TO THE RIGHT
                                    {
                                        arrays[random_x + 1, random_y] = arrays[random_x, random_y];
                                        arrays[random_x, random_y] = " ";
                                    }
                                    else // STEP TO THE BOTTOM
                                    {
                                        arrays[random_x, random_y + 1] = arrays[random_x, random_y];
                                        arrays[random_x, random_y] = " ";
                                    }
                                }
                                else
                                {
                                    if (arrays[random_x, random_y + 1] == " ") // STEP TO THE RIGHT
                                    {
                                        arrays[random_x, random_y + 1] = arrays[random_x, random_y];
                                        arrays[random_x, random_y] = " ";
                                    }
                                    else // STEP TO THE BOTTOM
                                    {
                                        arrays[random_x + 1, random_y] = arrays[random_x, random_y];
                                        arrays[random_x, random_y] = " ";
                                    }
                                }
                            }

                        }
                        // WINNING PROPERTIES FOR THE BOT
                        if (arrays[6, 6] == "█" && arrays[6, 7] == "█" && arrays[6, 8] == "█" &&
                           arrays[7, 6] == "█" && arrays[7, 7] == "█" && arrays[7, 8] == "█" &&
                           arrays[8, 6] == "█" && arrays[8, 7] == "█" && arrays[8, 8] == "█")
                        {
                            game = false;
                            Console.Clear();
                            finish(arrays);
                            Console.SetCursorPosition(20, 0);
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                            Console.WriteLine("     The Winner Is RED, Press Enter To Close The Console");
                            Console.ReadKey();
                        }
                        // TIE SITUATION
                        if ((arrays[5, 8] == "█" && arrays[6, 8] == "█" && arrays[7, 8] == "█" &&
                           arrays[8, 8] == "█") || (arrays[8, 5] == "█" &&
                           arrays[8, 6] == "█" && arrays[8, 7] == "█" && arrays[8, 8] == "█"))
                        {
                            game = false;
                            Console.Clear();
                            finish(arrays);
                            Console.SetCursorPosition(20, 0);
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                            Console.WriteLine("     The game cannot continue, Press Enter To Close The Console");
                            Console.ReadKey();
                        }

                        chain = false;
                        turn = true;
                    }
                }
                else if (keyinfo == ConsoleKey.NumPad3 || keyinfo == ConsoleKey.D3)
                {
                    Console.Clear();
                    Console.WriteLine("The game is called CENG Checkers \n\nThe game is a two - player board game like checkers\n\nPlayers can move their pieces in 4 directions (with W-A-S-D keys) according to the rules.\n\nThe aim of the game is to be the first player to move all 9\npawns across the board and into their own home area");
                    Console.ReadKey();
                }
                else if (keyinfo == ConsoleKey.Escape)
                {
                    Environment.Exit(0);
                }
                else
                {
                    keyinfo = Console.ReadKey(true).Key;
                }
            }
        }
    }
}


