/**  
 * Project Name:tfr-common-api  
 * File Name:JobTodoEntity.java  
 * Package Name:com.deppon.foss.module.transfer.common.api.shared.domain  
 * Date:2015年4月14日下午7:06:44  
 *  
 */  
  
package com.deppon.foss.module.transfer.common.api.shared.domain;  

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**  
 * ClassName: JobTodoEntity <br/>  
 * Function: 待处理Job表对应实体. <br/>  
 * date: 2015年4月14日 下午7:06:44 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class TfrJobTodoEntity extends BaseEntity {

	/**  
	 * serialVersionUID:serialVersionUID.  
	 * @since JDK 1.6  
	 */
	private static final long serialVersionUID = 12354587809767324L;
	
	/**
	 * 单据ID，可以是待处理数据的数据库主键，或者单据编号等
	 */
	private String businessID;
	
	/**
	 * 业务场景A，参见BusinessSceneConstants类的说明
	 */
	private String businessScene;
	
	/**
	 * 业务动作B，参见BusinessSceneConstants类的说明
	 */
	private String businessGoal;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 业务时间
	 */
	private Date businessTime;
	
	/**
	 * 操作人工号
	 */
	private String operatorCode;
	/**
	 * jobid
	 */
	private String jobId;
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**  
	 * operatorCode.  
	 *  
	 * @return  the operatorCode  
	 * @since   JDK 1.6  
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**  
	 * operatorCode.  
	 *  
	 * @param   operatorCode    the operatorCode to set  
	 * @since   JDK 1.6  
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**  
	 * businessTime.  
	 *  
	 * @return  the businessTime  
	 * @since   JDK 1.6  
	 */
	public Date getBusinessTime() {
		return businessTime;
	}

	/**  
	 * businessTime.  
	 *  
	 * @param   businessTime    the businessTime to set  
	 * @since   JDK 1.6  
	 */
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}

	/**  
	 * createTime.  
	 *  
	 * @return  the createTime  
	 * @since   JDK 1.6  
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * createTime.  
	 *  
	 * @param   createTime    the createTime to set  
	 * @since   JDK 1.6  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**  
	 * businessID.  
	 *  
	 * @return  the businessID  
	 * @since   JDK 1.6  
	 */
	public String getBusinessID() {
		return businessID;
	}

	/**  
	 * businessID.  
	 *  
	 * @param   businessID    the businessID to set  
	 * @since   JDK 1.6  
	 */
	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}

	/**  
	 * businessScene.  
	 *  
	 * @return  the businessScene  
	 * @since   JDK 1.6  
	 */
	public String getBusinessScene() {
		return businessScene;
	}

	/**  
	 * businessScene.  
	 *  
	 * @param   businessScene    the businessScene to set  
	 * @since   JDK 1.6  
	 */
	public void setBusinessScene(String businessScene) {
		this.businessScene = businessScene;
	}

	/**  
	 * businessGoal.  
	 *  
	 * @return  the businessGoal  
	 * @since   JDK 1.6  
	 */
	public String getBusinessGoal() {
		return businessGoal;
	}

	/**  
	 * businessGoal.  
	 *  
	 * @param   businessGoal    the businessGoal to set  
	 * @since   JDK 1.6  
	 */
	public void setBusinessGoal(String businessGoal) {
		this.businessGoal = businessGoal;
	}
	
	
}
  
