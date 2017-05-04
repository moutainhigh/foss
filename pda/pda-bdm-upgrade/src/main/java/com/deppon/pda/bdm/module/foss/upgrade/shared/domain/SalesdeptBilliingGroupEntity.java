package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:wenwuneng,date:2013-8-10 下午4:22:31,content:TODO </p>
 * @author wenwuneng
 * @date 2013-8-10 下午4:22:31
 * @since
 * @version
 */
public class SalesdeptBilliingGroupEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	private String virtualcode;
	private String salesdeptcode;
	private String salesdeptname;
	private String billinggroupcode;
	private String billinggroupname;
	private String operFlag;
	
	
	public String getVirtualcode() {
		return virtualcode;
	}
	public void setVirtualcode(String virtualcode) {
		this.virtualcode = virtualcode;
	}
	public String getSalesdeptcode() {
		return salesdeptcode;
	}
	public void setSalesdeptcode(String salesdeptcode) {
		this.salesdeptcode = salesdeptcode;
	}
	public String getSalesdeptname() {
		return salesdeptname;
	}
	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
	}
	public String getBillinggroupcode() {
		return billinggroupcode;
	}
	public void setBillinggroupcode(String billinggroupcode) {
		this.billinggroupcode = billinggroupcode;
	}
	public String getBillinggroupname() {
		return billinggroupname;
	}
	public void setBillinggroupname(String billinggroupname) {
		this.billinggroupname = billinggroupname;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	
}
