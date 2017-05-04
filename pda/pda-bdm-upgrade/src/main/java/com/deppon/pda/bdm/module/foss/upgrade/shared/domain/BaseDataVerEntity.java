package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(数据版本实体类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-4-10 下午2:30:04,content:TODO </p>
 * @author chengang
 * @date 2013-4-10 下午2:30:04
 * @since
 * @version
 */
public class BaseDataVerEntity extends BaseEntity{
	
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
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