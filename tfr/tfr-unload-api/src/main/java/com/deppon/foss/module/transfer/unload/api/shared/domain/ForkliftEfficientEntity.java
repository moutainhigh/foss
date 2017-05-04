package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 *电叉个数配置数据，提供前台页面显示 
 ***/
public class ForkliftEfficientEntity {
	
	/**ID**/
	String id;
	/**转运场**/
	private String transfFieldName;
	/**转运场编码**/
	private String transfFieldCode;
	/**配置日期**/
	private Date configDate;
	/**生效起始日期**/
	private Date workFromDate;
	/**生效截至日期**/
	private Date workToDate;
	/**叉车数量**/
	private BigDecimal forkliftCount;
	/**创建人**/
	private String createPerson;
	/**创建人代码**/
	private String createPersonCode;
	/**创建时间**/
	private Date createDate;
	/**修改人**/
	private String modifyPerson;
	/**修改人代码**/
	private String modifyPersonCode;
	/**修改时间**/
	private Date modifyDate;
	/**状态**/
	private String active;
	
	
	public Date getWorkFromDate() {
		return workFromDate;
	}
	public void setWorkFromDate(Date workFromDate) {
		this.workFromDate = workFromDate;
	}
	public Date getWorkToDate() {
		return workToDate;
	}
	public void setWorkToDate(Date workToDate) {
		this.workToDate = workToDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTransfFieldName() {
		return transfFieldName;
	}
	public void setTransfFieldName(String transfFieldName) {
		this.transfFieldName = transfFieldName;
	}
	public String getTransfFieldCode() {
		return transfFieldCode;
	}
	public void setTransfFieldCode(String transfFieldCode) {
		this.transfFieldCode = transfFieldCode;
	}
	public Date getConfigDate() {
		return configDate;
	}
	public void setConfigDate(Date configDate) {
		this.configDate = configDate;
	}
	public BigDecimal getForkliftCount() {
		return forkliftCount;
	}
	public void setForkliftCount(BigDecimal forkliftCount) {
		this.forkliftCount = forkliftCount;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getCreatePersonCode() {
		return createPersonCode;
	}
	public void setCreatePersonCode(String createPersonCode) {
		this.createPersonCode = createPersonCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getModifyPerson() {
		return modifyPerson;
	}
	public void setModifyPerson(String modifyPerson) {
		this.modifyPerson = modifyPerson;
	}
	public String getModifyPersonCode() {
		return modifyPersonCode;
	}
	public void setModifyPersonCode(String modifyPersonCode) {
		this.modifyPersonCode = modifyPersonCode;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}

	
}
