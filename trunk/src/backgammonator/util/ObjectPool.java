package backgammonator.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents implementation of object pool.
 */
public final class ObjectPool {
	
	private int size;
	private List instances;
	private Class clazz;
	
	/**
	 * Creates an object pool with the given size and type of objects.
	 * The implementations of the given class must provide a default constructor.	
	 * @param size the size of the pool
	 * @param clazz the class of the pooled objects
	 */
	public ObjectPool (int size, Class clazz) {
		this.size = size;
		this.clazz = clazz;
		instances = new ArrayList(size);
	}
	
	/**
	 * Borrows object from the pool.
	 * If the pool is empty a new instance of the object is created.
	 * @return the borrowed object
	 */
	public Object borrowObject() {
		int currentSize = instances.size();
		if (currentSize == size)  {
			try {
				return clazz.newInstance();
			} catch (InstantiationException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			}
		} 
		else return instances.remove(currentSize - 1);
	}
	
	/**
	 * Returns an object to the pool.
	 * If the pool is full or the object is not instance of the provided class nothing is done.
	 * @param object the object to return
	 */
	public void returnObject(Object object) {
		int currentSize = instances.size();
		if (currentSize == size) return; //pool is full
		if (object.getClass() != clazz) return; //invalid type for the object
		instances.add(object);
		
	}
	

}
