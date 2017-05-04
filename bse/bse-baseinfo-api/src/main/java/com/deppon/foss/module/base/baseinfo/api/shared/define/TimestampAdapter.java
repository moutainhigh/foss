
package com.deppon.foss.module.base.baseinfo.api.shared.define;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;



/**
 * @description 
 * @version 1.0
 * @author cbb
 * @update 2012-10-30 下午8:59:10 
 */
public class TimestampAdapter extends XmlAdapter<Date, Timestamp> {

	@Override
	public Date marshal(Timestamp t) throws Exception {
		return new Date(t.getTime());
	}

	@Override
	public Timestamp unmarshal(Date date) throws Exception {
		return new Timestamp(date.getTime());
	}

}
