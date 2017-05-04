package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * foss 返回请车趟数给TPS 
 * 2014-12-29
 */
@XmlRootElement(name="TotalNumberEntityEntity")
public class TotalNumberEntityEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 588381751040703634L;

	/**当年的发车次数*/
	private long currentyear;
	
	/**当月的发车次数*/
	private long currentmonth;
	
	public long getCurrentyear() {
		return currentyear;
	}
	public void setCurrentyear(long currentyear) {
		this.currentyear = currentyear;
	}
	public long getCurrentmonth() {
		return currentmonth;
	}
	public void setCurrentmonth(long currentmonth) {
		this.currentmonth = currentmonth;
	}
	
}
