
package com.deppon.foss.module.base.baseinfo.api.shared.define;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;


/**
 * @description 
 * @version 1.0
 * @author cbb
 * @update 2012-10-30 下午8:59:29 
 */
public class TimestampSerializer extends JsonSerializer<Timestamp> {

	@Override
	public void serialize(Timestamp timestamp, JsonGenerator generator,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(new Date(timestamp.getTime()));
		generator.writeString(dateStr);
	}

}
