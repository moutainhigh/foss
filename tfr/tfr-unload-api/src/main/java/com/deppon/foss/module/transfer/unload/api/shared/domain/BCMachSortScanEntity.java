package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;


/**
* @description 计泡机扫描运单信息实体
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月7日 上午11:21:38
*/
public class BCMachSortScanEntity implements Serializable { 
	
	/**
	* @fields serialVersionUID
	* @author 105869-foss-heyongdong
	* @update 2015年5月7日 上午11:19:48
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 6800599176133948174L;
	
	private String id;//id
	
	private String sortScanlogId;//扫描日志表id
	
	private String waybillNo;//运单号
	
	private String serialNo;//流水号
	
	private String operatorCode;//操作人工号
	
	private String operatorName;//操作人
	
	private String operationDept;//操作部门
	
	private String operationDeptCode;//操作人部门编码
	
	
	
	private Date scanTime;//操作时间
	
	private Date createTime;//创建时间
	
	private String scanType;//扫描类型 
	
	private BigDecimal length;//长
	
	private BigDecimal width;//宽
	
	private BigDecimal height;//高
	
	private BigDecimal weight;//重量
	
	private BigDecimal volumn;//体积
	
	private String goodsSize;//货物尺寸
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSortScanlogId() {
		return sortScanlogId;
	}

	public void setSortScanlogId(String sortScanlogId) {
		this.sortScanlogId = sortScanlogId;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getOperationDept() {
		return operationDept;
	}

	public void setOperationDept(String operationDept) {
		this.operationDept = operationDept;
	}

	public String getOperationDeptCode() {
		return operationDeptCode;
	}

	public void setOperationDeptCode(String operationDeptCode) {
		this.operationDeptCode = operationDeptCode;
	}
	@DateFormat
	public Date getScanTime() {
		return scanTime;
	}
	@DateFormat
	public void setScantime(Date scanTime) {
		this.scanTime = scanTime;
	}

	
	@DateFormat
	public Date getCreateTime() {
		return createTime;
	}
	@DateFormat
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeigth(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolumn() {
		return volumn;
	}

	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	

}
