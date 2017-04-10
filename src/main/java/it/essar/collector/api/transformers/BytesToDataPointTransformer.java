package it.essar.collector.api.transformers;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import it.essar.collector.DataPoint;

public class BytesToDataPointTransformer extends AbstractMessageTransformer
{

	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		try {
			
			// Create a new data point based on the byte data
			DataPoint dp = new DataPoint(message.getPayloadAsBytes());
        
			return dp;
			
		} catch(Exception e) {
			
			throw new TransformerException(this, e);
			
		}
    }
}
