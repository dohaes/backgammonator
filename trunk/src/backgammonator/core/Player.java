package backgammonator.core;

/**
 * Represents abstraction of the AI player. This interface has a method
 * getMove(BackgammonBoard, int, int):Move which should return the players move
 * according to the current configuration of the backgammon borad and the given dices.
 * The interface will have method endGame(boolen) which will be called by the GameManager
 * to indicate the end of the game. The boolean argument will show if this player wins or loses.
 * If a contestant wants to use the Backgamminator Librarty to test his AI, he has to implement this
 * interface and construct a GameManager object with instances of his implementation of this
 * interface given as agruments to the constructor.
 */

public interface Player {

}
