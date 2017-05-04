package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/** 
 * @className: QueryConnectionBillDto
 * @author: zenghaibin foss 205109
 * @description: 查询运单dto，用于运单信息查询参数
 * @date: 2012-10-23 上午11:22:27
 */
public class QueryWayBillForConnectionBillDto implements Serializable{

	/**序列号**/
	private static final long serialVersionUID = -2066355499072445176L;
	
	/**入库开始时间**/
	private Date  beginInStorageTime;
	
	/**入库结束时间**/
	private Date  endInStorageTime;
	
	/**运输类型**/
	private String transType;
	
	/**运输性质**/
	private String transProperty;
	
	/**运输性质编码**/
	private String transPropertyCode;
	
	/**运单号**/
	private String waybillNo;
	
	/**到达接驳点编码**/
	private String arriveDeptCode;
	
	/**当前部门编码**/
	private String orgCode;

	/**接驳点所辐射营业部编码LIST**/
	private List<String> acceptPointSalesDepts;
	
	
	public Date getBeginInStorageTime() {
		return beginInStorageTime;
	}

	public void setBeginInStorageTime(Date beginInStorageTime) {
		this.beginInStorageTime = beginInStorageTime;
	}

	public Date getEndInStorageTime() {
		return endInStorageTime;
	}

	public void setEndInStorageTime(Date endInStorageTime) {
		this.endInStorageTime = endInStorageTime;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransProperty() {
		return transProperty;
	}

	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getArriveDeptCode() {
		return arriveDeptCode;
	}

	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}

	public List<String> getAcceptPointSalesDepts() {
		return acceptPointSalesDepts;
	}

	public void setAcceptPointSalesDepts(List<String> acceptPointSalesDepts) {
		this.acceptPointSalesDepts = acceptPointSalesDepts;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTransPropertyCode() {
		return transPropertyCode;
	}

	public void setTransPropertyCode(String transPropertyCode) {
		this.transPropertyCode = transPropertyCode;
	}
}
