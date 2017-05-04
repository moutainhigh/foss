package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.util.Date;
import java.util.List;

/**
 * 
* @description 智能分拣柜建包请求参数实体
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年4月23日 下午6:20:43
 */
public class RequestIntelligentSortParam {

	 //包号
	 private String packageNo;
	 //包目的站
	 private String destDeptCode;
	 //出发部门
	 private String orgCode;
	 //建包人工号
	 private String createUserCode;
	 //建包人姓名
	 private String createUserName;
	 //建包人部门编码
	 private String createDeptCode;
	 //建包人部门
	 private String createDeptName;
	 //创建时间
	 private Date createTime;
	 //结束时间
	 private Date finishTime;
	 //是否直达包
	 private String pkgType;
	 //设备号（格口号）
	 private String deviceNo;
	 //运单信息
	 private List<ExpressIntelligentSortDetailEntity> waybillInfos;
	 
	 
	 
	public List<ExpressIntelligentSortDetailEntity> getWaybillInfos() {
		return waybillInfos;
	}
	public void setWaybillInfos(
			List<ExpressIntelligentSortDetailEntity> waybillInfos) {
		this.waybillInfos = waybillInfos;
	}
	
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getDestDeptCode() {
		return destDeptCode;
	}
	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateDeptCode() {
		return createDeptCode;
	}
	public void setCreateDeptCode(String createDeptCode) {
		this.createDeptCode = createDeptCode;
	}
	public String getCreateDeptName() {
		return createDeptName;
	}
	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getPkgType() {
		return pkgType;
	}
	public void setPkgType(String pkgType) {
		this.pkgType = pkgType;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	 
	 
	 

}
