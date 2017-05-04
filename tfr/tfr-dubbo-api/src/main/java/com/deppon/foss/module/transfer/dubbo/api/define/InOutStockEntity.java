/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.dubbo.api.define;

import java.util.Date;
import java.util.HashMap;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 记录出入库动作信息
 * @author 097457-dp-wangqiang
 * @date 2012-10-12 下午2:09:17
 */
public class InOutStockEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3038582085914182062L;
	
	/**
	* 主键ID
	*/
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNO;
	/**
	 * 流水号
	 */
	private String serialNO;
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	/**
	 * 货区名称
	 */
	private String goodsAreaName;
	/**
	 * 操作人编号
	 */
	private String operatorCode;
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	/**
	 * 出入库类型
	 */
	private String inOutStockType;
	/**
	 * 出入库单据号
	 */
	private String inOutStockBillNO;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 出入库时间
	 */
	private Date inOutStockTime;
	/**
	 * PDA扫描时间
	 */
	private Date scanTime;
	/**
	 * 部门编号
	 */
	private String orgCode;
	/**
	 * 动作是否有效
	 */
	private String isValid;
	/**
	 * PDA设备号
	 */
	private String pdaDeviceNO;
	
	/**
	 * 计划出发时间
	 */
	private Date planStartTime;
	/** 下一部门编号*/
	private String nextOrgCode;
	/** 备注*/
	private String notes;
	/**
	* 库位
	*/
	private String position;
	
	/**
	 *卸车部门：二程接驳 卸车专用 
	 ***/
	private String unloadSCOrgCode;
	
	/**
	 * 卸车类型：二程接驳 卸车专用 
	 * **/
	private String unloadSCType;
	
	/**
	* 仅供StockService.verifyInOutStockInfo 方法 调用。 用于更新走货路径的状态为入库
	*/
	private boolean updateTransportPathActionForInstoreFlag;
	/**
	* 仅供StockService类调用
	* 货区的查询类型
	*/
	private String goodsAreaAdd;
	
	//是否建包扫描
	private String bePackage;
	/**
	 * 推送给ptp的运单Map
	 */
	private HashMap<String,String> pushMapToPtp;
	
	/**
	 * 推送给ptp的运单Map
	 * @return  the pushMapToPtp
	 */
	public HashMap<String, String> getPushMapToPtp() {
		return pushMapToPtp;
	}

	/**
	 * 推送给ptp的运单Map
	 * @param pushMapToPtp the pushMapToPtp to set
	 */
	public void setPushMapToPtp(HashMap<String, String> pushMapToPtp) {
		this.pushMapToPtp = pushMapToPtp;
	}

	public String getBePackage() {
		return bePackage;
	}

	public void setBePackage(String bePackage) {
		this.bePackage = bePackage;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNO() {
		return waybillNO;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param waybillNO the new 运单号
	 */
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}
	
	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNO() {
		return serialNO;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNO the new 流水号
	 */
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	
	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 设置 货区编号.
	 *
	 * @param goodsAreaCode the new 货区编号
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	
	/**
	 * 获取 货区名称.
	 *
	 * @return the 货区名称
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	
	/**
	 * 设置 货区名称.
	 *
	 * @param goodsAreaName the new 货区名称
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	
	/**
	 * 获取 操作人编号.
	 *
	 * @return the 操作人编号
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	
	/**
	 * 设置 操作人编号.
	 *
	 * @param operatorCode the new 操作人编号
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	
	/**
	 * 获取 操作人姓名.
	 *
	 * @return the 操作人姓名
	 */
	public String getOperatorName() {
		return operatorName;
	}
	
	/**
	 * 设置 操作人姓名.
	 *
	 * @param operatorName the new 操作人姓名
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	/**
	 * 获取 出入库类型.
	 *
	 * @return the 出入库类型
	 */
	public String getInOutStockType() {
		return inOutStockType;
	}
	
	/**
	 * 设置 出入库类型.
	 *
	 * @param inOutStockType the new 出入库类型
	 */
	public void setInOutStockType(String inOutStockType) {
		this.inOutStockType = inOutStockType;
	}
	
	/**
	 * 获取 出入库单据号.
	 *
	 * @return the 出入库单据号
	 */
	public String getInOutStockBillNO() {
		return inOutStockBillNO;
	}
	
	/**
	 * 设置 出入库单据号.
	 *
	 * @param inOutStockBillNO the new 出入库单据号
	 */
	public void setInOutStockBillNO(String inOutStockBillNO) {
		this.inOutStockBillNO = inOutStockBillNO;
	}
	
	/**
	 * 获取 设备类型.
	 *
	 * @return the 设备类型
	 */
	public String getDeviceType() {
		return deviceType;
	}
	
	/**
	 * 设置 设备类型.
	 *
	 * @param deviceType the new 设备类型
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	/**
	 * 获取 出入库时间.
	 *
	 * @return the 出入库时间
	 */
	public Date getInOutStockTime() {
		return inOutStockTime;
	}
	
	/**
	 * 设置 出入库时间.
	 *
	 * @param inOutStockTime the new 出入库时间
	 */
	public void setInOutStockTime(Date inOutStockTime) {
		this.inOutStockTime = inOutStockTime;
	}
	
	/**
	 * 获取 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 部门编号.
	 *
	 * @param orgCode the new 部门编号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 获取 pDA扫描时间.
	 *
	 * @return the pDA扫描时间
	 */
	public Date getScanTime() {
		return scanTime;
	}
	
	/**
	 * 设置 pDA扫描时间.
	 *
	 * @param scanTime the new pDA扫描时间
	 */
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	
	/**
	 * 获取 动作是否有效.
	 *
	 * @return the 动作是否有效
	 */
	public String getIsValid() {
		return isValid;
	}
	
	/**
	 * 设置 动作是否有效.
	 *
	 * @param isValid the new 动作是否有效
	 */
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	
	/**
	 * 获取 pDA设备号.
	 *
	 * @return the pDA设备号
	 */
	public String getPdaDeviceNO() {
		return pdaDeviceNO;
	}
	
	/**
	 * 设置 pDA设备号.
	 *
	 * @param pdaDeviceNO the new pDA设备号
	 */
	public void setPdaDeviceNO(String pdaDeviceNO) {
		this.pdaDeviceNO = pdaDeviceNO;
	}
	
	/**
	 * 获取 计划出发时间.
	 *
	 * @return the 计划出发时间
	 */
	public Date getPlanStartTime() {
		return planStartTime;
	}
	
	/**
	 * 设置 计划出发时间.
	 *
	 * @param planStartTime the new 计划出发时间
	 */
	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}
	
	/**
	 * 获取 下一部门编号.
	 *
	 * @return the 下一部门编号
	 */
	public String getNextOrgCode() {
		return nextOrgCode;
	}
	
	/**
	 * 设置 下一部门编号.
	 *
	 * @param nextOrgCode the new 下一部门编号
	 */
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}

	/**
	 * 获取 备注.
	 *
	 * @return the 备注
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * 设置 备注.
	 *
	 * @param notes the new 备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	* @return
	* @description 获取状态值
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 上午10:10:49
	*/
	public boolean getUpdateTransportPathActionForInstoreFlag() {
		return updateTransportPathActionForInstoreFlag;
	}

	/**
	* @param updateTransportPathActionForInstoreFlag
	* @description 设置状态值
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 上午10:10:52
	*/
	public void setUpdateTransportPathActionForInstoreFlag(
			boolean updateTransportPathActionForInstoreFlag) {
		this.updateTransportPathActionForInstoreFlag = updateTransportPathActionForInstoreFlag;
	}

	/**
	* @return
	* @description 获取库位
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 上午10:10:56
	*/
	public String getPosition() {
		return position;
	}

	/**
	* @param position
	* @description 设置库位
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 上午10:11:00
	*/
	public void setPosition(String position) {
		this.position = position;
	}
	
	/** 
	* 获取主键ID
	*/
	public String getId() {
		return id;
	}

	public String getUnloadSCOrgCode() {
		return unloadSCOrgCode;
	}

	public void setUnloadSCOrgCode(String unloadSCOrgCode) {
		this.unloadSCOrgCode = unloadSCOrgCode;
	}

	/**
	* 设置主键id
	*/
	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsAreaAdd() {
		return goodsAreaAdd;
	}

	public void setGoodsAreaAdd(String goodsAreaAdd) {
		this.goodsAreaAdd = goodsAreaAdd;
	}

	public String getUnloadSCType() {
		return unloadSCType;
	}

	public void setUnloadSCType(String unloadSCType) {
		this.unloadSCType = unloadSCType;
	}
	
}