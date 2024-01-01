// class Player
public class Player{
    
    // declare instance fields (attributes) for objects of this class
    private String nickname;
    private int points;
    
    // constructor method, has the player name as a String parameter, and the player points as an int parameter
    public Player(String playerName, int playerPoints){
        // set the values for each of the instance fields/attributes based on the arguments passed in
        this.nickname = playerName;
        this.points = playerPoints;
    }
    
    // this method gets the nickname of the Player object and returns it
    String getNickname(){
        return this.nickname; // returns nickname of the Player object
    }
    
    // this method gets the number of points of the Player object and returns it
    int getPoints(){
        return this.points; // returns number of points of the Player object
    }
    
    // set method created for nickname attribute in order to make class as general as possible 
    // (even though its not used in this program specifically)
    // this method sets the nickname of the Player object to a new nickname
    void setNicknames(String newNickname){
        this.nickname = newNickname; // changes the nickname of the player object to the new nickname
    }
    
    // this method sets the number of points of the Player object to a new number of points
    void setPoints(int newPoints){
        this.points = newPoints; // changes number of points of the Player object to a new number of points
    }
} // end of Player class