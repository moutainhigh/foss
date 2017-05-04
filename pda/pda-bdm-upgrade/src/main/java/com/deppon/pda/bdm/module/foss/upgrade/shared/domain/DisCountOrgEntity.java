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
public class DisCountOrgEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	private String dept_org_code;
	private String arrv_org_code;
	private Date begin_time;
	private Date end_time;
	private Long version_no;
	private String active;
	private String t_srv_pricing_valuation_id;
	private String dept_org_id;
	private String arrv_org_id;
	private String dept_org_type_code;
	private String arrv_org_type_code;
	private String dept_org_type_name;
	private String arrv_org_type_name;
	private String operFlag;
	
	
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	public String getDept_org_code() {
		return dept_org_code;
	}
	public void setDept_org_code(String dept_org_code) {
		this.dept_org_code = dept_org_code;
	}
	public String getArrv_org_code() {
		return arrv_org_code;
	}
	public void setArrv_org_code(String arrv_org_code) {
		this.arrv_org_code = arrv_org_code;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getT_srv_pricing_valuation_id() {
		return t_srv_pricing_valuation_id;
	}
	public void setT_srv_pricing_valuation_id(String t_srv_pricing_valuation_id) {
		this.t_srv_pricing_valuation_id = t_srv_pricing_valuation_id;
	}
	public String getDept_org_id() {
		return dept_org_id;
	}
	public void setDept_org_id(String dept_org_id) {
		this.dept_org_id = dept_org_id;
	}
	public String getArrv_org_id() {
		return arrv_org_id;
	}
	public void setArrv_org_id(String arrv_org_id) {
		this.arrv_org_id = arrv_org_id;
	}
	public String getDept_org_type_code() {
		return dept_org_type_code;
	}
	public void setDept_org_type_code(String dept_org_type_code) {
		this.dept_org_type_code = dept_org_type_code;
	}
	public String getArrv_org_type_code() {
		return arrv_org_type_code;
	}
	public void setArrv_org_type_code(String arrv_org_type_code) {
		this.arrv_org_type_code = arrv_org_type_code;
	}
	public String getDept_org_type_name() {
		return dept_org_type_name;
	}
	public void setDept_org_type_name(String dept_org_type_name) {
		this.dept_org_type_name = dept_org_type_name;
	}
	public String getArrv_org_type_name() {
		return arrv_org_type_name;
	}
	public void setArrv_org_type_name(String arrv_org_type_name) {
		this.arrv_org_type_name = arrv_org_type_name;
	}
}
