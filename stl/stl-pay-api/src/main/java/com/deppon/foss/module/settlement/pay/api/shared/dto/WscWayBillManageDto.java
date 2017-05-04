/**
 * company : com.deppon poroject : foss结算 copyright : copyright (c) 2016
 * 
 * @description: 待刷卡运单管理数据传输对象
 * @author : panshiqi (309613)
 * @date : 2016年3月2日 下午9:57:12
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;

/**
 * 
* @description: 待刷卡运单管理dto
* @className: WscWayBillManageDto
* 
* @author panshiqi 309613
* @date 2016年3月2日 下午9:58:09 
*
 */
public class WscWayBillManageDto implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	// ----------------------查询条件------------------------
	/**
	 * 查询开始日期
	 */
	private Date queryStartDate;

	/**
	 * 查询结束日期
	 */
	private Date queryEndDate;

	/**
	 *  运单号
	 */
	private String wayBillNo;

	/**
	 * 运单数据来源{1-运单开单 2-运单更改}
	 */
	private String wayBillSource;

	/**
	 * 运单支付状态{Y-已支付  N-未支付}
	 */
	private String paymentStatus;

	/**
	 * 数据有效状态{Y-有效   N-无效}
	 */
	private String active;

	/**
	 * 排序字段
	 */
	private String sortField;

	/**
	 * 排序类型 ASC DESC
	 */
	private String sortType;

	/**
	 * 当前登录人部门编号
	 */
	private String currentDeptCode;

	/**
	 * 当前登录人员工编号
	 */
	private String currentEmpCode;

	// ----------------------查询结果------------------------
	/**
	 * 待刷卡数据集合
	 */
	private List<WSCWayBillEntity> resultList = new ArrayList<WSCWayBillEntity>();

	/**
	 * 查询到的数据条数
	 */
	private long totalCount = 0;

	/**
	 * 查询到运单总待刷卡金额
	 */
	private double totalAmount = 0;

	// ----------------------get set------------------------
	/**  
	 * 获取 查询开始日期  
	 * @return queryStartDate 查询开始日期  
	 */
	public Date getQueryStartDate() {
		return queryStartDate;
	}

	/**  
	 * 设置 查询开始日期  
	 * @param queryStartDate 查询开始日期  
	 */
	public void setQueryStartDate(Date queryStartDate) {
		this.queryStartDate = queryStartDate;
	}

	/**  
	 * 获取 查询结束日期  
	 * @return queryEndDate 查询结束日期  
	 */
	public Date getQueryEndDate() {
		return queryEndDate;
	}

	/**  
	 * 设置 查询结束日期  
	 * @param queryEndDate 查询结束日期  
	 */
	public void setQueryEndDate(Date queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	/**  
	 * 获取 运单号  
	 * @return wayBillNo 运单号  
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}

	/**  
	 * 设置 运单号  
	 * @param wayBillNo 运单号  
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**  
	 * 获取 运单数据来源{1-运单开单2-运单更改}  
	 * @return wayBillSource 运单数据来源{1-运单开单2-运单更改}  
	 */
	public String getWayBillSource() {
		return wayBillSource;
	}

	/**  
	 * 设置 运单数据来源{1-运单开单2-运单更改}  
	 * @param wayBillSource 运单数据来源{1-运单开单2-运单更改}  
	 */
	public void setWayBillSource(String wayBillSource) {
		this.wayBillSource = wayBillSource;
	}

	/**  
	 * 获取 运单支付状态{Y-已支付N-未支付}  
	 * @return paymentStatus 运单支付状态{Y-已支付N-未支付}  
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**  
	 * 设置 运单支付状态{Y-已支付N-未支付}  
	 * @param paymentStatus 运单支付状态{Y-已支付N-未支付}  
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**  
	 * 获取 数据有效状态{Y-有效N-无效}  
	 * @return active 数据有效状态{Y-有效N-无效}  
	 */
	public String getActive() {
		return active;
	}

	/**  
	 * 设置 数据有效状态{Y-有效N-无效}  
	 * @param active 数据有效状态{Y-有效N-无效}  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**  
	 * 获取 待刷卡数据集合  
	 * @return resultList 待刷卡数据集合  
	 */
	public List<WSCWayBillEntity> getResultList() {
		return resultList;
	}

	/**  
	 * 设置 待刷卡数据集合  
	 * @param resultList 待刷卡数据集合  
	 */
	public void setResultList(List<WSCWayBillEntity> resultList) {
		this.resultList = resultList;
	}

	/**  
	 * 获取 查询到的数据条数  
	 * @return totalCount 查询到的数据条数  
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**  
	 * 设置 查询到的数据条数  
	 * @param totalCount 查询到的数据条数  
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**  
	 * 获取 排序字段  
	 * @return sortField 排序字段  
	 */
	public String getSortField() {
		return sortField;
	}

	/**  
	 * 设置 排序字段  
	 * @param sortField 排序字段  
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	/**  
	 * 获取 排序类型ASCDESC  
	 * @return sortType 排序类型ASCDESC  
	 */
	public String getSortType() {
		return sortType;
	}

	/**  
	 * 设置 排序类型ASCDESC  
	 * @param sortType 排序类型ASCDESC  
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	/**  
	 * 获取 当前登录人部门编号  
	 * @return currentDeptCode 当前登录人部门编号  
	 */
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	/**  
	 * 设置 当前登录人部门编号  
	 * @param currentDeptCode 当前登录人部门编号  
	 */
	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	/**  
	 * 获取 查询到运单总待刷卡金额  
	 * @return totalAmount 查询到运单总待刷卡金额  
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**  
	 * 设置 查询到运单总待刷卡金额  
	 * @param totalAmount 查询到运单总待刷卡金额  
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**  
	 * 获取 当前登录人员工编号  
	 * @return currentEmpCode 当前登录人员工编号  
	 */
	public String getCurrentEmpCode() {
		return currentEmpCode;
	}

	/**  
	 * 设置 当前登录人员工编号  
	 * @param currentEmpCode 当前登录人员工编号  
	 */
	public void setCurrentEmpCode(String currentEmpCode) {
		this.currentEmpCode = currentEmpCode;
	}
}
