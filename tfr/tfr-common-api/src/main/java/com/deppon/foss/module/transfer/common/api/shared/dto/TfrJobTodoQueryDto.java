  /**  
 * Project Name:tfr-common-api  
 * File Name:TfrJobTodoQueryDto.java  
 * Package Name:com.deppon.foss.module.transfer.common.api.shared.dto  
 * Date:2015年4月14日下午9:43:20  
 *  
 */  
  
package com.deppon.foss.module.transfer.common.api.shared.dto;  

import java.util.Date;

/**  
 * ClassName: TfrJobTodoQueryDto <br/>  
 * Function: 查询待办Job时的查询条件. <br/>  
 * date: 2015年4月14日 下午9:43:20 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class TfrJobTodoQueryDto {
	
	/**
	 * 数据库主键
	 */
	private String id;
	
	/**
	 * 业务场景list
	 */
	private String[] businessSceneList;
	
	/**
	 * 业务目标list
	 */
	private String[] businessGoalList;
	
	/**
	 * 业务开始时间
	 */
	private Date bizStartTime;
	
	/**
	 * 业务截止时间
	 */
	private Date bizEndTime;
	
	/**
	 * 开始时间（create_time）
	 */
	private Date startTime;
	
	/**
	 * 截止时间
	 */
	private Date endTime;
	
	/**
	 * 记录条数
	 */
	private int count;

	/**  
	 * id.  
	 *  
	 * @return  the id  
	 * @since   JDK 1.6  
	 */
	public String getId() {
		return id;
	}

	/**  
	 * id.  
	 *  
	 * @param   id    the id to set  
	 * @since   JDK 1.6  
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**  
	 * bizStartTime.  
	 *  
	 * @return  the bizStartTime  
	 * @since   JDK 1.6  
	 */
	public Date getBizStartTime() {
		return bizStartTime;
	}

	/**  
	 * bizStartTime.  
	 *  
	 * @param   bizStartTime    the bizStartTime to set  
	 * @since   JDK 1.6  
	 */
	public void setBizStartTime(Date bizStartTime) {
		this.bizStartTime = bizStartTime;
	}

	/**  
	 * bizEndTime.  
	 *  
	 * @return  the bizEndTime  
	 * @since   JDK 1.6  
	 */
	public Date getBizEndTime() {
		return bizEndTime;
	}

	/**  
	 * bizEndTime.  
	 *  
	 * @param   bizEndTime    the bizEndTime to set  
	 * @since   JDK 1.6  
	 */
	public void setBizEndTime(Date bizEndTime) {
		this.bizEndTime = bizEndTime;
	}

	/**  
	 * businessSceneList.  
	 *  
	 * @return  the businessSceneList  
	 * @since   JDK 1.6  
	 */
	public String[] getBusinessSceneList() {
		return businessSceneList;
	}

	/**  
	 * businessSceneList.  
	 *  
	 * @param   businessSceneList    the businessSceneList to set  
	 * @since   JDK 1.6  
	 */
	public void setBusinessSceneList(String[] businessSceneList) {
		this.businessSceneList = businessSceneList;
	}

	/**  
	 * businessGoalList.  
	 *  
	 * @return  the businessGoalList  
	 * @since   JDK 1.6  
	 */
	public String[] getBusinessGoalList() {
		return businessGoalList;
	}

	/**  
	 * businessGoalList.  
	 *  
	 * @param   businessGoalList    the businessGoalList to set  
	 * @since   JDK 1.6  
	 */
	public void setBusinessGoalList(String[] businessGoalList) {
		this.businessGoalList = businessGoalList;
	}

	/**  
	 * startTime.  
	 *  
	 * @return  the startTime  
	 * @since   JDK 1.6  
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**  
	 * startTime.  
	 *  
	 * @param   startTime    the startTime to set  
	 * @since   JDK 1.6  
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**  
	 * endTime.  
	 *  
	 * @return  the endTime  
	 * @since   JDK 1.6  
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**  
	 * endTime.  
	 *  
	 * @param   endTime    the endTime to set  
	 * @since   JDK 1.6  
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**  
	 * count.  
	 *  
	 * @return  the count  
	 * @since   JDK 1.6  
	 */
	public int getCount() {
		return count;
	}

	/**  
	 * count.  
	 *  
	 * @param   count    the count to set  
	 * @since   JDK 1.6  
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
}
  
