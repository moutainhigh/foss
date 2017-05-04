package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;
/**
 * 非全打包装是否需要生成待办Dto
 * @author Foss-105888-Zhangxingwang
 * @date 2014-4-3 17:38:10
 *
 */
public class NeedCreateTodoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 需要生成待办的流水号集合
	 */
	private List<String> needCreateSerialList;
	/**
	 * 用户所在部门编码
	 */
	private String userOrgCode;
	/**
	 * 用户所在部门名称
	 */
	private String userOrgName;
	/**
	 * 用户编码
	 */
	private String userCode;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 修改运单对应的件数
	 */
	private Integer newGoodNum;
	/**
	 * 运单原来的件数
	 */
	private Integer oldGoodNum;
	
	//set、get方法
	public Integer getOldGoodNum() {
		return oldGoodNum;
	}
	public void setOldGoodNum(Integer oldGoodNum) {
		this.oldGoodNum = oldGoodNum;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public List<String> getNeedCreateSerialList() {
		return needCreateSerialList;
	}
	public void setNeedCreateSerialList(List<String> needCreateSerialList) {
		this.needCreateSerialList = needCreateSerialList;
	}
	public String getUserOrgCode() {
		return userOrgCode;
	}
	public void setUserOrgCode(String userOrgCode) {
		this.userOrgCode = userOrgCode;
	}
	public String getUserOrgName() {
		return userOrgName;
	}
	public void setUserOrgName(String userOrgName) {
		this.userOrgName = userOrgName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getNewGoodNum() {
		return newGoodNum;
	}
	public void setNewGoodNum(Integer newGoodNum) {
		this.newGoodNum = newGoodNum;
	}
}
