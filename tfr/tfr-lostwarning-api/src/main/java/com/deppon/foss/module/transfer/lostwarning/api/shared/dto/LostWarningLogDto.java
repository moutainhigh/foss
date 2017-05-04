package com.deppon.foss.module.transfer.lostwarning.api.shared.dto;

import java.util.Date;

/**
 * 丢货预警日志表
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：LostWarningLogDto
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-7-18 下午6:22:36
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class LostWarningLogDto {
	
	//运单号
	private String wayBillNo;
	
	//流水号
	private String serialNo;
	
	//上报方式编码
	private String repType;
	
	//上报场景： 1 零担 2 快递
	private String repScene;
	
	//上报结果: 1成功 0失败
	private String repCode;
	
	//上报结果备注
	private String repMsg;
	
	//丢货编号
	private String lostRepCode;
	
	//是否找到： 1已找到 0未找到
	private String isFind;
	
	//责任部门编码
	private String respDeptCode;
	
	//创建时间
	private Date createTime;
	
	//上报字符串
	private String uploadMsg;
	
	//ID
	private String ID;
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getRepType() {
		return repType;
	}
	public void setRepType(String repType) {
		this.repType = repType;
	}
	public String getRepScene() {
		return repScene;
	}
	public void setRepScene(String repScene) {
		this.repScene = repScene;
	}
	public String getRepCode() {
		return repCode;
	}
	public void setRepCode(String repCode) {
		this.repCode = repCode;
	}
	public String getRepMsg() {
		return repMsg;
	}
	public void setRepMsg(String repMsg) {
		this.repMsg = repMsg;
	}
	public String getLostRepCode() {
		return lostRepCode;
	}
	public void setLostRepCode(String lostRepCode) {
		this.lostRepCode = lostRepCode;
	}
	public String getIsFind() {
		return isFind;
	}
	public void setIsFind(String isFind) {
		this.isFind = isFind;
	}
	public String getRespDeptCode() {
		return respDeptCode;
	}
	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUploadMsg() {
		return uploadMsg;
	}
	public void setUploadMsg(String uploadMsg) {
		this.uploadMsg = uploadMsg;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
}
