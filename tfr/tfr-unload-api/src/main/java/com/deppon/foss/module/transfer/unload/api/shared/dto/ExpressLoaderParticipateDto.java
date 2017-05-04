package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 快递-理货员明细
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月27日 下午2:14:00
 */
public class ExpressLoaderParticipateDto implements Serializable{
	
	/**
	* @fields serialVersionUID
	* @author 328768-foss-gaojianfu
	* @update 2016年4月27日 下午2:18:40
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	/**卸车任务编号*/
	private String taskNo;
	
	/**理货员姓名*/
	private String loaderName;
	
	/**理货员工号*/
	private String loaderCode;
	
	/** 装卸车队编号 */
	private String loadOrgCode;
	
	/** 装卸车队名称 */
	private String loadOrgName;
	
	/** 加入时间 */
	private Date joinTime;
	
	/** 离开时间 */
	private Date leaveTime;
	
	/** 是否为建立任务理货员 */
	private String iscreator;
	
	/** 类型 */
	private String taskType;

	/**
	 * @description 获取 卸车任务编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:38
	 */
	public String getTaskNo() {
		return taskNo;
	}

	/**
	 * @description 设置 卸车任务编号
	 * @param taskNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:46
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * @description 获取 理货员姓名
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:52
	 */
	public String getLoaderName() {
		return loaderName;
	}

	/**
	 * @description 设置 理货员姓名
	 * @param loaderName
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:46
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}

	/**
	 * 
	* @description 获取 理货员工号
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年4月29日 上午7:56:52
	 */
	public String getLoaderCode() {
		return loaderCode;
	}

	/**
	 * 
	* @description 设置 理货员工号
	* @param loaderCode
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年4月29日 上午7:56:46
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	/**
	 * @description 获取 装卸车队编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:52
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	/**
	 * @description 设置 装卸车队编号
	 * @param loadOrgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:46
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * 
	* @description 获取 装卸车队名称
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年4月29日 上午7:56:52
	 */
	public String getLoadOrgName() {
		return loadOrgName;
	}

	/**
	 * @description 设置 装卸车队名称
	 * @param loadOrgName
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:46
	 */
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}

	/**
 	 * @description 获取 加入时间
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:52
	 */
	public Date getJoinTime() {
		return joinTime;
	}

	/**
	 * @description 设置  加入时间
	 * @param joinTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:46
	 */
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	/**
	 * @description 获取 离开时间
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:52
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}

	/**
	 * @description 设置 离开时间
	 * @param leaveTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午7:56:46
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	/**
	 * 
	* @description 获取 是否为建立任务理货员
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年4月29日 上午7:56:52
	 */
	public String getIscreator() {
		return iscreator;
	}

	/**
	 * 
	* @description 设置 是否为建立任务理货员
	* @param iscreator
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年4月29日 上午7:56:46
	 */
	public void setIscreator(String iscreator) {
		this.iscreator = iscreator;
	}

	/**
	 * 
	* @description 获取 类型
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年4月29日 上午7:56:52
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * 
	* @description 设置 类型
	* @param taskType
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年4月29日 上午7:56:46
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	
}
