package com.deppon.pda.bdm.module.core.shared.domain;

import java.io.Serializable;

/**
 * 实体基类
 * 
 * @author gaojia
 * @date Sep 6,2012 10:00:30 AM
 * @version 1.0
 * @since
 */
public class DomainEntity implements Serializable {

	/**
	 * @description
	 */      
	private static final long serialVersionUID = -3830145893270403418L;
	/**
	 * 流水号
	 */
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}