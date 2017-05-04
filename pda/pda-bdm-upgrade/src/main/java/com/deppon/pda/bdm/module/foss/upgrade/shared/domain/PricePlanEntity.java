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
public class PricePlanEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	private String t_srv_price_region_id;
	private String t_srv_price_region_code;
	private String name;
	private String version_info;
	private String ref_id;
	private String currency_code;
	private String transport_flag;
	private String descRiption;
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
	public String getT_srv_price_region_id() {
		return t_srv_price_region_id;
	}
	public void setT_srv_price_region_id(String t_srv_price_region_id) {
		this.t_srv_price_region_id = t_srv_price_region_id;
	}
	public String getT_srv_price_region_code() {
		return t_srv_price_region_code;
	}
	public void setT_srv_price_region_code(String t_srv_price_region_code) {
		this.t_srv_price_region_code = t_srv_price_region_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion_info() {
		return version_info;
	}
	public void setVersion_info(String version_info) {
		this.version_info = version_info;
	}
	public String getRef_id() {
		return ref_id;
	}
	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public String getTransport_flag() {
		return transport_flag;
	}
	public void setTransport_flag(String transport_flag) {
		this.transport_flag = transport_flag;
	}
	public String getDescRiption() {
		return descRiption;
	}
	public void setDescRiption(String descRiption) {
		this.descRiption = descRiption;
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
