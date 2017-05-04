package com.deppon.foss.module.transfer.departure.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.departure.api.shared.domain.AgingNodesLogEntity;

public class AgingNodesLogVo implements Serializable{

	private static final long serialVersionUID = -3684098090309146409L;
	
	//查询修改时效节点日志条件 单号
	private String billNo;
	//查询修改时效节点日志结果
	private List<AgingNodesLogEntity> agingNodesLogs;
	//修改时效节点时的传递参数 修改类型
	private String modifyType;
	//修改时效节点时传递参数 修改后时间
	private Date afterModifyTime;
	//关联单号
	private String relationbillNos;
	
	
	
	public String getRelationbillNos() {
		return relationbillNos;
	}
	public void setRelationbillNos(String relationbillNos) {
		this.relationbillNos = relationbillNos;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public List<AgingNodesLogEntity> getAgingNodesLogs() {
		return agingNodesLogs;
	}
	public void setAgingNodesLogs(List<AgingNodesLogEntity> agingNodesLogs) {
		this.agingNodesLogs = agingNodesLogs;
	}
	public String getModifyType() {
		return modifyType;
	}
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}
	public Date getAfterModifyTime() {
		return afterModifyTime;
	}
	public void setAfterModifyTime(Date afterModifyTime) {
		this.afterModifyTime = afterModifyTime;
	}
	
}
