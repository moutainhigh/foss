package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TruckEfficiencyEntity;

/**
 * 装卸车效率管理VO
 * @author 200978  xiaobingcheng
 * 2015-1-23
 */
public class TruckEfficiencyVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6739599010299587893L;
	
	
	/**装卸车效率 查询结果list*/
	private List<TruckEfficiencyEntity> truckEfficiencyList;
	/**转运场*/
	private String transferCenterCode;
	private String transferCenterName;
	/**统计日期   统计月份*/
	private Date statisticDate;
	/**经营本部编码*/
	private String operationDeptCode;
	private String operationDeptName;
	
	
	
	public String getTransferCenterName() {
		return transferCenterName;
	}
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}
	public String getOperationDeptName() {
		return operationDeptName;
	}
	public void setOperationDeptName(String operationDeptName) {
		this.operationDeptName = operationDeptName;
	}
	public List<TruckEfficiencyEntity> getTruckEfficiencyList() {
		return truckEfficiencyList;
	}
	public void setTruckEfficiencyList(
			List<TruckEfficiencyEntity> truckEfficiencyList) {
		this.truckEfficiencyList = truckEfficiencyList;
	}
	public String getTransferCenterCode() {
		return transferCenterCode;
	}
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}
	public Date getStatisticDate() {
		return statisticDate;
	}
	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}
	public String getOperationDeptCode() {
		return operationDeptCode;
	}
	public void setOperationDeptCode(String operationDeptCode) {
		this.operationDeptCode = operationDeptCode;
	}
	
	
	
	
}
