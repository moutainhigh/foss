package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
* @ClassName: FindGoodsAdminDetailEntity
* @Description: 找货明细Entity
* @author 189284--ZX
* @date 2015-7-10 下午2:08:56
*
 */
public class FindGoodsAdminDetailEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 任务编号（返给Pda的）
	 */
	String taskNo;
	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 流水号
	 */
	private String serialNo;

	/**
	 * 丢货件数
	 */
	private Long lostGoodsQty;

	/**
	 * 总件数
	 */
	private Long totalQty;

	/**
	 * 重量
	 */
	private BigDecimal weight;

	/**
	 * 体积
	 */
	private BigDecimal volume;

	/**
	 * 包装
	 */
	private String packageType;

	/**
	 * 提货网点
	 */
	private String destOrgCode;
	/**
	 * 提货网点名称
	 */
	private String destOrgName;
	/**
	 * 是否找到(Y是 ,N否)
	 */
	private String findType;
	/**
	 * 上报时间
	 */
	private Date reportDate;
	private String createUserCode;
	private String modifyUserCode;
	/**
	 * 获取 运单号
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * 设置  运单号
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * 获取 流水号
	 * @return serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * 设置  流水号
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * 获取 丢货件数
	 * @return lostGoodsQty
	 */
	public Long getLostGoodsQty() {
		return lostGoodsQty;
	}
	/**
	 * 设置 丢货件数
	 * @param lostGoodsQty the lostGoodsQty to set
	 */
	public void setLostGoodsQty(Long lostGoodsQty) {
		this.lostGoodsQty = lostGoodsQty;
	}
	/**
	 * 获取  总数
	 * @return totalQty
	 */
	public Long getTotalQty() {
		return totalQty;
	}
	/**
	 * 设置 总数
	 * @param totalQty the totalQty to set
	 */
	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}
	/**
	 * 获取 重量
	 * @return weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * 设置 重量
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * 获取  体积
	 * @return volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	/**
	 * 设置  体积
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	/**
	 * 获取 货物包装
	 * @return packageType
	 */
	public String getPackageType() {
		return packageType;
	}
	/**
	 * 设置 货物包装
	 * @param packageType the packageType to set
	 */
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	/**
	 * 获取  提货网点
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	/**
	 * 设置  提货网点
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * 获取 提货网点名称
	 * @return destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	/**
	 * 设置 提货网点名称
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	/**
	 * 获取  是否找到(Y是 ,N否)
	 * @return findType
	 */
	public String getFindType() {
		return findType;
	}
	/**
	 * 设置  是否找到(Y是 ,N否)
	 * @param findType the findType to set
	 */
	public void setFindType(String findType) {
		this.findType = findType;
	}
	/**
	 * 获取 上报时间
	 * @return reportDate
	 */
	public Date getReportDate() {
		return reportDate;
	}
	/**
	 * 设置 上报时间
	 * @param reportDate the reportDate to set
	 */
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	/**
	 * 获取 
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 * 设置 
	 * @param crrateUserCode the crrateUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * 获取 
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	/**
	 * 设置 
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	/**
	 * 获取  任务编号（返给Pda的）
	 * @return taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}
	/**
	 * 设置 任务编号（返给Pda的）
	 * @param taskNo the taskNo to set
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
}
