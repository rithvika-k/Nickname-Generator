// class KahootPlayer, is a subclass of the Player class to inherit attributes and methods
public class KahootPlayer extends Player{
    
    // declare instance field (attribute) for objects specific to this subclass
    private int questionsCorrect; 
    
    // constructor method, inherits the player name as a String parameter, and the player points as an int parameter from the Player class
    public KahootPlayer(String playerName, int playerPoints){
        super(playerName, playerPoints); // the object will include the same attributes as objects from the superclass (Player)
        this.questionsCorrect = 0;  // this attribute (number of Kahoot questions got right) is automatically set to 0 when an object is created
    }
    
    // METHODS SPECIFIC TO KahootPlayer
    
    // this method gets the number of questions got right by the Player object and returns it
    int getQuestionsCorrect(){
        return this.questionsCorrect; // returns number of questions got right by the Player object
    }
    
    /*
    this method determines what happens if the user gets the question right
    if they get a question right, a certain number of points will be added to the users total number of points, 
    and the number of questions got right will be increment by 1
    */
    void gotQuestionRight(int ptsToAdd){
        this.setPoints(this.getPoints() + ptsToAdd); // set the points of the player object = the points it had before + points gained
        this.questionsCorrect += 1; // increment number of questions got correct by 1
    }
    
    // this method determines what happens if a player object wins the game
    void wonGame(){
        System.out.println("👑  Congrats " + this.getNickname() + ", you won the game! 👑 "); // output a winning message for the winning player object
    }
} // end of KahootPlayer class