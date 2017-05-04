package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;


/**
 * 
 * @author 200939
 *
 */
public class GGCheckNumberEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2295404994203115326L;

	/**
	 * 
	 */
	private String channelNumber;

    /**
     * 
     * @return
     */
	public String getChannelNumber() {
		return channelNumber;
	}

   /**
    * 
    * @param channelNumber
    */
	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}
	
	
}
