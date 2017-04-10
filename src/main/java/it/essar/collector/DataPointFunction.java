package it.essar.collector;

/**
 * Interface that defines a method for extracting a value from a DataPoint.
 * @author Steve Roberts
 */
interface DataPointFunction
{

	/**
	 * Extract a value from a data point.
	 * @param dp the data point to use.
	 * @return the value extracted.
	 */
	public double extract(DataPoint dp);
	
}
