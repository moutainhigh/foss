/**
 *
 */
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 货物轨迹Dto
 * @author 231438 chenjunying
 * 2015-4-14 下午4:12:13
 */
public class WaybillTrackDto implements Serializable  {
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 操作类型
	 */
	private String operateType;
	/**
	 * 当前登陆信息
	 */
	private CurrentInfo currentInfo;
	/**
	 * 操作人名称/收件人名称
	 */
	private String operatorName;
	/**
	 * 操作人电话/收件人电话
	 */
	private String operatorPhone;
	/**
	 * 操作描述/内容
	 */
	private String operateDesc;
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the operateType
	 */
	public String getOperateType() {
		return operateType;
	}
	/**
	 * @param operateType the operateType to set
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	/**
	 * @return the currentInfo
	 */
	public CurrentInfo getCurrentInfo() {
		return currentInfo;
	}
	/**
	 * @param currentInfo the currentInfo to set
	 */
	public void setCurrentInfo(CurrentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}
	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return the operatorPhone
	 */
	public String getOperatorPhone() {
		return operatorPhone;
	}
	/**
	 * @param operatorPhone the operatorPhone to set
	 */
	public void setOperatorPhone(String operatorPhone) {
		this.operatorPhone = operatorPhone;
	}
	/**
	 * @return the operateDesc
	 */
	public String getOperateDesc() {
		return operateDesc;
	}
	/**
	 * @param operateDesc the operateDesc to set
	 */
	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}
	
}
