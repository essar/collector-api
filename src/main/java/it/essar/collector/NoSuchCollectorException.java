package it.essar.collector;

/**
 * Exception class used to indicate that a collector cannot be found.
 * @author Steve Roberts
 */
public class NoSuchCollectorException extends Exception
{
	
	private static final long serialVersionUID = -8276442077359050072L;

	/**
	 * Creates a new object.
	 */
	public NoSuchCollectorException() {
		
		super();
		
	}
	
	/**
	 * Creates a new object with the specified message.
	 * @param message a message describing the failure.
	 */
	public NoSuchCollectorException(String message) {
		
		super(message);
		
	}
}
