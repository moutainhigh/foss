package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: CheckSmtPwdEntity 
 * @Description: TODO(检查提交密码实体) 
 * @author &268974  wangzhili
 * @date 2016-6-25 上午10:01:01 
 *
 */
public class CheckSmtPwdEntity implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO()
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//工号
	private String userCode;
	//提交密码
	private String smtPwd;
	//用户名
	private String username;
	//记录插入数据库时间
	private Date recordDate;
	//修改时间
	private Date modifyDate;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getSmtPwd() {
		return smtPwd;
	}
	public void setSmtPwd(String smtPwd) {
		this.smtPwd = smtPwd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	

}
