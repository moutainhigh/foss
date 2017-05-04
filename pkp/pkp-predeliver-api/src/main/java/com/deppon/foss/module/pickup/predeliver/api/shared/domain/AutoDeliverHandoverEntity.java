package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;
/**
 * 待自动交单临时表
 * @author 159231 meiying
 * 2015-5-28  下午3:23:52
 */
public class AutoDeliverHandoverEntity {
    private String id;
    /**
     * 运单号
     */
    private String waybillNo;
	/**
	 * 创建时间
	 */
    private Date createTime;
    /**
     * job占用id
     */
    private String jobId;
    private Integer num;
	/**
	 * 获取id  
	 * @return id id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置id  
	 * @param id id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取waybillNo  
	 * @return waybillNo waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * 设置waybillNo  
	 * @param waybillNo waybillNo 
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * 获取createTime  
	 * @return createTime createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置createTime  
	 * @param createTime createTime 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取jobId  
	 * @return jobId jobId
	 */
	public String getJobId() {
		return jobId;
	}
	/**
	 * 设置jobId  
	 * @param jobId jobId 
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	/**
	 * 获取num  
	 * @return num num
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * 设置num  
	 * @param num num 
	 */
	public void setNum(Integer num) {
		this.num = num;
	}

    
}