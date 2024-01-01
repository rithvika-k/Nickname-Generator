# Nickname-Generator

Customized nickname generator. 

-- Kahoot Triva Simulator 
The player is given a list of 25 uniquely generated nicknames. The nickname will be made of two adjectives and an animal name. For example, “Crazy Fun
Puppy” can be one of the nicknames generated. Here, you will 
also be able to add, search, and or delete from the list of nicknames. Each player
will then pick a player nickname and then proceed to answer 5 triva questions. 
After each question, points will be awarded and a scoreboard will be 
displayed. At the end of the game a podium with the top three players will be 
displayed. The objective is to get the most amount of questions right to WIN!

REQUIRED BASICS: Here are the program specifications:
1. There are 3 text files:
a. A file with 10 adjectives
b. Another file with 10 adjectives, each different from the first file
c. A third file with 10 animals
2. The program generates 25 nicknames by randomly selecting a word from each file,
and then combining them to make the nickname.
3. The nicknames that are generated are displayed in alphabetical order, ignoring
case sensitivity.
4. A search function to allow the user to enter a nickname to see if it was generated.
5. An option to either add to the list of nicknames or to delete one of the nicknames

NOTES (FEATURES ADDED)
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
