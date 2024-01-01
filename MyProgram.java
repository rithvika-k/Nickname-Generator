/*
Rithvika Kathroju 
11/1/2022
ICS 4U1-01

Unit 2 Assignment

Option 1: The Nickname Generator Program
-- Kahoot Triva Simulator 
The player is given a list of 25 uniquely generated nicknames. Here, you will 
also be able to add, search, and or delete from the list of nicknames. Each player
will then pick a player nickname and then proceed to answer 5 triva questions. 
After each question, points will be awarded and a scoreboard will be 
displayed. At the end of the game a podium with the top three players will be 
displayed. The objective is to get the most amount of questions right to WIN!

NOTES README!
- implemented 2D arrays for the questionnaire aspect
- restructured code to implement classes, complete with attributes, constructors, and methods
  --- Player class which acts as a storage to hold key attributes of any Player object created
      helps keep code organized and make working with the attributes of any Player object easier
- an extended java class
  --- KahootPlayer subclass which extends from the Player class. It includes methods more specific
      to Kahoot players as opposed to any other player (i.e. number of questions correct, or if the
      player got a question right)
- created a method that checks to see if there are any duplicate nicknames generated
  --- program also prevents duplicate trivia questions from being outputted
      prevent user from adding a nickname that already exists
      prevent user from deleting too many nicknames (4 nicknames should remain, one for each player)
- used an ArrayList<> to add/delete (among other things) from an array
  --- ArrayList<String> nicknames (array that holds the 25 generated nicknames)
*/

import java.util.Scanner; // import Scanner class
import java.util.Arrays; // import Arrays class
import java.util.InputMismatchException; // import InputMismatchException class
import java.io.File; // import File class
import java.io.FileNotFoundException; // import FileNotFoundException class
import java.util.ArrayList; // import ArrayList class
import java.util.Collections; // import Collections class (for ArrayList objects)


public class MyProgram{
    
    // declare global variables
    static Scanner s = new Scanner(System.in); // declare and initialize scanner object s for keyboard input
    
    // 3 String arrays to hold the options for each part of the nickname
    static String[] adjectives1 = new String[10]; // declare and initialize adjectives1 array to hold 10 elements
    static String[] adjectives2 = new String[10]; // declare and initialize adjectives2 array to hold 10 elements
    static String[] animals = new String[10]; // declare and initialize animals array to hold 10 elements
    
