package it.essar.collector.tester;

import it.essar.collector.DataPoint;

/**
 * Extends the default data point class for testing purposes.
 * @author Steve Roberts
 */
class TestDataPoint extends DataPoint
{
	
	private static final long serialVersionUID = -3926591453114381258L;

	/**
	 * Creates a new data point with the specified collector ID.
	 * @param srcCollectorID a String containing the source collector ID.
	 */
	TestDataPoint(String srcCollectorID) {
		
		super();
		setSource(srcCollectorID);
		
	}
}
