package it.essar.collector.api.transformers;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import it.essar.collector.DataPoint;

public class DataPointToBytesTransformer extends AbstractMessageTransformer
{

	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		try {
			
			DataPoint dp = message.getPayload(DataPoint.class);
			
			// Get a byte array from the DataPoint message
			byte[] bytes = dp == null ? new byte[0] : dp.toBytes();
			
			return bytes;
			
		} catch(Exception e) {
			
			throw new TransformerException(this, e);
			
		}
    }
}
