package it.essar.collector;

/**
 * Exception class used to indicate that a group cannot be found.
 * @author Steve Roberts
 *
 */
public class NoSuchGroupException extends Exception
{
	
	private static final long serialVersionUID = 8604081825004583513L;

	/**
	 * Creates a new object.
	 */
	public NoSuchGroupException() {
		
		super();
		
	}
	
	/**
	 * Creates a new object with the specified message.
	 * @param message a message describing the failure.
	 */
	public NoSuchGroupException(String message) {
		
		super(message);
		
	}
}
