package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SubForkAreaGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SubForkAreaGoodsExpressEntity;

/**
 *待叉区货物查询的数据vo，用于传递前后台参数数据 
 * @author zenghaibin
 * @date 2014 0707 10：20
 ***/
public class SubForkAreaGoodsVo {

	/** 产品List，运输性质*/
	private List<BaseDataDictDto> productList;
	/** 运单号*/
	private String wayBllNo;
	/**组织编码**/
	private String orgCode;
	/** 运输性质*/
	private String transType;
	/** 下一目的站*/
	private String  nextDestination;
	/** 卸车任务号*/
	private String  unloadTaskNo;
	/**卸车任务创建开始时间*/
	private Date createBeginTime;
	/** 卸车任务创建结束时间*/
	private Date createEndTime;
	/**待叉区数据list 零担**/
	private List<SubForkAreaGoodsEntity> subForkAreaGoodsEntityList;
	/**待叉区数据list 快递**/
	private List<SubForkAreaGoodsExpressEntity> subForkAreaGoodsExpressEntityList;

	//当前用户的部门
	private String optionOrgCode;
		
	private int currentPageNo;
		
	private int pageSize;
	
	public List<BaseDataDictDto> getProductList() {
		return productList;
	}

	public void setProductList(List<BaseDataDictDto> productList) {
		this.productList = productList;
	}

	public String getWayBllNo() {
		return wayBllNo;
	}

	public void setWayBllNo(String wayBllNo) {
		this.wayBllNo = wayBllNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getNextDestination() {
		return nextDestination;
	}

	public void setNextDestination(String nextDestination) {
		this.nextDestination = nextDestination;
	}

	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	public Date getCreateBeginTime() {
		return createBeginTime;
	}

	public void setCreateBeginTime(Date createBeginTime) {
		this.createBeginTime = createBeginTime;
	}

	

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public List<SubForkAreaGoodsEntity> getSubForkAreaGoodsEntityList() {
		return subForkAreaGoodsEntityList;
	}

	public void setSubForkAreaGoodsEntityList(
			List<SubForkAreaGoodsEntity> subForkAreaGoodsEntityList) {
		this.subForkAreaGoodsEntityList = subForkAreaGoodsEntityList;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public List<SubForkAreaGoodsExpressEntity> getSubForkAreaGoodsExpressEntityList() {
		return subForkAreaGoodsExpressEntityList;
	}

	public void setSubForkAreaGoodsExpressEntityList(
			List<SubForkAreaGoodsExpressEntity> subForkAreaGoodsExpressEntityList) {
		this.subForkAreaGoodsExpressEntityList = subForkAreaGoodsExpressEntityList;
	}

	public String getOptionOrgCode() {
		return optionOrgCode;
	}

	public void setOptionOrgCode(String optionOrgCode) {
		this.optionOrgCode = optionOrgCode;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "SubForkAreaGoodsVo [productList=" + productList + ", wayBllNo="
				+ wayBllNo + ", orgCode=" + orgCode + ", transType="
				+ transType + ", nextDestination=" + nextDestination
				+ ", unloadTaskNo=" + unloadTaskNo + ", createBeginTime="
				+ createBeginTime + ", createEndTime=" + createEndTime
				+ ", subForkAreaGoodsEntityList=" + subForkAreaGoodsEntityList
				+ ", subForkAreaGoodsExpressEntityList="
				+ subForkAreaGoodsExpressEntityList + "]";
	}
	
	
	
}
