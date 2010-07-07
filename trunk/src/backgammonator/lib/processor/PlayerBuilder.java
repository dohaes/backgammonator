package backgammonator.lib.processor;

import java.io.File;

import backgammonator.lib.game.Player;

/**
 * Represents interface for compiling sources and building {@link Player}
 * objects from them. Implementations of this interface should be created for
 * each programming language that is to be integrated with the system.
 */
public interface PlayerBuilder {

	/**
	 * Maps to a {@link PlayerBuilder} for C/C++ sources.
	 */
	int C = 0;

	/**
	 * Maps to a {@link PlayerBuilder} for Java sources.
	 */
	int JAVA = 1;

	/**
	 * Maps to a {@link PlayerBuilder} for Python sources.
	 */
	int PYTHON = 2;

	/**
	 * Compiles the given source file and builds a {@link Player} object from
	 * for it.
	 * 
	 * @param source the file that contains the source code.
	 * @return a new instance of {@link Player} implementation.
	 * @throws PlayerBuilderException if any errors occur.
	 */
	Player build(File source) throws PlayerBuilderException;

}
