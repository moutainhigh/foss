package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.deppon.foss.framework.entity.BaseEntity;
/**
* @description 营业部交接库存实体类
* @version 1.0
* @author 360903  linhua.yan-tfr
* @update 2016年9月18日
*/
@XmlRootElement
public class StockSaleEntity extends BaseEntity{
	
	private static final long serialVersionUID = -8221954949349651858L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 流水号
	 */
	private String serialNO;
	/**
	 * 操作人编号
	 */
	private String operatorCode;
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	/**
	 * 部门编号
	 */
	private String orgCode;
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 入库时间
	 */
	private Date inStockTime;
	/**
	 * PDA扫描时间
	 */
	private Date scanTime;
	/**
	 * 预配状态
	 */
	private String preHandOverState;
	/**
	 * 下一部门
	 */
	private String nextOrgCode;
	/**
	 * 货区名称    MCEW-丢货预警接口需求
	 * */
	private String goodsAreaName;
	/**
	 * 是否出库
	 */
	private String isOut;
	
	
	private String bePackage;
	
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNO() {
		return serialNO;
	}
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Date getInStockTime() {
		return inStockTime;
	}
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getPreHandOverState() {
		return preHandOverState;
	}
	public void setPreHandOverState(String preHandOverState) {
		this.preHandOverState = preHandOverState;
	}
	public String getNextOrgCode() {
		return nextOrgCode;
	}
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	public String getIsOut() {
		return isOut;
	}
	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}
	public String getBePackage() {
		return bePackage;
	}
	public void setBePackage(String bePackage) {
		this.bePackage = bePackage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}