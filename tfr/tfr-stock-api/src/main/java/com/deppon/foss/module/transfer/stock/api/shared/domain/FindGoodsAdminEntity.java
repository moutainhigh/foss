package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
* @ClassName: FindGoodsAdminEntity
* @Description: 找货管理Entity
* @author 189284--ZX
* @date 2015-7-9 下午4:28:29
*
 */
public class FindGoodsAdminEntity extends BaseEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID';
	 */
	private String id;
	/**
	 * 任务编号';
	 */
	private String taskNo;
	/**
	 * 任务状态
	 * 找货中：find_ing
	 * 已提交：submit_end';
	 */
	private String taskStatus;
	/**
	 * 货区Code';
	 */
	private String goodsAreaCode;
	/**
	 * 货区所属外场Code
	 */
	private String orgCode;
	/**
	 * 货区';
	 */
	private String goodsAreaName;
	/**
	 * 找货人Code';
	 */
	private String findGoodsManCode;
	/**
	 * 找货人';
	 */
	private String findGoodsManName;
	/**
	 * 任务创建时间（上报时间）';
	 */
	private Date taskCreateDate;
	/**
	 * 任务创建结束时间';
	 */
	private Date taskEndDate;
	/**
	 * 找货时间（任务时间）
	 */
	private Date taskDate;
	private String createUserCode;
	private String modifyUserCode;
	/**
	 * 获取 id
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 id
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 任务编号
	 * @return taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}
	/**
	 * 设置 任务编号
	 * @param taskNo the taskNo to set
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	/**
	 * 获取  任务状态
	 * @return taskStatus
	 */
	public String getTaskStatus() {
		return taskStatus;
	}
	/**
	 * 设置  任务状态
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	/**
	 * 获取 货区Code'
	 * @return goodsAreaCode
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	/**
	 * 设置 货区Code'
	 * @param goodsAreaCode the goodsAreaCode to set
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	/**
	 * 获取 货区
	 * @return goodsAreaName
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	/**
	 * 设置 货区
	 * @param goodsAreaName the goodsAreaName to set
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	
	/**
	 * 获取 货区所属外场Code
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * 设置 货区所属外场Code
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * 获取 找货人Code'
	 * @return findGoodsManCode
	 */
	public String getFindGoodsManCode() {
		return findGoodsManCode;
	}
	/**
	 * 设置 找货人Code'
	 * @param findGoodsManCode the findGoodsManCode to set
	 */
	public void setFindGoodsManCode(String findGoodsManCode) {
		this.findGoodsManCode = findGoodsManCode;
	}
	/**
	 * 获取  找货人
	 * @return findGoodsManName
	 */
	public String getFindGoodsManName() {
		return findGoodsManName;
	}
	/**
	 * 设置  找货人
	 * @param findGoodsManName the findGoodsManName to set
	 */
	public void setFindGoodsManName(String findGoodsManName) {
		this.findGoodsManName = findGoodsManName;
	}
	/**
	 * 获取 任务创建时间（上报时间）
	 * @return taskCreateDate
	 */
	public Date getTaskCreateDate() {
		return taskCreateDate;
	}
	
	/**
	 * 设置 任务创建时间（上报时间）
	 * @param taskCreateDate the taskCreateDate to set
	 */
	public void setTaskCreateDate(Date taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}
	/**
	 * 获取 任务创建结束时（上报时间）
	 * @return taskEndDate
	 */
	public Date getTaskEndDate() {
		return taskEndDate;
	}
	/**
	 * 设置 任务创建结束时（上报时间）
	 * @param taskEndDate the taskEndDate to set
	 */
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	/**
	 * 获取  找货时间（任务时间）
	 * @return taskDate
	 */
	public Date getTaskDate() {
		return taskDate;
	}
	/**
	 * 设置 找货时间（任务时间）
	 * @param taskDate the taskDate to set
	 */
	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}
	/**
	 * 获取 
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 * 设置 
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * 获取 
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	/**
	 * 设置 
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	
}
