
package com.deppon.foss.module.base.baseinfo.api.shared.define;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;


/**
 * @description 
 * @version 1.0
 * @author cbb
 * @update 2012-10-30 下午8:59:21 
 */
public class TimestampDeserializer extends JsonDeserializer<Timestamp> {

	@Override
	public Timestamp deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String text = parser.getText();
		Timestamp timestamp = stringToTimestamp(text, "yyyy-MM-dd HH:mm:ss");
		return timestamp;
	}
	
	private Timestamp stringToTimestamp(String dateStr, String dateFormat)
	{
		if(dateStr == null || "".equals(dateStr.trim()))
		{
			return null;
		}
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			return new Timestamp(df.parse(dateStr).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
