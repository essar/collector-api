package it.essar.collector;

import java.util.LinkedList;
import java.util.List;

/**
 * Class defining a range of methods for use with Lists.
 * @author Steve Roberts
 */
interface ListFunctions
{

	/**
	 * Calculates the average value from a list of data.
	 * @param data the data to process.
	 * @param df the function to extract a value from the data points.
	 * @return the average value.
	 */
	public static double avg(List<DataPoint> data, DataPointFunction df) {

		return data == null || data.size() == 0 ? 0.0 : sum(data, df) / data.size();
		
	}
	
	/**
	 * Maps a list of data to another list of values.
	 * @param data the data to process.
	 * @param df the function to extract a value from the data points.
	 * @return a list of values.
	 */
	public static List<Double> map(List<DataPoint> data, DataPointFunction df) {
		
		List<Double> out = new LinkedList<>();
		
		for(DataPoint dp : data) {
			
			out.add(df.extract(dp));
			
		}
		
		return out;
		
	}
	
	/**
	 * Calculates the maximum value from a list of data.
	 * @param data the data to process.
	 * @param df the function to extract a value from the data points.
	 * @return the maximum value.
	 */
	public static double max(List<DataPoint> data, DataPointFunction df) {

		return reduce(data, df, (d1, d2) -> Math.max(d1, d2));
		
	}
	
	/**
	 * Calculates the minimum value from a list of data.
	 * @param data the data to process.
	 * @param df the function to extract a value from the data points.
	 * @return the minimum value.
	 */
	public static double min(List<DataPoint> data, DataPointFunction df) {

		return reduce(data, df, (d1, d2) -> Math.min(d1, d2));
		
	}
	
	/**
	 * Reduces a list of data from a list to a scalar value.
	 * @param data the data to process.
	 * @param df the function to extract a value from the data points.
	 * @param mf the mapping function to reduce the list.
	 * @return the reduced value.
	 */
	public static double reduce(List<DataPoint> data, DataPointFunction df, MapFunction mf) {

		if(data == null || data.size() == 0) {
			
			return 0.0;
			
		}
		
		double v = df.extract(data.get(0));
		for(int i = 1; i < data.size(); i ++) {
			
			v = mf.map(v, df.extract(data.get(i)));
			
		}

		return v;
		
	}
	
	/**
	 * Calculates the sum of a list of data.
	 * @param data the data to process.
	 * @param df the function to extract a value from the data points.
	 * @return the sum of the values.
	 */
	public static double sum(List<DataPoint> data, DataPointFunction df) {
		
		double sum = 0.0;
		for(DataPoint dp : data) {
			
			sum += df.extract(dp);
			
		}
		return sum;
		
	}
}
