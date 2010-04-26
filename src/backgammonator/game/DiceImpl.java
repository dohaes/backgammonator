package backgammonator.game;

import java.util.Random;

import backgammonator.core.Dice;

/**
 * Represents implementation of the {@link Dice} interface.
 */
public final class DiceImpl implements Dice {
  
  private static final Random generator = new Random();
  private int die1;
  private int die2;

  /**
   * Creates new randomly generated pair of dice.
   */
  public DiceImpl() { }

  @Override
  public int getDie1() {
    return die1;
  }

  @Override
  public int getDie2() {
    return die2;
  }

  @Override
  public boolean isDouble() {
    return die1 == die2;
  }

  /**
   * Generates the next pair of dice.
   */
  void generateNext() {
    die1 = generator.nextInt(6) + 1;
    die2 = generator.nextInt(6) + 1;
  }
}