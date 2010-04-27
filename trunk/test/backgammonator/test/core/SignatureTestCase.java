package backgammonator.test.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Point;
import backgammonator.impl.game.BackgammonBoardImpl;
import backgammonator.impl.game.DiceImpl;
import backgammonator.impl.game.PointImpl;

/**
 * Tests if th eobjects accessible to the test user have any visible methods
 * except those in the interface defined.
 */
@SuppressWarnings("unchecked")
public class SignatureTestCase extends TestCase {

	private Method[] methodsFromImpl;
	private Field[] fieldsFromImpl;
	private Method[] methodsFromInterface;
	
	private static Method[] methodsFromObject = Object.class.getMethods();

	public void testBackgammonBoard() {
		initAll(BackgammonBoardImpl.class, BackgammonBoard.class);
		assertSignatures();
	}

	public void testDice() {
		initAll(DiceImpl.class, Dice.class);
		assertSignatures();
	}

	public void testPoint() {
		initAll(PointImpl.class, Point.class);
		assertSignatures();
	}

	private void initAll(Class clazz, Class interfacce) {
		methodsFromInterface = interfacce.getMethods();
		methodsFromImpl = clazz.getMethods();
		fieldsFromImpl = clazz.getFields();
	}

	private void assertSignatures() {
		// assert public fields
		for (int i = 0; i < fieldsFromImpl.length; i++) {
			if (!fieldsFromImpl[i].getDeclaringClass().isInterface()) {
				fail("The public filed [" + fieldsFromImpl[i]
						+ "] is not from implemented interface");
			}
		}
		// assert public methods
		for (int i = 0; i < methodsFromImpl.length; i++) {
			if (!isFrom(methodsFromInterface, methodsFromImpl[i])
					&& !isFrom(methodsFromObject, methodsFromImpl[i])) {
				fail("Method ["
						+ methodsFromImpl[i]
						+ "] is not from implemented inteface or java.lang.Object!");
			}
		}
	}

	private boolean isFrom(Method[] methods, Method method) {
		Method currenti = null;// next method from the implemented interfaces
		for (int i = 0; i < methods.length; i++) {
			currenti = methods[i];
			if (method.getName().equals(currenti.getName())
					&& assertExceptions(method.getExceptionTypes(), currenti
							.getExceptionTypes())
					&& assertReturnTypes(method.getReturnType(), currenti
							.getReturnType())
					&& assertParameterTypes(method.getParameterTypes(),
							currenti.getParameterTypes())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Exceptions should be subset of Exceptions ei
	 */
	private boolean assertExceptions(Class[] ec, Class[] ei) {
		boolean in;
		for (int i = 0; i < ec.length; i++) {
			in = false;
			for (int j = 0; j < ei.length; j++) {
				if (isInHeirarchy(ec[i], ei[j])) {
					in = true;
					break;
				}
			}
			if (in == false) {
				return false;
			}
		}
		return true;
	}

	private boolean assertReturnTypes(Class c, Class i) {
		return isInHeirarchy(c, i);
	}

	/**
	 * Goes down in the heirarchy of class clazz (DFS) and returns true if -
	 * class clazz is same as class interfacce - class interfacce is super class
	 * for c, if interfacce is not an interface - class clazz extends some class
	 * c1 (down in the classes hierarchy) which implements interfacce, if
	 * interfacce is an interface
	 */
	private boolean isInHeirarchy(Class clazz, Class interfacce) {
		if (clazz == null)
			return false;
		if (clazz == interfacce)
			return true;

		Class superclass = clazz.getSuperclass();
		if (isInHeirarchy(superclass, interfacce))
			return true;

		Class[] interfaces = clazz.getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			if (isInHeirarchy(interfaces[i], interfacce))
				return true;
		}

		return false;
	}

	/**
	 * The formal parameter type (as sequence) in the signature of the method
	 * from the interface must be the same as they are in the class that
	 * implements it
	 */
	private boolean assertParameterTypes(Class[] i, Class[] c) {
		if (i.length != c.length) {
			return false;
		}
		for (int j = 0; j < i.length; j++) {
			if (i[j] != c[j]) {
				return false;
			}
		}
		return true;
	}
}