    static ArrayList<String> nicknames = new ArrayList<String>(); // declare ArrayList object to store the generated nicknames
    static KahootPlayer[] players = new KahootPlayer[4]; // declare an array to store KahootPlayer objects (4 players)
    
    
    public static void main(String[] args){
        
        convertTextFilesToArrays(); // invoke method to convert the 3 textfiles, that construct the nickname, into arrays 
        printFile("Introduction.txt"); // invoke method with an arugument to print an introduction game message
        
        generateNicknames(); // invoke method to generate the list of 25 nicknames 
        printNicknames(); // invoke method to display the 25 generated nicknames to the console
        printFile("Prompt.txt"); // invoke method with an argument to print a prompt informing them to search, add, or delete elements from the array, OR enter 0 to proceed to play
        getUsersChoice(); // invoke method to get users choice for the prompt above
        
        getPlayers(); // since the user will eventually enter 0 to play, invoke getPlayers method to get players for the game
        playKahootGame(); // invoke method to play the kahoot triva game
        declareWinner(); // invoke method to display the podium of top three and declare the winner
        printFile("Ending.txt"); // invoke method with argument, to output end of program message
        
    } // end main()
    
    
    /*
    this method converts each textfile into an array, has two parameters to take in the file name and array
    */
    public static void fileToArray(String file, String[] namesList){
        /*
        try/catch block checks to see if the file exists, if not the catch block would be executed
        */
        try{
            File namesFile = new File(file); // create an object from the File class to store the file passed in
            Scanner f1 = new Scanner(namesFile); // declare and initialize a scanner object to scan the text in the text file
            int i = 0; // create a counter that holds the index position of an element coming from the file
            
            // the while loop executes so long as we have a line of text to read in.
            while(f1.hasNextLine()){
                namesList[i] = f1.nextLine(); // store the line in the array(passed in) at a specific index (based on the number of the line read)
                i++; // increment i for the next index position of the array
            }
            f1.close(); // close scanner object for file
        }
        catch(FileNotFoundException e){
            System.out.println("File does not exist"); // output an error message
        }
    } // end fileToArray()
    
    
    /*
    this method converts all 3 textfiles contributing to the nickname into arrays 
    this method helps decluttter the main method, and makes it easier to understand
    invokes the fileToArray() method for each file conversion
    */
    public static void convertTextFilesToArrays(){
        fileToArray("Adjectives1.txt", adjectives1); // invoke method to convert the first list of 10 adjectives into an array
        fileToArray("Adjectives2.txt", adjectives2); // invoke method to convert the second list of 10 adjectives into an array
        fileToArray("Animals.txt", animals); // invoke method to convert the third list with animals into an array
    } // end convertTextFilesToArrays()
    
    
    /*
    this method takes in a textfile as an argument and prints the text within it 
    used for introduction, prompt, and end of game message
    */
    public static void printFile(String file){
        /*
        try/catch block checks to see if the file exists, if not the catch block would be executed
        */
        try{
            File namesFile = new File(file); // create an object from the File class to store the file passed in
            Scanner f2 = new Scanner(namesFile); // declare and initialize a scanner object to scan the text in the text file
            
            // the while loop executes so long as we have a line of text to read in.
            while(f2.hasNextLine()){
                String line = f2.nextLine(); // stores the line read by the scanner in a variable
                System.out.println(line); // prints the stored line
            }
            f2.close(); // closes the scanner object for the file
        }
        catch(FileNotFoundException e){
            System.out.println("File does not exist"); // output error message
        }
    } // end printFile()
    
    
    /*
    this method generates the list of 25 random nicknames, 
    first prints an introduction, then randomly generates each part of the nickname to create it, and adds it to the array
    also ensures there are no duplicate nicknames
    */
    public static void generateNicknames(){
        // output a subheading to the console to make it more organized and visually appealing
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("                                 PLAYER NICKNAME");
        System.out.println("--------------------------------------------------------------------------------");
        
        System.out.println("First Pick a Nickname!\n");
        System.out.print("Here is the randomly generated list of nicknames.\nPress enter to view: "); // prompt the user to press enter
        s.nextLine(); // take in input, no reason to store it
        System.out.print("\n"); // new line for formatting
        
        /*
        this for loop loops 25 times, in order to generate 25 nicknames,
        generates 3 random numbers between (0 to 9) which will act as the index position to 
        access an element from the 3 arrays that construct a nickname. the nickname will then 
        be created, stored, and added to the nicknames (which is an ArrayList)
        
        also checks for duplicates, if there is, i will be decremented, in which the for loop will iterate
        again for that value of i to ensure that a nickname is generated at that position (ensures 25 nicknames will be generated)
        */
        for(int i = 0; i < 25; i++){
            // declare and initialize 3 variables to store a random number between 0 and 9 (invoke method)
            int randNum1 = randomNumGenerator(0,9);
            int randNum2 = randomNumGenerator(0,9);
            int randNum3 = randomNumGenerator(0,9);
            
            // declare and initialize nickname variable, constructs nickname using concatenation and an element from adjectives1, adjectives2, and animals
            String nickname = adjectives1[randNum1] + " " + adjectives2[randNum2] + " " + animals[randNum3]; 
            
            nicknames.add(nickname); // adds nickname to nicknames array
            
            // if statement runs if there is a duplicate (invoking method that returns boolean)
            if(isDuplicate(nicknames, i)){
                i--; // decrement i so that for loop can repeat for that value
            }
        }
    } // end generateNicknames()
    
    
    /*
    this method generates a random number between a range that the user enters as arguments
    */
    public static int randomNumGenerator(int min, int max){
        return (int)(Math.random()*(max - min + 1) + min); // formula that generates random number
    } // end randomNumGenerator()
    
    
    /*
    this method checks for duplicates in an ArrayList, has two parameters (ArrayList, index)
    mainly for the nicknames array, 
    method is structured in such a way where it only checks for duplicates BELOW that specified index
    */
    public static boolean isDuplicate(ArrayList<String> array, int index){
        /*
        for loop compares the value at a specific index with each of the values before it in an array
        if they are equal the method would return a boolean value of true
        otherwise it will return false
        */
        for(int i = 0; i < index; i++){
            if(array.get(i).equals(array.get(index))){
                return true;
            }
        }
        return false;
    } // end isDuplicate()
    
    
    /*
    this method prints each of the elements in the nicknames array in a well formatted manner
    */
    public static void printNicknames(){
        // nicknames array is an object from the ArrayList class
        // therefore, using Collections.sort method sorts the nicknames array in acending order (a-z) case insensitive 
        Collections.sort(nicknames, String.CASE_INSENSITIVE_ORDER);
        
        // for loop prints out each element in the nicknames array in a numbered list format
        for(int i = 0; i < nicknames.size(); i++){
            System.out.println((i+1) + ". " + nicknames.get(i));
        }
    } // end printNicknames()
    
    
    /*
    this method gets the user choice, (search, add, delete elements in the list, or proceed to the game)
    */
    public static void getUsersChoice(){
        int usersChoice; // declare variable to store if the users choice
        
        /*
        while loop keeps iterating until the user enters 0, in which the will proceed to play the game. 
        the loop will continue if the enter 1,2,3, but will break out of the loop once user enters 0
        */
        while(true){
            System.out.print("\nEnter number (1,2,3, or 0): "); // prompt user to enter an option, option description is outputted from textfile
            
            /*
            try-catch block ensures that user enters valid input(a number)
            catch block is executed if the user enters input other than a number
            */
            try{
                usersChoice = s.nextInt(); // get user input
                s.nextLine(); // fix glitch
                // if block ensures user enters a number within range, otherwise they will be prompted again (go back to the top of loop)
                if(usersChoice < 0 || usersChoice > 3){
                    System.out.println("Invalid entry. Please try again."); // output error message
                    continue;
                }
                
                /*
                if-else block checks what the user entered and executes code accordingly 
                */
                // if user wants to search
                if(usersChoice == 1){
                    searchForNickname(); // invoke method
                }
                // if user wants to add 
                else if(usersChoice == 2){
                    addNickname(); // invoke method
                }
                // if user wants to delete 
                else if(usersChoice == 3){
                    // if block executes if there are only 4 nicknames in the nicknames array  (4 nicknames must remain - one for each player)
                    if(nicknames.size() == 4){
                        System.out.println("You have already deleted the maximum number of nicknames and cannot delete anymore. Please select another option."); // output message
                    }
                    // otherwise user can delete an item, since there are more than 4 nicknames
                    else{
                        deleteNickname(); // invoke method
                    }
                }
                // if the user enters 0, they will proceed to the game, it will also break out of the while loop
                else{
                    System.out.println("\nPROCEEDING TO GAME...");
                    break;
                }
            }
            // executed if user does not enter a number
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a number."); // print error message
                s.nextLine(); // fix glitch
            }
        }
    } // end getUsersChoice()
    
    
    /*
    this method allows the user to search for a nickname in the nicknames array
    */
    public static void searchForNickname(){
        System.out.println("\n--------------------\nSEARCH FOR NICKNAME\n--------------------\n"); // output subheading
        
        System.out.println("Enter the nickname you would like to search (In Sentence Case): "); // prompt user
        String searchNickname = s.nextLine(); // get input and store it in a variable
        
        // sort the nicknames array (a-z) case insensitive
        Collections.sort(nicknames, String.CASE_INSENSITIVE_ORDER);
        // use binarySearch method to search the array for the nickname the user entered, store the index the method returns in a variable
        int searchIndex = Collections.binarySearch(nicknames, searchNickname); 
        
        /*
        if the index the method returns exists (>=0) tell the user that nickname was found,
        otherwise, inform the user that the nickname was not found
        */
        if(searchIndex >= 0){
            System.out.println("--Nickname was found"); // output message
        }
        else{
            System.out.println("--Nickname was not found"); // output message
        }
    } // end searchForNickname()
    
    
    /*
    this methods allows the user to add a nickname to the list of nicknames generated
    */
    public static void addNickname(){
        // declare and initialize variables to store user input for each part of the new nickname they want to add
        String adjAdd1, adjAdd2, animalAdd, addNickname; 
        
        // while loop will execute until the user enters a valid nickname, one that does not already exist in the list of nicknames
        while(true){
            boolean nicknameIsDuplicate = false; // declare and initalize boolean variable to check for duplicates
            
            System.out.println("\n-------------\nADD NICKNAME\n-------------\n"); // output subheading
            
            System.out.println("Please enter the first adjective: "); // prompt user to enter first adjective
            adjAdd1 = s.nextLine(); // get user input and store it
            
            System.out.println("Please enter the second adjective: "); // prompt user to enter second adjective 
            adjAdd2 = s.nextLine(); // get user input and store it
            
            System.out.println("Please enter an animal: "); // prompt user to enter animal
            animalAdd = s.nextLine(); // get user input and store it
            
            // combine parts of the nickname to create it, and store it as a variable
            addNickname = (adjAdd1 + " " + adjAdd2 + " " + animalAdd);
            
            /*
            for loop goes through each element in the nicknames array and compares it with the nickname the user wants
            to add, if they are the same (duplicate), user wil be informed, and will be prompted to enter a new nickname
            */
            for(int i = 0; i < nicknames.size(); i++){
                // compares each element in the nicknames array with the nickname the user wants to enter
                if(nicknames.get(i).equals(addNickname)){
                    System.out.println("\nThat nickname already exists. Follow the instructions to add a new one."); // output message
                    nicknameIsDuplicate = true; // sets the boolean variable to true so that the outer while loop can execute
                    break; // break out of for loop
                }
            }
            
            // if block executes if the nickname the user entered is NOT a duplicate, in which it will break out of the while loop
            // if block would not execute if there is a duplicate so it would go back to the outer loop to prompt user again
            if(!nicknameIsDuplicate){
                break; // break out of while loop
            }
        }
        
        nicknames.add(addNickname); // add nickname to the nicknames array
        System.out.println("\nUPDATED NICKNAMES LIST: \n"); // output subheading
        printNicknames(); // output updated list of nicknames
    } // end addNickname()
    
    
    /*
    this method allows the user to delete a nickname from the nicknames array list
    */
    public static void deleteNickname(){
        System.out.println("\n----------------\nDELETE NICKNAME\n----------------\n"); // output subheading
        
        int delNickname; // declare counter to keep track of the number of nicknames they delete
        
        // while loop executes until the user enters a valid input(valid number of nickname to delete)
        while(true){
            System.out.println("Enter the number of the nickname to delete: "); // prompt user
            
            try{
                delNickname = s.nextInt(); // get input and store it 
                s.nextLine(); // fix glitch
                
                // if block ensures user enters a number within range, otherwise they will be prompted again (go back to the top of loop)
                if(delNickname < 1 || delNickname > nicknames.size()){
                    System.out.println("Invalid entry. Please try again.\n"); // output error message
                    continue;
                }
                
                nicknames.remove(delNickname-1); // number of the nickname user entered is removed from arraylist
                
                System.out.println("\nUPDATED NICKNAMES LIST: \n"); // output subheading
                printNicknames(); // print the updated list of nicknames
                break;
            }
            catch(InputMismatchException e){
                System.out.println("Invalid entry! Please try again.\n"); // outputs error message to inform user
                s.nextLine(); // fixes the nextInt() glitch
            }
        }
    } // end deleteNickname
    
    
    /*
    this method allows the user to pick a nickname (player name), 
    it also then outputs the player name of each of the 4 players, (you and three other computers)
    */
    public static void getPlayers(){
        int choice; // declare variable to store the number of the nickname they pick
        System.out.println("\n----------------\nPICK NICKNAME\n----------------\n"); // output subheading
        System.out.println("NICKNAMES:\n"); // output subheading
        printNicknames(); // print the list of nicknames
        
        // while loop executes until the user enters valid input (number, that is also within range)
        while(true){
            System.out.print("\nEnter the number of the nickname you pick: "); // prompt the user to enter the number of the nickname they pick
            // try-catch block ensures user enters valid input(numbers), 
            // catch block is executed if user enters input other than a number
            try{
                choice = s.nextInt(); // get user input
                s.nextLine(); // fix glitch
                
                // if block ensures user enters a number within range, otherwise they will be prompted again
                if(choice < 1 || choice > nicknames.size()){
                    System.out.println("Invalid entry. Please try again."); // output error message
                    continue;
                }
                break; // break out of loop once user enters a valid number
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a number."); // print error message
                s.nextLine(); // fix glitch from nextInt()
            }
        }
        
        // prints out the nicknames for each of the 4 players
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════╗"); //formatting
        /*
        for loop will loop 4 times for each of the 4 players
        */
        for(int i = 0; i < 4; i++){
            /*
            if block will execute when i==0 (first player, which is the user)
            else block will execute for the remaining three players which are the computers
            */
            if(i == 0){
                // create a KahootPlayer object and store it in the players array (which is an array of objects) at index 0
                // pass in a nickname, and number of points
                players[i] = new KahootPlayer(nicknames.get(choice-1), 0); 
                // output first player name by referring to the players[i] object and a get method in the KahootPlayer class
                System.out.println("  Your Player Nickname: " + players[i].getNickname()); 
            }
            else{
                // generates a random number from 1 to the size of the nicknames array
                choice = randomNumGenerator(1, nicknames.size()); 
                // create a KahootPlayer object and store it in the players array (which is an array of objects) at a specific index 
                // pass in a nickname, and number of points
                players[i] = new KahootPlayer(nicknames.get(choice-1), 0);
                
                // output player name by referring to the players[i] object and a get method in the KahootPlayer class
                System.out.println("  Computer " + (i) + "\'s Nickname: " + players[i].getNickname());
            }
            
            // after each player picks a name, remove that name from the nickname list so that are no duplicate players
            nicknames.remove(choice-1); 
        }
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝"); // formatting
    } // end getPlayers()
    
    
    /*
    this method holds the entire kahoot trivia game
    it outputs a total of 5 random questions from a list of 12 and for each question each of the 4 players input an answer
    then, 
        the options they entered is outputted, 
        the players that got it right and wrong and the points each of them gained is outputted 
        and a scoreboard is outputted
    the program will also remove the question asked from the list of questions to ensure there are no duplicate questions asked
    */
    public static void playKahootGame(){
        int answer; // declare a variable to store the users answer
        
        // declare and initialize a (12 by 3) 2D array that holds 12 questions, the options, and the right answer
        // each row is in the format {question, options, correct answer}
        // 1st column holds all the questions, 2nd column holds all the options, 3rd column holds all the answers
        String[][] questionnaire = new String[][]{
            {"What is the most common element in the human body?", "1. Hydrogen\n2. Calcium\n3. Carbon\n4. Oxygen", "4"},
            {"What belongs to you but others use it more than you?", "1. Your brain\n2. Lindts\n3. Your name\n4. Your CPU", "3"},
            {"What grows in Ontario?", "1. Carrots\n2. Tomatoes\n3. Watermelons\n4. Good things", "4"},
            {"What is the closest planet to the sun?", "1. Mercury\n2. Earth\n3. Venus\n4. Mars", "1"},
            {"How many glasses of water should you drink everyday?", "1. 4 Glasses\n2. 0 Glasses\n3. 9 Glasses\n4. 8 Glasses", "4"},
            {"Which of the following has more bones?", "1. Teenager\n2. Baby\n3. Adult\n4. They are all the same", "2"},
            {"What colour is Rudolph's nose?", "1. Blue\n2. Red\n3. Black\n4. Brown", "2"},
            {"What can you catch but not throw?", "1. A candle\n2. A cold\n3. Wind\n4. Water", "2"},
            {"What do honey bees make?", "1. Honey\n2. Good Soup\n3. Cheerios\n4. Sugar", "1"},
            {"Which is a star?", "1. The Sun\n2. Jupiter\n3. Saturn\n4. Venus", "1"},
            {"Where is the Eiffel Tower?", "1. Croissant\n2. France\n3. Rome\n4. It doesn't exist", "2"},
            {"What is the colour of grass?", "1. Ruby Red\n2. Great Green\n3. Bad Blue\n4. Moody Magenta", "2"},
        };
        
        // print out a subheading 
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("                                 KAHOOT TRIVIA");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("\nCHOOSE THE BEST ANSWER IF MULTIPLE ARE CORRECT\n"); // output message
        
        // for loop loops 5 times, once for each randomly selected trivia question, 
        // this loop gets answers, outputs right/wrong, points gained by players, and scoreboard
        for(int q = 0; q < 5; q++){
            int questionNum; // declare variable to store random number for questionnaire
            int[] playerAnswers = new int[4]; // declare and initialize an array to hold each of the player answers
            String[][] proxyQuestionnaire; // declare proxy array to exclude the question just asked
            
            System.out.println("\n═════════════\n QUESTION " + (q+1) + "\n═════════════\n"); //output subheading
            
            // generates a random number from 0 to the questionnaire length array minus 1 and stores it, generates random questions
            questionNum = randomNumGenerator(0, questionnaire.length-1); 
            System.out.println(questionnaire[questionNum][0]); // display the question from the questionnaire at the row index of the randomly generated number
            System.out.println(questionnaire[questionNum][1]); // display the options from the questionnaire at the row index of the randomly generated number
            
            // ANSWERS
            // for loop, loops for each of the four players and allows them to enter an answer/ store an answer
            for(int i = 0; i < players.length; i++){
                /*
                if-else block executes based on whether the player is the user or a computer
                if executes if the player is the user, which is at the index 0
                else executes for the players that are computers, which are at index 1,2,3  of the players array
                */
                if(i == 0){
                    // while loop executes until the user enters a valid answer (number, also within range)
                    while(true){
                        System.out.print("\nEnter your answer(#): "); // prompt user to enter an answer
                        
                        /*
                        try-catch block ensures that the user enters valid input (number)
                        catch block is executed if user enters input other than a number
                        */
                        try{
                            playerAnswers[0] = s.nextInt(); // player answer is stored in the playerAnswers array at index 0, since the first element refers to the user
                            s.nextLine(); // fix glitch
                            
                            // if block ensures user enters a number within range, otherwise they will be prompted again
                            if(playerAnswers[0] < 1 || playerAnswers[0] > 4){
                                System.out.println("Invalid entry. Please try again."); // output error message
                                continue; // repeat loop so that user is prompted again 
                            }
                            // executes once the input is valid, outputs the option the user entered, used getNickname() method from created class
                            System.out.println("\n" + players[i].getNickname() + " answered: " + playerAnswers[0]); 
                            break; //break out of while loop
                        }
                        catch(InputMismatchException e){
                            System.out.println("Invalid input. Please enter a number."); // output error message 
                            s.nextLine(); // fix glitch
                        }
                    }
                }
                // else executes for computer players
                else{
                    // a random number is generated from 1 to 4, acts as their answer
                    // note that the user has a higher chance of winning because of this
                    playerAnswers[i] = randomNumGenerator(1,4); 
                    // outputs the option "answered", used getNickname() method from created class
                    System.out.println(players[i].getNickname() + " answered: " + playerAnswers[i]);
                }
            }
            
            // OUTPUTS WHETHER THE USER GOT THE ANSWER RIGHT OR WRONG
            System.out.println("\n"); // output new line
            /*
            for loop loops 4 times since the playerAnswers array has a length of 4
            loop checks if each player got the question right or wrong and outputs a message accordingly 
            it will then award points randomly generated between a range of 200-220
            */
            for(int i = 0; i < playerAnswers.length; i++){
                /*
                if-else block checks if user got the question right
                if the user got the question correct, a random number of points (200-220) will be awarded and a message will be displayed
                else, a message saying the player got the question wrong and gained 0 points will be displayed
                */
                if(playerAnswers[i] == Integer.parseInt(questionnaire[questionNum][2])){
                    int pointsToAdd = randomNumGenerator(200,220); // store a randomly generated number between 200-220 which will act as the number of points gained
                    players[i].gotQuestionRight(pointsToAdd); // add the points to the specific player object who got it right using the method created specifically for the KahootPlayer class
                    
                    System.out.println("✔️  " + players[i].getNickname() + " --- GAIN: +" + pointsToAdd + " points!"); // output message
                }
                else{
                    System.out.println("❌  " + players[i].getNickname() + " --- GAIN: 0 points!"); // output message
                }
            }
            
            // OUTPUT SCOREBOARD
            System.out.println("\n"); // output a new line
            // for loop prints out each player - output their nickname and the total number of points they have up until that point
            for(int i = 0; i < players.length; i++){
                System.out.println("💎  " + players[i].getNickname() + " | POINTS: " + players[i].getPoints()); // output message, used methods from created class
            }
            
            // prompt the user to press enter to go on to the next question
            System.out.print("\nPress enter to continue: ");
            s.nextLine(); // get input, no reason to store it
            
            // DELETE QUESTION ASKED TO PREVENT DUPLICATES
            proxyQuestionnaire = new String[questionnaire.length - 1][]; // proxy questionnaire array that will exclude asked question
            /*
            for loop initalizes i and j at 0, removes question asked from the array
            */
            for(int i = 0, j = 0; i < questionnaire.length; i++){
                // if i != questionNum, copy over the array and one to j
                // if i == questionNum, j++ does not happen, but i++ still does
                if(i != (questionNum)){
                    proxyQuestionnaire[j] = questionnaire[i];
                    j++;
                }
            }
            questionnaire = proxyQuestionnaire; // set the questionnaire array equal to the proxy questionnaire array so that it updates
        }
    } // end playKahootGame()
    
    
    
    /*
    this method declares the winner of the Kahoot Trivia game, 
    it ranks the top three players on a podium in order of most points attained
    */
    public static void declareWinner(){
        // declare and initialize array to store the number of points of each player
        int[] playerPoints = new int[4]; 
        
        // FOR PODIUM
        // declare and initialize array to store the original index of the top three players with the greatest points (index from the array of players objects)
        // indicies are in decreasing order of points
        int[] newOrder = new int[3]; 
        
        // declare and initialize new player list of KahootPlayer objects, will store the list of players in order of greatest points to least points
        KahootPlayer[] newPlayerList = new KahootPlayer[3];
        
        /*
        for loop gets the numbers of points of each player (in order of the players array) and adds it to the playerPoints array
        */
        for(int i = 0; i < players.length; i++){
            playerPoints[i] = players[i].getPoints();
        }
        
        /*
        for loop loops 3 times, to find the indicies of the top 3 players with the greatest number of points
        */
        for(int i = 0; i < 3; i++){
            // declare and initalize a variable to store the index of the player with the greatest points, set to 0 by default 
            int greatestPoints = 0;
            
            /*
            for loop compares the (number of points of each player after the first player) with (the player with the greatest number of points)
            if (any player after the first player) has more points than the (player with the greatest number of points) then greatestPoints (which
            stores the index of the player with the greatest points) will equal the index of the player that has the greatest number of points
            
            this continues until it goes through all of the players 
            */
            for(int j = 1; j < players.length; j++){
                if(playerPoints[j] > playerPoints[greatestPoints]){
                    greatestPoints = j;
                }
            }
            // this will store the index of the player with the greatest points
            newOrder[i] = greatestPoints;
            // that same player with greatest points will now have their number of points = -1, so that we can find the player with the next greatest number of points
            playerPoints[greatestPoints] = -1;
        }
        
        
        /*
        for loop creates a list of players in the sorted order of decreasing points
        */
        for(int i = 0; i < 3; i++){
            newPlayerList[i] = players[newOrder[i]];
        }
        players = newPlayerList; // set the original player list equal to the newly sorted one
        
        // output podium
        System.out.println("\n================================================================================"); // formatting
        System.out.println("👾  👥  👑  🐤  🦊  🐸  🐳  🐔  🐷  🦔  👾  👥  👑  🐤  🦊  🐸  🐳  🐔  🐷  🦔  👾  👥  👑  🐤  🦊  🐸  🐳 ");
        System.out.println("\n                                     PODIUM\n"); // output subheading 
        /*
        for loop prints the stats of each player (1st place to 3rd place)
        which includes the nickname, the number of questions got correct, and the number of points
        */
        for(int i = 0; i < 3; i++){
            System.out.println((i+1) + ": " + players[i].getNickname()); // output nickname
            System.out.println("    -️ (" + players[i].getQuestionsCorrect() + " out of 5)"); // output number of questions correct
            System.out.println("    - (POINTS: " + players[i].getPoints() + ")\n\n"); // output number of points got correct
        }
        
        players[0].wonGame();
        System.out.println("\n\n👾  👥  👑  🐤  🦊  🐸  🐳  🐔  🐷  🦔  👾  👥  👑  🐤  🦊  🐸  🐳  🐔  🐷  🦔  👾  👥  👑  🐤  🦊  🐸  🐳 ");
        System.out.println("================================================================================\n"); // formatting
    } // end declareWinner()
    
} // end of MyProgram