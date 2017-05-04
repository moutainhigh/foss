package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverOrgEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.ForkliftGoodsStayDurationDto;

public class ForkliftGoodsEfficiencyVo implements Serializable {

	/** 转运场 */
	private String transferCenterCode;
	private String transferCenterName;
	/** 统计日期 统计月份 */
	private Date staDate;
	private int staMonth;
	/** 经营本部编码 */
	private String operationDeptCode;
	private String operationDeptName;

	/** 叉车司机部门 */
	private String driverOrgCode;
	/**叉车司机*/
	private String driverCode;

	/** 叉车司机电叉分货效率list */
	private List<ForkliftDriverEffEntity> forkliftDriverEffList;

	/** 叉车司机部门电叉分货效率list */
	private List<ForkliftDriverOrgEffEntity> forkliftDriverOrgEffList;

	/** 转运场电叉分货效率list */
	private List<ForkliftEffEntity> forkliftEffList;
	
	/**待叉区停留时长占比 list*/
	private List<ForkliftGoodsStayDurationDto> forkliftGoodsStayDurationList;
	
	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public List<ForkliftGoodsStayDurationDto> getForkliftGoodsStayDurationList() {
		return forkliftGoodsStayDurationList;
	}

	public void setForkliftGoodsStayDurationList(
			List<ForkliftGoodsStayDurationDto> forkliftGoodsStayDurationList) {
		this.forkliftGoodsStayDurationList = forkliftGoodsStayDurationList;
	}

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public int getStaMonth() {
		return staMonth;
	}

	public void setStaMonth(int staMonth) {
		this.staMonth = staMonth;
	}

	public String getOperationDeptCode() {
		return operationDeptCode;
	}

	public void setOperationDeptCode(String operationDeptCode) {
		this.operationDeptCode = operationDeptCode;
	}

	public String getOperationDeptName() {
		return operationDeptName;
	}

	public void setOperationDeptName(String operationDeptName) {
		this.operationDeptName = operationDeptName;
	}

	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

	public List<ForkliftDriverEffEntity> getForkliftDriverEffList() {
		return forkliftDriverEffList;
	}

	public void setForkliftDriverEffList(
			List<ForkliftDriverEffEntity> forkliftDriverEffList) {
		this.forkliftDriverEffList = forkliftDriverEffList;
	}

	public List<ForkliftDriverOrgEffEntity> getForkliftDriverOrgEffList() {
		return forkliftDriverOrgEffList;
	}

	public void setForkliftDriverOrgEffList(
			List<ForkliftDriverOrgEffEntity> forkliftDriverOrgEffList) {
		this.forkliftDriverOrgEffList = forkliftDriverOrgEffList;
	}

	public List<ForkliftEffEntity> getForkliftEffList() {
		return forkliftEffList;
	}

	public void setForkliftEffList(List<ForkliftEffEntity> forkliftEffList) {
		this.forkliftEffList = forkliftEffList;
	}

}
