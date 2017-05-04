package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.util.Date;

/**
 * 获取找货明细实体
 * @author 245955
 *
 */
public class FindGoodsAdminEntity {
    //货区
	private String goodsAreaCode;
	//部门
	private String orgCode;
	//找货人
	private String findGoodsManCode;
	//开始上报时间
	private Date taskCrateDate;
	//结束上报时间
	private Date taskEndDate;
	
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getFindGoodsManCode() {
		return findGoodsManCode;
	}
	public void setFindGoodsManCode(String findGoodsManCode) {
		this.findGoodsManCode = findGoodsManCode;
	}
	public Date getTaskCrateDate() {
		return taskCrateDate;
	}
	public void setTaskCrateDate(Date taskCrateDate) {
		this.taskCrateDate = taskCrateDate;
	}
	public Date getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	
}
