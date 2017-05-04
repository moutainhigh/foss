package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 图片开单Entity
 * ClassName: WaybillPictureEntity <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-10-14 上午10:37:17 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
public class WaybillPictureEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1317401798810648581L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 运单标识码
	 */
	private String waybillUuid;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 司机code
	 */
	private String driverCode;
	/**
	 * 是否大票货
	 */
	private String bigGoodsFlag;
	/**
	 * 是否现金
	 */
	private String cashPayFlag;
	/**
	 * 图片url
	 */
	private String filePath;
	/**
	 * 运单类型
	 */
	private String pendgingType;
	/**
	 * 状态
	 */
	private String active;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 创建人
	 */
	private String createUserCode;
	/**
	 * 创建人
	 */
	private String modifyUserCode;
	/**
	 * 所属机构
	 */
	private String belongOrgCode;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 设备号
	 */
	private String equipmentNo;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 收货部门
	 */
	private String receiveOrgCode;
	/**
	 * 开单开始时间
	 */
	private Date startBillTime;
	/**
	 * 开单结束时间
	 */
	private Date endBillTime;
	/**
	 * 百度ID
	 */
	private String baiDuId;
	/**
	 * 司机所在车队部门
	 */
	private String billOrgCode;
	/**
	 * 是否大件上楼加收
	 */
	private String isBigUp;
	/**
	 * 500KG到1000KG超重件数
	 */
	private Integer fhToOtOverQty;
	/**
	 * 1000KG到2000KG超重件数
	 */
	private Integer otToTtOverQty;
	/**
	 * 劳务费费率
	 */
	private BigDecimal serviceRate;
	/**
	 * 劳务费
	 */
	private BigDecimal serviceFee;
	
	/**
	 * 车牌号
	 */
	private String truckCode;
	
	/**
	 * 司机手机
	 * @return
	 */
	private String mobilephone;
	/**
	 * 理货员
	 */
	private String clearEmp;
	/**
	 * 理货员所在部门
	 */
	private String clearDept;
	/**
	 * 是否补录异常
	 */
	private String isException;
	/**
	 * 补录时间
	 */
	private Date makeupTime;
	/**
	 * 本地Y 异地 N
	 */
	private String local;
	/**
	 * 本属开单组
	 */
	private  String localBillGroupCode;
	
	/**	
	 * 是否展会货 Y 是  N 否
     */
	private String isExhibitCargo;
		
	public String getIsExhibitCargo() {
		return isExhibitCargo;
	}
	public void setIsExhibitCargo(String isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}
	
	/**
	 * 排除的运单
	 */
	private List<String> excludedWaybillNos;
	
	
	public String getBillOrgCode() {
		return billOrgCode;
	}
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}
	public String getBaiDuId() {
		return baiDuId;
	}
	public void setBaiDuId(String baiDuId) {
		this.baiDuId = baiDuId;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public Date getStartBillTime() {
		return startBillTime;
	}
	public void setStartBillTime(Date startBillTime) {
		this.startBillTime = startBillTime;
	}
	public Date getEndBillTime() {
		return endBillTime;
	}
	public void setEndBillTime(Date endBillTime) {
		this.endBillTime = endBillTime;
	}
	public String getEquipmentNo() {
		return equipmentNo;
	}
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWaybillUuid() {
		return waybillUuid;
	}
	public void setWaybillUuid(String waybillUuid) {
		this.waybillUuid = waybillUuid;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getBigGoodsFlag() {
		return bigGoodsFlag;
	}
	public void setBigGoodsFlag(String bigGoodsFlag) {
		this.bigGoodsFlag = bigGoodsFlag;
	}
	public String getCashPayFlag() {
		return cashPayFlag;
	}
	public void setCashPayFlag(String cashPayFlag) {
		this.cashPayFlag = cashPayFlag;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getPendgingType() {
		return pendgingType;
	}
	public void setPendgingType(String pendgingType) {
		this.pendgingType = pendgingType;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getBelongOrgCode() {
		return belongOrgCode;
	}
	public void setBelongOrgCode(String belongOrgCode) {
		this.belongOrgCode = belongOrgCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getIsBigUp() {
		return isBigUp;
	}
	public void setIsBigUp(String isBigUp) {
		this.isBigUp = isBigUp;
	}
	public Integer getFhToOtOverQty() {
		return fhToOtOverQty;
	}
	public void setFhToOtOverQty(Integer fhToOtOverQty) {
		this.fhToOtOverQty = fhToOtOverQty;
	}
	public Integer getOtToTtOverQty() {
		return otToTtOverQty;
	}
	public void setOtToTtOverQty(Integer otToTtOverQty) {
		this.otToTtOverQty = otToTtOverQty;
	}
	public BigDecimal getServiceRate() {
		return serviceRate;
	}
	public void setServiceRate(BigDecimal serviceRate) {
		this.serviceRate = serviceRate;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getClearEmp() {
		return clearEmp;
	}
	public void setClearEmp(String clearEmp) {
		this.clearEmp = clearEmp;
	}
	public String getClearDept() {
		return clearDept;
	}
	public void setClearDept(String clearDept) {
		this.clearDept = clearDept;
	}
	public String getIsException() {
		return isException;
	}
	public void setIsException(String isException) {
		this.isException = isException;
	}
	public Date getMakeupTime() {
		return makeupTime;
	}
	public void setMakeupTime(Date makeupTime) {
		this.makeupTime = makeupTime;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getLocalBillGroupCode() {
		return localBillGroupCode;
	}
	public void setLocalBillGroupCode(String localBillGroupCode) {
		this.localBillGroupCode = localBillGroupCode;
	}
	public List<String> getExcludedWaybillNos() {
		return excludedWaybillNos;
	}
	public void setExcludedWaybillNos(List<String> excludedWaybillNos) {
		this.excludedWaybillNos = excludedWaybillNos;
	}
	
	/**
	 * 是否特殊客户 Y 特殊  N 非特殊
	 * 是否空运
	 * 字段和WaybillPicturePdaDto中保持一致
	 * by 352676
	 * 2017年3月8日15:09:12
	 */
	private String  specialCustomer;

	public String getSpecialCustomer() {
		return specialCustomer;
	}
	public void setSpecialCustomer(String specialCustomer) {
		this.specialCustomer = specialCustomer;
	}
	
}
