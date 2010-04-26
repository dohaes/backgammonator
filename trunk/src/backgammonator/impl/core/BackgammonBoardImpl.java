package backgammonator.impl.core;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.CheckerMove;
import backgammonator.core.PlayerColor;
import backgammonator.core.PlayerMove;
import backgammonator.core.Point;

/**
 * Represents implementation of the {@link BackgammonBoard} interface
 * TODO maybe use pooling ?
 */
public final class BackgammonBoardImpl implements BackgammonBoard {

  /**
   * Positions 0..23 are the points of the board from the view of the white
   * player. 24 and 25 are the points of the hit white and black checkers and
   * 26 and 27 are the points of the born off white and black checkers.
   */
  private PointImpl[] board;

  public BackgammonBoardImpl() {
    board = new PointImpl[28];
  }

  @Override
  public Point getPoint(int point, PlayerColor color) {
    return getPoint0(point, color);
  }

  @Override
  public int getHits(PlayerColor color) {
    return (color.equals(PlayerColor.WHITE) ? board[HIT_WHITE]
        : board[HIT_BLACK]).getCount();
  }

  @Override
  public int getBornOff(PlayerColor color) {
    return (color.equals(PlayerColor.WHITE) ? board[BORN_WHITE]
        : board[BORN_BLACK]).getCount();
  }

  void setPoint(int point, PlayerColor color, int count) {
    if (point <= 0 || point > MAX_POINTS) {
      throw new IllegalArgumentException("Illegal position: " + point);
    }
    if (count < 0 || count > MAX_CHECKERS) {
      throw new IllegalArgumentException("Illegal count: " + count);
    }
    if (color.equals(PlayerColor.WHITE)) {
      board[point - 1] = new PointImpl(count, color);
    } else {
      board[MAX_POINTS - point] = new PointImpl(count, color);
    }
  }

  void setHits(PlayerColor color, int count) {
    if (count < 0 || count > MAX_CHECKERS) {
      throw new IllegalArgumentException("Illegal count: " + count);
    }
    if (color.equals(PlayerColor.WHITE)) {
      board[HIT_WHITE] = new PointImpl(count, color);
    } else {
      board[HIT_BLACK] = new PointImpl(count, color);
    }
  }

  void setBornOff(PlayerColor color, int count) {
    if (count < 0 || count > MAX_CHECKERS) {
      throw new IllegalArgumentException("Illegal count: " + count);
    }
    if (color.equals(PlayerColor.WHITE)) {
      board[BORN_WHITE] = new PointImpl(count, color);
    } else {
      board[BORN_BLACK] = new PointImpl(count, color);
    }
  }
  
  private PointImpl getPoint0(int point, PlayerColor color) {
    if (point <= 0 || point > MAX_POINTS) {
      throw new IllegalArgumentException("Illegal position: " + point);
    }
    return color.equals(PlayerColor.WHITE) ? board[point - 1]
        : board[MAX_POINTS - point];
  }

  void makeMove(PlayerMove move) {
    CheckerMove m = null;
    PlayerColor color = move.getPlayerColor();
    m = move.getCheckerMove(1);
    makeMove(color, m.getStartPoint(), m.getStartPoint()
        + m.getMoveLength());
    m = move.getCheckerMove(2);
    makeMove(color, m.getStartPoint(), m.getStartPoint()
        + m.getMoveLength());
    if (move.isDouble()) {
      m = move.getCheckerMove(3);
      makeMove(color, m.getStartPoint(), m.getStartPoint()
          + m.getMoveLength());
      m = move.getCheckerMove(4);
      makeMove(color, m.getStartPoint(), m.getStartPoint()
          + m.getMoveLength());
    }
  }

  private void makeMove(PlayerColor color, int from, int to) {
    PointImpl source = getPoint0(from, color);
    source.decrease();
    if (to > MAX_POINTS) {
      to = color.equals(PlayerColor.WHITE) ? BORN_WHITE : BORN_BLACK;
    }
    PointImpl dest = getPoint0(to, color);
    if (dest.increase(color)) {
      setHits(color.oposite(), getHits(color));
    }
  }
}