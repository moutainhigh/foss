package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 申请库存迁移实体类 
* @version 1.0
* @author 218381-foss-lijie
* @update 2014-12-12 下午5:00:05
*/
public class MoveGoodsEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
//	String uuidStr = UUIDUtils.getUUID();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
				//UUIDUtils.getUUID();
	}
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
	 * 申请人所在部门
	 */
	private String org_name;
	/**
	 * 申请人所在部门编号
	 */
	private String org_code;
	/**
	 * 审核时间
	 */
	private Date auditor_time;
	/**
	 * 审核人姓名
	 */
	private String auditor_name;
	/**
	 * 审核人工号
	 */
	private String auditor_code;
	
	/**
	 * 作废时间
	 */
	private Date invalidate_time;
	/**
	 * 作废人姓名
	 */
	private String invalidate_name;
	/**
	 * 作废人工号
	 */
	private String invalidate_code;
	
	/**
	 * 退回时间
	 */
	private Date return_time;
	/**
	 * 退回人姓名
	 */
	private String return_name;
	/**
	 * 退回人工号
	 */
	private String return_code;
	
	/**
	 * 确认迁移时间
	 */
	private Date confirm_time;
	/**
	 * 确认迁移人姓名
	 */
	private String confirm_name;
	/**
	 * 确认迁移人工号
	 */
	private String confirm_code;
	
	/**
	 * 撤销时间
	 */
	private Date revocation_time;
	/**
	 * 撤销人姓名
	 */
	private String revocation_name;
	/**
	 * 撤销人工号
	 */
	private String revocation_code;
	
	/**
	 * 修改时间
	 */
	private Date modify_time;
	
	/**
	* @description 获取审核时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:59:28
	*/
	public Date getAuditor_time() {
		return auditor_time;
	}
	
	/**
	* @description 设置审核时间
	* @param auditor_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午10:59:50
	*/
	public void setAuditor_time(Date auditorTime) {
		this.auditor_time = auditorTime;
	}
	
	/**
	* @description 获取审核人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:00:02
	*/
	public String getAuditor_name() {
		return auditor_name;
	}
	
	/**
	* @description 设置审核人姓名
	* @param auditor_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:00:13
	*/
	public void setAuditor_name(String auditorName) {
		this.auditor_name = auditorName;
	}
	
	/**
	* @description 获取审核人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:00:27
	*/
	public String getAuditor_code() {
		return auditor_code;
	}
	
	/**
	* @description 设置审核人code
	* @param auditor_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:00:40
	*/
	public void setAuditor_code(String auditorCode) {
		this.auditor_code = auditorCode;
	}
	
	/**
	* @description 获取作废时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:00:51
	*/
	public Date getInvalidate_time() {
		return invalidate_time;
	}
	
	/**
	* @description 设置作废时间
	* @param invalidate_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:01:16
	*/
	public void setInvalidate_time(Date invalidateTime) {
		this.invalidate_time = invalidateTime;
	}
	
	/**
	* @description 获取作废人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:01:26
	*/
	public String getInvalidate_name() {
		return invalidate_name;
	}
	
	/**
	* @description 设置作废人姓名
	* @param invalidate_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:01:38
	*/
	public void setInvalidate_name(String invalidateName) {
		this.invalidate_name = invalidateName;
	}
	
	/**
	* @description 获取作废人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:01:48
	*/
	public String getInvalidate_code() {
		return invalidate_code;
	}
	
	/**
	* @description 设置作废人code
	* @param invalidate_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:02:02
	*/
	public void setInvalidate_code(String invalidateCode) {
		this.invalidate_code = invalidateCode;
	}
	
	/**
	* @description 获取退回时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:02:15
	*/
	public Date getReturn_time() {
		return return_time;
	}
	
	/**
	* @description 设置退回时间
	* @param return_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:02:27
	*/
	public void setReturn_time(Date returnTime) {
		this.return_time = returnTime;
	}
	
	/**
	* @description 获取退回人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:02:38
	*/
	public String getReturn_name() {
		return return_name;
	}
	
	/**
	* @description 设置退回人姓名
	* @param return_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:02:49
	*/
	public void setReturn_name(String returnName) {
		this.return_name = returnName;
	}
	
	/**
	* @description 获取退回人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:02:59
	*/
	public String getReturn_code() {
		return return_code;
	}
	
	/**
	* @description 设置退回人code
	* @param return_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:03:10
	*/
	public void setReturn_code(String returnCode) {
		this.return_code = returnCode;
	}
	
	/**
	* @description 获取确认迁移时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:03:25
	*/
	public Date getConfirm_time() {
		return confirm_time;
	}
	
	/**
	* @description 设置确认迁移时间
	* @param confirm_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:03:54
	*/
	public void setConfirm_time(Date confirmTime) {
		this.confirm_time = confirmTime;
	}
	
	/**
	* @description 获取确认迁移人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:04:04
	*/
	public String getConfirm_name() {
		return confirm_name;
	}
	
	/**
	* @description 设置确认迁移人姓名
	* @param confirm_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:04:16
	*/
	public void setConfirm_name(String confirmName) {
		this.confirm_name = confirmName;
	}
	
	/**
	* @description 获取确认迁移人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:04:27
	*/
	public String getConfirm_code() {
		return confirm_code;
	}
	
	/**
	* @description 设置确认迁移人code
	* @param confirm_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:04:39
	*/
	public void setConfirm_code(String confirmCode) {
		this.confirm_code = confirmCode;
	}
	
	/**
	* @description 获取撤销时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:05:20
	*/
	public Date getRevocation_time() {
		return revocation_time;
	}
	
	/**
	* @description 设置撤销时间
	* @param revocation_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:05:32
	*/
	public void setRevocation_time(Date revocationTime) {
		this.revocation_time = revocationTime;
	}
	
	/**
	* @description 获取撤销人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:05:42
	*/
	public String getRevocation_name() {
		return revocation_name;
	}
	
	/**
	* @description 设置撤销人姓名
	* @param revocation_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:05:51
	*/
	public void setRevocation_name(String revocationName) {
		this.revocation_name = revocationName;
	}
	
	/**
	* @description 获取撤销人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:06:03
	*/
	public String getRevocation_code() {
		return revocation_code;
	}
	
	/**
	* @description 设置撤销人code
	* @param revocation_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:06:14
	*/
	public void setRevocation_code(String revocationCode) {
		this.revocation_code = revocationCode;
	}
	
	/**
	* @description 获取修改时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:06:24
	*/
	public Date getModify_time() {
		return modify_time;
	}
	
	/**
	* @description 设置修改时间
	* @param modify_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:06:37
	*/
	public void setModify_time(Date modifyTime) {
		this.modify_time = modifyTime;
	}
	
	/**
	* @description 获取修改人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:06:49
	*/
	public String getModify_name() {
		return modify_name;
	}
	
	/**
	* @description 设置修改人姓名
	* @param modify_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:07:01
	*/
	public void setModify_name(String modifyName) {
		this.modify_name = modifyName;
	}
	
	/**
	* @description 获取修改人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:07:12
	*/
	public String getModify_code() {
		return modify_code;
	}
	
	/**
	* @description 设置修改人code
	* @param modify_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月2日 上午11:07:22
	*/
	public void setModify_code(String modifyCode) {
		this.modify_code = modifyCode;
	}
	/**
	 * 修改人姓名
	 */
	private String modify_name;
	/**
	 * 修改人工号
	 */
	private String modify_code;
	
	/**
	 * 
	* @description 获取申请时间 
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:46:39
	 */
	public Date getApplicant_time() {
		return applicant_time;
	}
	/**
	 * 
	* @description 设置申请时间 
	* @param applicant_time
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:47:31
	 */
	public void setApplicant_time(Date applicantTime) {
		this.applicant_time = applicantTime;
	}
	/**
	 * 
	* @description 获取申请人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:47:49
	 */
	public String getApplicant_name() {
		return applicant_name;
	}
	/**
	 * 
	* @description 设置申请人姓名
	* @param applicant_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:47:53
	 */
	public void setApplicant_name(String applicantName) {
		this.applicant_name = applicantName;
	}
	/**
	 * 
	* @description 获取申请人工号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:47:57
	 */
	public String getApplicant_code() {
		return applicant_code;
	}
	/**
	 * 
	* @description 设置申请人工号
	* @param applicant_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:48:02
	 */
	public void setApplicant_code(String applicantCode) {
		this.applicant_code = applicantCode;
	}
	/**
	 * 
	* @description 获取申请人所在部门
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:48:05
	 */
	public String getOrg_name() {
		return org_name;
	}
	/**
	 * 
	* @description 设置申请人所在部门
	* @param org_name
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:48:09
	 */
	public void setOrg_name(String orgName) {
		this.org_name = orgName;
	}
	/**
	 * 
	* @description 获取申请人所在部门编号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:48:12
	 */
	public String getOrg_code() {
		return org_code;
	}
	/**
	 * 
	* @description 设置申请人所在部门编号
	* @param org_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:48:15
	 */
	public void setOrg_code(String orgCode) {
		this.org_code = orgCode;
	}

	/**
	 * 
	* @description 获取 状态
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:48:57
	 */
	public String getState() {
		return state;
	}
	/**
	 * 
	* @description 设置 状态
	* @param state
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:49:00
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 
	* @description 获取 备注
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:49:03
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 
	* @description 设置 备注
	* @param remarks
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:49:06
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}

