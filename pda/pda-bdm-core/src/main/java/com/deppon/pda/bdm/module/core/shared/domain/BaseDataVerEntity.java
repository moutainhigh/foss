package com.deppon.pda.bdm.module.core.shared.domain;

import java.util.Date;

/**
 * 
  * @ClassName BaseDataVerEntity 
  * @Description 基础数据下载实体类 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class BaseDataVerEntity extends DomainEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4425853378974538479L;
	/**
	 * 数据版本
	 */
	private String dataVer;
	/**
	 * 更新日期
	 */
	private Date updDate;


	public String getDataVer() {
		return dataVer;
	}

	public void setDataVer(String dataVer) {
		this.dataVer = dataVer;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
}