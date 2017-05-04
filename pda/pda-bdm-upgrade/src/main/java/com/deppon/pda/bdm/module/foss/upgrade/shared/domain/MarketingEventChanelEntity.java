package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-3-16 下午4:22:31,content:TODO </p>
 * @author chengang
 * @date 2013-3-16 下午4:22:31
 * @since
 * @version
 */
public class MarketingEventChanelEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	private String sales_channel_code;
	private String sales_channel_id;
	private String t_srv_marketing_event_id;
	private String active;
	private Date begin_time;
	private Date end_time;
	private Long version_no;
	private String operFlag;
	
	
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	
	public String getSales_channel_code() {
		return sales_channel_code;
	}
	public void setSales_channel_code(String sales_channel_code) {
		this.sales_channel_code = sales_channel_code;
	}
	public String getSales_channel_id() {
		return sales_channel_id;
	}
	public void setSales_channel_id(String sales_channel_id) {
		this.sales_channel_id = sales_channel_id;
	}
	public String getT_srv_marketing_event_id() {
		return t_srv_marketing_event_id;
	}
	public void setT_srv_marketing_event_id(String t_srv_marketing_event_id) {
		this.t_srv_marketing_event_id = t_srv_marketing_event_id;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Long getVersion_no() {
		return version_no;
	}
	public void setVersion_no(Long version_no) {
		this.version_no = version_no;
	}
	
	
}
