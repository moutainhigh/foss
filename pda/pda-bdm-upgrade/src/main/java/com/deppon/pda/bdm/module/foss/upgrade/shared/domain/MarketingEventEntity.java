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
public class MarketingEventEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	private String t_srv_price_region_code;
	private String t_srv_price_region_id;
	private String pricing_entry_id;
	private String pricing_entry_code;
	private String code;
	private String name;
	private String descRiption;
	private String type;
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
	public String getT_srv_price_region_code() {
		return t_srv_price_region_code;
	}
	public void setT_srv_price_region_code(String t_srv_price_region_code) {
		this.t_srv_price_region_code = t_srv_price_region_code;
	}
	public String getT_srv_price_region_id() {
		return t_srv_price_region_id;
	}
	public void setT_srv_price_region_id(String t_srv_price_region_id) {
		this.t_srv_price_region_id = t_srv_price_region_id;
	}
	public String getPricing_entry_id() {
		return pricing_entry_id;
	}
	public void setPricing_entry_id(String pricing_entry_id) {
		this.pricing_entry_id = pricing_entry_id;
	}
	public String getPricing_entry_code() {
		return pricing_entry_code;
	}
	public void setPricing_entry_code(String pricing_entry_code) {
		this.pricing_entry_code = pricing_entry_code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescRiption() {
		return descRiption;
	}
	public void setDescRiption(String descRiption) {
		this.descRiption = descRiption;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
