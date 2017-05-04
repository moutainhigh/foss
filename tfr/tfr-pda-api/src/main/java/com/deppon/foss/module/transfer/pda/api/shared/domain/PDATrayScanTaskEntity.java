package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PDATrayScanTaskEntity implements Serializable{

	private static final long serialVersionUID = -4437452001250208034L;
	
	/**
	 * id
	 * */
	private String id;
	

	/**
	 * 任务号
	 * */
	private String taskNo;
	
	/**
	 * 叉车扫描时间
	 * */
	private Date  trayscanTime;
	
	/**
	 * 叉车司机工号
	 * */
	private String forkliftDriverCode;
	
	/**
	 * 叉车司机部门
	 * */
	private String forkliftDept;
	
	/**
	 * 绑定人工号
	 * */
	private String bindingCode;
		
	/**
	 * 托盘绑定任务时间
	 * */
	private Date  traytaskCreatTime;
	
	/***
	 * 绑定任务的部门
	 *
	 ** */
	private String bindingDept;
	
	/**
	 * 任务状态
	 * */
	private String trayscanStatu;
	/**
	 * 
	 * 外场
	 * 
	 * */
	private String outfieldCode;
	
	/***
	 * 
	 * 任务明细
	 * 
	 * **/
	private List<PDATrayScanDetailEntity> trayScanDetails = new ArrayList<PDATrayScanDetailEntity>(); 

	
	/**
	 * 
	 *  叉车票数
	 * 
	 * */
	private int forkliftVotes;
	
	/**
	 * 
	 *  卸车任务编号
	 * 
	 * */
    private String unloadTaskNo;
	
    /**
     * PDA操作类型    手动拉车 ：HANDSCAN 叉车分货：UNSCAN
     * */
    private String operationType;
    
	
	public int getForkliftVotes() {
		return forkliftVotes;
	}

	public void setForkliftVotes(int forkliftVotes) {
		this.forkliftVotes = forkliftVotes;
	}

	public String getOutfieldCode() {
		return outfieldCode;
	}

	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}

	public List<PDATrayScanDetailEntity> getTrayScanDetails() {
		return trayScanDetails;
	}

	public void setTrayScanDetails(List<PDATrayScanDetailEntity> trayScanDetails) {
		this.trayScanDetails = trayScanDetails;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getForkliftDept() {
		return forkliftDept;
	}

	public void setForkliftDept(String forkliftDept) {
		this.forkliftDept = forkliftDept;
	}

	public String getBindingDept() {
		return bindingDept;
	}

	public void setBindingDept(String bindingDept) {
		this.bindingDept = bindingDept;
	}

	
	/**
	 * 获取 任务号.
	 *
	 * @return the 任务号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * 设置 任务号
	 *
	 * @param traytaskCode the new 任务号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	

	/**
	 * 获取 任务扫描时间.
	 *
	 * @return the 任务扫描时间
	 */
	public Date getTrayscanTime() {
		return trayscanTime;
	}
	
	/**
	 * 设置 任务扫描时间
	 *
	 * @param trayscanTime the new 任务扫描时间
	 */
	public void setTrayscanTime(Date trayscanTime) {
		this.trayscanTime = trayscanTime;
	}
	
	/**
	 * 获取叉车司机工号.
	 *
	 * @return the 叉车司机工号
	 */
	public String getForkliftDriverCode() {
		return forkliftDriverCode;
	}
	
	/**
	 * 设置 叉车司机工号
	 *
	 * @param forkliftDriverCode the new 叉车司机工号
	 */
	public void setForkliftDriverCode(String forkliftDriverCode) {
		this.forkliftDriverCode = forkliftDriverCode;
	}
	
	/**
	 * 获取绑定人工号.
	 *
	 * @return the 绑定人工号
	 */
	public String getBindingCode() {
		return bindingCode;
	}
	
	/**
	 * 设置绑定人工号
	 *
	 * @param bindingCode the new绑定人工号
	 */
	public void setBindingCode(String bindingCode) {
		this.bindingCode = bindingCode;
	}
	
	/**
	 * 获取任务创建时间.
	 *
	 * @return the 任务创建时间
	 */
	public Date getTraytaskCreatTime() {
		return traytaskCreatTime;
	}

	/**
	 * 设置任务创建时间
	 *
	 * @param traytaskCreatTime the new任务创建时间
	 */
	public void setTraytaskCreatTime(Date traytaskCreatTime) {
		this.traytaskCreatTime = traytaskCreatTime;
	}
	
	/**
	 * 获取任务状态.
	 *
	 * @return the 任务状态
	 */
	public String getTrayscanStatu() {
		return trayscanStatu;
	}

	
	/**
	 * 设置任务状态
	 *
	 * @param trayscanType the new任务状态
	 */
	public void setTrayscanStatu(String trayscanStatu) {
		this.trayscanStatu = trayscanStatu;
	}

	/**
	 * 获取卸车任务状态.
	 *
	 * @return the 卸车任务状态.
	 */
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	/**
	 * 设置卸车任务状态
	 *
	 * @param trayscanType the new卸车任务状态
	 */
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	
	
	
}
