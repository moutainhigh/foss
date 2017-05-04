package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class ChangeGoodsAreaEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 申请时间
	 */
	private Date applicant_time;
	/**
	 * 申请人姓名
	 */
	private String applicant_name;
	/**
	 * 申请人工号
	 */
	private String applicant_code;
	/**
	 * 部门名称
	 */
	private String org_name;
	/**
	 * 部门code
	 */
	private String org_code;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	/**
	* @description 获取状态
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:06:14
	*/
	public String getState() {
		return state;
	}

	
	/**
	* @description 设置状态
	* @param state
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:06:25
	*/
	public void setState(String state) {
		this.state = state;
	}

	
	/**
	* @description 获取备注
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:06:32
	*/
	public String getRemarks() {
		return remarks;
	}

	
	/**
	* @description 设置备注
	* @param remarks
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:06:40
	*/
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	/**
	* @description 获取申请时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:06:48
	*/
	public Date getApplicant_time() {
		return applicant_time;
	}

	
	/**
	* @description 设置申请时间
	* @param applicant_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:06:57
	*/
	public void setApplicant_time(Date applicantTime) {
		this.applicant_time = applicantTime;
	}

	
	/**
	* @description 获取申请人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:07:04
	*/
	public String getApplicant_name() {
		return applicant_name;
	}

	
	/**
	* @description 设置申请人姓名
	* @param applicant_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:07:17
	*/
	public void setApplicant_name(String applicantName) {
		this.applicant_name = applicantName;
	}

	
	/**
	* @description 获取申请人工号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:07:28
	*/
	public String getApplicant_code() {
		return applicant_code;
	}

	
	/**
	* @description 设置申请人工号
	* @param applicant_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:07:38
	*/
	public void setApplicant_code(String applicantCode) {
		this.applicant_code = applicantCode;
	}

	
	/**
	* @description 获取部门名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:07:49
	*/
	public String getOrg_name() {
		return org_name;
	}

	
	/**
	* @description 设置部门名称
	* @param org_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:07:59
	*/
	public void setOrg_name(String orgName) {
		this.org_name = orgName;
	}

	
	/**
	* @description 获取部门编码
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:08:09
	*/
	public String getOrg_code() {
		return org_code;
	}

	
	/**
	* @description 设置部门编码
	* @param org_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:08:20
	*/
	public void setOrg_code(String orgCode) {
		this.org_code = orgCode;
	}

	
	
}
