package com.deppon.esb.pojo.domain.foss2qms;

import java.io.Serializable;

public class ErrorResultItemEntity implements Serializable {

	private static final long serialVersionUID = -7041344235193543620L;

	//奖罚对象类别
	private String rapObjectCategory;
	//奖罚类型
	private String rapType;
	//奖罚员工工号或部门编码
	private String rapObjectCode;
	//奖罚员工姓名或部门名称
	private String rapObjectName;
	//奖罚金额
	private String money;
	
	/**
	 * rapObjectCategory <p>getter method</p>
	 * @author 150976
	 * @return  the rapObjectCategory
	 */
	public String getRapObjectCategory() {
		return rapObjectCategory;
	}
	/**
	 * rapObjectCategory <p>setter method</p>
	 * @author 150976
	 * @param rapObjectCategory the rapObjectCategory to set
	 */
	public void setRapObjectCategory(String rapObjectCategory) {
		this.rapObjectCategory = rapObjectCategory;
	}
	/**
	 * rapType <p>getter method</p>
	 * @author 150976
	 * @return  the rapType
	 */
	public String getRapType() {
		return rapType;
	}
	/**
	 * rapType <p>setter method</p>
	 * @author 150976
	 * @param rapType the rapType to set
	 */
	public void setRapType(String rapType) {
		this.rapType = rapType;
	}
	/**
	 * rapObjectCode <p>getter method</p>
	 * @author 150976
	 * @return  the rapObjectCode
	 */
	public String getRapObjectCode() {
		return rapObjectCode;
	}
	/**
	 * rapObjectCode <p>setter method</p>
	 * @author 150976
	 * @param rapObjectCode the rapObjectCode to set
	 */
	public void setRapObjectCode(String rapObjectCode) {
		this.rapObjectCode = rapObjectCode;
	}
	/**
	 * rapObjectName <p>getter method</p>
	 * @author 150976
	 * @return  the rapObjectName
	 */
	public String getRapObjectName() {
		return rapObjectName;
	}
	/**
	 * rapObjectName <p>setter method</p>
	 * @author 150976
	 * @param rapObjectName the rapObjectName to set
	 */
	public void setRapObjectName(String rapObjectName) {
		this.rapObjectName = rapObjectName;
	}
	/**
	 * money <p>getter method</p>
	 * @author 150976
	 * @return  the money
	 */
	public String getMoney() {
		return money;
	}
	/**
	 * money <p>setter method</p>
	 * @author 150976
	 * @param money the money to set
	 */
	public void setMoney(String money) {
		this.money = money;
	}
	
	
}
