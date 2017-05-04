package com.deppon.foss.module.transfer.partialline.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.ExpressOpreateRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.LdpExternalBillTrackEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.QueryLdpTrajectoryEntity;
public class QueryLdpTrajectoryVo {
	/**运单号**/
	private String wayBillNo;
	/**状态**/
    private String status;
    /**快递代理公司编码**/
	private String agentCompanyCode;
	/**操作记录entity**/
	private ExpressOpreateRecordEntity expressOpreateRecordEntity;
	/**操作记录entityList**/
	private List<ExpressOpreateRecordEntity> expressOpreateRecordEntityList;
	/**外发轨迹实体**/
	private LdpExternalBillTrackEntity ldpExternalBillTrackEntity;
	/**登录部门编码**/
	private String orgCode;
	/**生成外发单时间**/
	private Date billGenerationBeginTime;
    /**外发单结束时间**/
    private Date billGenerationEndTime;

    /**查询返回结果集合**/
    private List<QueryLdpTrajectoryEntity> ldpTrajectoryEntitylist;

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	public ExpressOpreateRecordEntity getExpressOpreateRecordEntity() {
		return expressOpreateRecordEntity;
	}

	public void setExpressOpreateRecordEntity(
			ExpressOpreateRecordEntity expressOpreateRecordEntity) {
		this.expressOpreateRecordEntity = expressOpreateRecordEntity;
	}

	public LdpExternalBillTrackEntity getLdpExternalBillTrackEntity() {
		return ldpExternalBillTrackEntity;
	}

	public void setLdpExternalBillTrackEntity(
			LdpExternalBillTrackEntity ldpExternalBillTrackEntity) {
		this.ldpExternalBillTrackEntity = ldpExternalBillTrackEntity;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Date getBillGenerationBeginTime() {
		return billGenerationBeginTime;
	}

	public void setBillGenerationBeginTime(Date billGenerationBeginTime) {
		this.billGenerationBeginTime = billGenerationBeginTime;
	}

	public Date getBillGenerationEndTime() {
		return billGenerationEndTime;
	}

	public void setBillGenerationEndTime(Date billGenerationEndTime) {
		this.billGenerationEndTime = billGenerationEndTime;
	}

	public List<QueryLdpTrajectoryEntity> getLdpTrajectoryEntitylist() {
		return ldpTrajectoryEntitylist;
	}

	public void setLdpTrajectoryEntitylist(
			List<QueryLdpTrajectoryEntity> ldpTrajectoryEntitylist) {
		this.ldpTrajectoryEntitylist = ldpTrajectoryEntitylist;
	}

	public List<ExpressOpreateRecordEntity> getExpressOpreateRecordEntityList() {
		return expressOpreateRecordEntityList;
	}

	public void setExpressOpreateRecordEntityList(
			List<ExpressOpreateRecordEntity> expressOpreateRecordEntityList) {
		this.expressOpreateRecordEntityList = expressOpreateRecordEntityList;
	}
	
	
}
